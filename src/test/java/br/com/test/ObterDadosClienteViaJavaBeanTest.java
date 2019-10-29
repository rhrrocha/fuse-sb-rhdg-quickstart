package br.com.test;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;

import br.com.redhat.poc.Application;

@PropertySource("classpath:application.properties")
public class ObterDadosClienteViaJavaBeanTest extends CamelSpringTestSupport {

	protected AbstractApplicationContext createApplicationContext() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
		
		return ctx;
	}


	@Test
	public void testSendMatchingMessage() throws Exception {
	
		template.sendBodyAndHeader("direct:obterDadosClienteViaJavaBean", "<matched/>", "id", "1");
		System.out.println("exchange");
		
	}

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("direct:obterDadosClienteViaJavaBean2").autoStartup(true).log("$body");
			}
		};
	}
}