package br.com.redhat.poc.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ResponseSaveKeyProcessor implements Processor {
	
	@Override
	public void process(Exchange exchange) throws Exception {
				
	
		exchange.getIn().setBody("OK-FEITO");
				
	}

}
