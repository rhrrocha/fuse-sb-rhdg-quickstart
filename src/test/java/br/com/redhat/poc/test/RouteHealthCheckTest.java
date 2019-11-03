package br.com.redhat.poc.test;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;

import br.com.redhat.poc.Application;


@PropertySource("classpath:application.properties")
public class RouteHealthCheckTest extends CamelSpringTestSupport {

	
	protected AbstractApplicationContext createApplicationContext() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
		
		return ctx;
	}

	
	@Override
    /*
     * Change the from from("timer://foo?fixedRate=true&period=5000") to direct:mockTimer
     * This is necessary to simplify the execution of the test because of the original start endpoint cannot be invoked manually.
     */
	public void setUp() throws Exception {
	 	replaceRouteFromWith("health-check-route", "direct:mockTimer");
        super.setUp();
    } 

	@Test
	public void call() throws Exception {
		
		context.getRouteDefinitions().get(0).adviceWith(context, new RouteBuilder() {
	        @Override
	        public void configure() throws Exception {
	            // intercept sending to mock:http4 instead of http4://* 
	            interceptSendToEndpoint("http4://*")
	                    .skipSendToOriginalEndpoint()
	                    .to("direct:http4");
	        }
	    });
		
		getMockEndpoint("mock:result").expectedMessageCount(1);
		
		template.sendBodyAndHeader("direct:mockTimer", "", "", "");
		
	}

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("direct:http4").process(new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						
						exchange.getIn().setBody(mockHttp4());
						
					}
				});
			}
		};
	}
	
	
	/*
	 * This method simulate a response of endpoint:.to("http4://{{custom.rhdg.health.check.host}}/management/subsystem/datagrid-infinispan/cache-container/cache-container-example/health/HEALTH"
	 * 
	 */
	private  String  mockHttp4() {
	
		StringBuffer sb = new StringBuffer();
				
		sb.append(" { ");
		sb.append("     \"cache-health\" : [ ");
		sb.append("         \"___protobuf_metadata\", ");
		sb.append("         \"HEALTHY\", ");
		sb.append("         \"mecachedCache\", ");
		sb.append("         \"HEALTHY\", ");
		sb.append("         \"cache1\", ");
		sb.append("         \"HEALTHY\", ");
		sb.append("         \"___script_cache\", ");
		sb.append("         \"HEALTHY\" ");
		sb.append("     ], ");
		sb.append("     \"cluster-health\" : \"HEALTHY\", ");
		sb.append("     \"cluster-name\" : \"cache-container-example\", ");
		sb.append("     \"free-memory\" : 6088265, ");
		sb.append("     \"log-tail\" : [ ");
		sb.append("         \"      service jboss.datagrid-infinispan.cc-itau-poc.default (missing) dependents: [service jboss.datagrid-infinispan-endpoint.hotrod.hotrod-connector, service jboss.datagrid-infinispan-endpoint.rest.rest-connector, service jboss.datagrid-infinispan-endpoint.memcached.memcached-connector] \", ");
		sb.append("         \"      service jboss.datagrid-infinispan.cc-itau-poc.memcachedCache (missing) dependents: [service jboss.datagrid-infinispan-endpoint.memcached.memcached-connector] \", ");
		sb.append("         \"\", ");
		sb.append("         \"2019-11-03 15:52:38,588 INFO  [org.jboss.as.server] (Controller Boot Thread) WFLYSRV0212: Resuming server\", ");
		sb.append("         \"2019-11-03 15:52:38,590 INFO  [org.jboss.as] (Controller Boot Thread) WFLYSRV0060: Http management interface listening on http://127.0.0.1:9990/management\", ");
		sb.append("         \"2019-11-03 15:52:38,590 INFO  [org.jboss.as] (Controller Boot Thread) WFLYSRV0051: Admin console listening on http://127.0.0.1:9990\", ");
		sb.append("         \"2019-11-03 15:52:38,590 ERROR [org.jboss.as] (Controller Boot Thread) WFLYSRV0026: Red Hat Data Grid Server 7.3.2 (WildFly Core 6.0.12.Final-redhat-00001) started (with errors) in 8618ms - Started 239 of 294 services (3 services failed or missing dependencies, 151 services are lazy, passive or on-demand)\", ");
		sb.append("         \"2019-11-03 15:52:42,376 INFO  [org.jgroups.protocols.pbcast.GMS] (jgroups-7,rosantos) _rosantos:site01: no members discovered after 5004 ms: creating cluster as first member\", ");
		sb.append("         \"2019-11-03 15:52:42,383 INFO  [org.infinispan.remoting.transport.jgroups.JGroupsTransport] (jgroups-7,rosantos) ISPN000439: Received new x-site view: [site01]\", ");
		sb.append("         \"2019-11-03 15:52:42,384 INFO  [org.jgroups.protocols.relay.RELAY2] (jgroups-7,rosantos) _rosantos:site01: joined bridge cluster 'xsite'\" ");
		sb.append("     ], ");
		sb.append("     \"number-of-cpus\" : 8, ");
		sb.append("     \"number-of-nodes\" : 1, ");
		sb.append("     \"total-memory\" : 6291456 ");
		sb.append("} ");
		
		return sb.toString();
	
	
	}

}
