package br.com.test;

import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;

import br.com.redhat.poc.Application;

@PropertySource("classpath:application.properties")
public class RouteGetKeyTest extends CamelSpringTestSupport {

	protected AbstractApplicationContext createApplicationContext() {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);

		return ctx;

	}

	// @Override

	public String isMockEndpointsAndSkip() {
		return "infinispan://*";
	}

	@Override
	public boolean isUseAdviceWith() {
		return false;
	}

	@Test
	public void call() throws Exception {


		template.sendBodyAndHeader("direct:getKey", "", "id", "1");

	}
	/*
	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteDSLSample(); //Real implementation. To try not recreate routes here.
	}*/
	
	/*
	 * @Before public void mockEndpoints() throws Exception { AdviceWithRouteBuilder
	 * mockSolr = new AdviceWithRouteBuilder() {
	 * 
	 * @Override public void configure() throws Exception {
	 * 
	 * 
	 * interceptSendToEndpoint("direct:getKey") .skipSendToOriginalEndpoint()
	 * .to("mock:direct:getKey");
	 * 
	 * 
	 * } }; context.getRouteDefinition("routeExemple").adviceWith(context,
	 * mockSolr); }
	 */

}