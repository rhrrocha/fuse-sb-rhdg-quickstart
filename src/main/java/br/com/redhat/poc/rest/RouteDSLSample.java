package br.com.redhat.poc.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.infinispan.InfinispanConstants;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import br.com.redhat.poc.dto.KeyDTO;
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
	
	}

}
