package br.com.redhat.poc.test;

import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;

import br.com.redhat.poc.Application;
import br.com.redhat.poc.dto.KeyDTO;

/**
 * Simple JUnit test
 * @author rosantos
 *
 */
@PropertySource("classpath:application.properties")
public class RouteSaveKeyTest extends CamelSpringTestSupport {

	protected AbstractApplicationContext createApplicationContext() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
		
		return ctx;
	}

	/*
	 * inform to Junit to ignore and not to execute  endpoint ever this one  starts with "infinispan://" ao JUnit
	 * 
	 */
    @Override 
    public String isMockEndpointsAndSkip() { 
            return "infinispan://*"; 
    } 

	@Test
	public void call() throws Exception {
		
		KeyDTO keyDto = new KeyDTO();
		keyDto.setKey("1");
		keyDto.setValue("Teste - valo chave 1");
				
		template.sendBodyAndHeader("direct:saveKey", keyDto, "id", "1");
			
	}
	
}