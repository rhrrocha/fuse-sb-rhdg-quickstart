package br.com.redhat.poc.route;

import java.util.concurrent.TimeUnit;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.infinispan.InfinispanConstants;
import org.apache.camel.component.infinispan.InfinispanOperation;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import br.com.redhat.poc.dto.KeyDTO;
import br.com.redhat.poc.model.CacheHealthModel;
import br.com.redhat.poc.processor.ResponseSaveKeyProcessor;

@Component
public class RouteDSLSample extends RouteBuilder {

	private static Logger LOGGER = LoggerFactory.getLogger(RouteDSLSample.class);
	
	@Override
	public void configure() throws Exception {

			
		restConfiguration("spark-rest").port(8083).bindingMode(RestBindingMode.json);

		rest("/fuse").consumes("application/json")
			  					
		.post("/rhdg")
			.type(KeyDTO.class)
			.to("direct:saveKey")
		.get("/rhdg/{id}")
			.to("direct:getKey")
		.delete("/rhdg/{id}")
			.to("direct:deleteKey");
		
		//Consumers to services
		

		// get a key
		from("direct:getKey")
			.routeId("get-key-route")
			.setHeader(InfinispanConstants.OPERATION, constant(InfinispanOperation.GET))	
		
			.setHeader(InfinispanConstants.KEY , simple("${in.header.id}"))
			/*
			 * The cache name is defined in the application.properties under the key custom.rhdg.cache.name
			 * 
			 * O nome do cache está definido na chave key custom.rhdg.cache.name no arquivo application.properties
			 */
			.to("infinispan:{{custom.rhdg.cache.name}}?cacheContainer=#remoteCacheManagerExample") 
		     .process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
				
						LOGGER.info("Value of Key " + exchange.getIn().getHeader(InfinispanConstants.KEY) + " is " + exchange.getIn().getBody(String.class));
						
					}
		 	});
		
		//Save a key
		from("direct:saveKey").routeId("put-key-route")
				.setHeader(InfinispanConstants.OPERATION, constant(InfinispanOperation.PUT))
				.setHeader(InfinispanConstants.LIFESPAN_TIME, simple("10"))
				.setHeader(InfinispanConstants.OPERATION, constant(InfinispanOperation.PUT))
				.setHeader(InfinispanConstants.MAX_IDLE_TIME, simple("10"))
				.setHeader(InfinispanConstants.MAX_IDLE_TIME_UNIT, simple(TimeUnit.SECONDS.toString()))
				.process(new Processor() {

					@Override
					public void process(Exchange exchange) throws Exception {
						KeyDTO chave = exchange.getIn().getBody(KeyDTO.class);
						exchange.getIn().setHeader(InfinispanConstants.KEY, chave.getKey());
						exchange.getIn().setHeader(InfinispanConstants.VALUE, chave.getValue());
					}

				}).to("infinispan:{{custom.rhdg.cache.name}}?cacheContainer=#remoteCacheManagerExample")
				.process(new ResponseSaveKeyProcessor());
				
		//Remove a key
		from("direct:deleteKey").routeId("delete-key-route")
				.setHeader(InfinispanConstants.OPERATION, constant(InfinispanOperation.REMOVE))
				.setHeader(InfinispanConstants.KEY , simple("${in.header.id}"))
				.to("infinispan:{{custom.rhdg.cache.name}}?cacheContainer=#remoteCacheManagerExample");
				
				
		
			
		
		/*
		 * This route verify the number of nodes actives in a cluster every 5 seconds.
		 * Esta rota verifica a cada 5 segundos a quantidade de nós ativos no cluster.
		 * 
		 * @see custom.rhdg.health.check.host in application.properties
		 * 
		 * NOTE: this url is expected a basic authentication
		 */
		from("timer://foo?fixedRate=true&period=5000").autoStartup(false)
			.routeId("health-check-route")
            .setHeader(Exchange.HTTP_METHOD).constant(HttpMethod.GET)
            .setHeader(Exchange.CONTENT_TYPE).constant("application/json")
            .setHeader(Exchange.HTTP_QUERY,  simple("?operation=resource&include-runtime=true&json.pretty=1"))  
            
            //NOTE: Basic authentication parameter cannot sent by Exchange.HTTP_QUERY in camel-http4 component. 
            //NOTA: Os parametros para basic authentication não podem ser passados via header no componente camel-http4.
            .to("http4://{{custom.rhdg.health.check.host}}/management/subsystem/datagrid-infinispan/cache-container/cc-example-one/health/HEALTH??authMethod=Basic&authUsername=admin&authPassword=admin")
            //Transform the JSON response in a java object.
            .unmarshal().json(JsonLibrary.Jackson, CacheHealthModel.class)  
            .log("Number of Nodes ON: ${in.body.numberOfNodes}");
		
	}
}
