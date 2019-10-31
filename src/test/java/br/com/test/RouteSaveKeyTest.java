package br.com.test;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;

import br.com.redhat.poc.Application;
import br.com.redhat.poc.dto.KeyDTO;

@PropertySource("classpath:application.properties")

public class RouteSaveKeyTest extends CamelSpringTestSupport {

	protected AbstractApplicationContext createApplicationContext() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
		
		return ctx;
	}

	
	@Override
	  public boolean isUseAdviceWith() {
	    return true;
	  }
	
    //@Override 
    public String isMockEndpointsAndSkip() { 
            return "infinispan://*"; 
    } 

	@Test
	public void call() throws Exception {
		
		KeyDTO keyDto = new KeyDTO();
		keyDto.setKey("1");
		keyDto.setValue("Teste - valo chave 1");
				
		template.sendBodyAndHeader("direct:saveKey", keyDto, "id", "1");
		System.out.println("exchange");
		
	}

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("direct:saveKey").autoStartup(true).log("$body");
			}
		};
	}
}