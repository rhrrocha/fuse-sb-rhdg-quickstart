package br.com.redhat.poc.rest;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.infinispan.InfinispanConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import br.com.redhat.poc.dto.KeyDTO;
import br.com.redhat.poc.model.CacheHealthModel;
import br.com.redhat.poc.processor.RetrieveKeyProcessor;
import br.com.redhat.poc.processor.SaveKeyProcessor;

@Component
public class RouteDSLSample extends RouteBuilder {

	
	@Override
	public void configure() throws Exception {

			
		restConfiguration("spark-rest").port(8083).bindingMode(RestBindingMode.json);

		rest("/cache").consumes("application/json").produces("application/json")
		.get("/retrieveKey/{id}")
			.to("direct:getKey")		  					
		.post("/newKey")
			.type(KeyDTO.class)
			.to("direct:saveKey");
					
		//Consumers to services
		from("direct:getKey")
			.routeId("get-key-route")
			.setHeader(InfinispanConstants.OPERATION, constant(InfinispanConstants.GET))	
		
			.setHeader(InfinispanConstants.KEY , simple("${in.header.id}"))
			/*
			 * The cache name is defined in the application.properties under the key custom.rhdg.cache.name
			 * 
			 * O nome do cache está definido na chave key custom.rhdg.cache.name no arquivo application.properties
			 */
			.to("infinispan://foo??cacheContainer=#cacheContainer&cacheName={{custom.rhdg.cache.name}}") 
			.process(new RetrieveKeyProcessor());
			
		from("direct:saveKey")
			.routeId("put-key-route")
		    .setHeader(InfinispanConstants.OPERATION, constant(InfinispanConstants.PUT))
		    .process(new SaveKeyProcessor())
		    .to("infinispan://foo??cacheContainer=#cacheContainer&cacheName=cache1");
		
				
		/*
		 * This route verify the number of nodes actives in a cluster every 5 seconds.
		 * Esta rota verifica a cada 5 segundos a quantidade de nós ativos no cluster.
		 * 
		 * @see custom.rhdg.health.check.host in application.properties
		 * 
		 * NOTE: this url is expected a basic authentication
		 */
		from("timer://foo?fixedRate=true&period=5000")
			.routeId("health-check-route")
            .setHeader(Exchange.HTTP_METHOD).constant(HttpMethod.GET)
            .setHeader(Exchange.HTTP_QUERY,  simple("?operation=resource&include-runtime=true&json.pretty=1"))  
            //NOTE: Basic authentication parameter cannot sent by Exchange.HTTP_QUERY in camel-http4 component. 
            //NOTA: Os parametros para basic authentication não podem ser passados via header no componente camel-http4.
            .to("http4://{{custom.rhdg.health.check.host}}/management/subsystem/datagrid-infinispan/cache-container/cache-container-example/health/HEALTH??authMethod=Basic&authUsername=admin&authPassword=admin")			
            .unmarshal().json(JsonLibrary.Jackson, CacheHealthModel.class)  //Transform the JSON response in a java object.
            .log("Number of Nodes ON: ${in.body.numberOfNodes}");
           	
	}

}
