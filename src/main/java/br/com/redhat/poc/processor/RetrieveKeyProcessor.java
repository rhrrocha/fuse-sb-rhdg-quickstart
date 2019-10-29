package br.com.redhat.poc.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class RetrieveKeyProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {

		String keyValue = exchange.getIn().getHeader("CamelInfinispanOperationResult").toString();
	
		exchange.getIn().setBody(keyValue);
		
	}

}
