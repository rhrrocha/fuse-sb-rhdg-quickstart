package br.com.redhat.poc.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.infinispan.InfinispanConstants;
import org.springframework.stereotype.Component;

import br.com.redhat.poc.dto.KeyDTO;

@Component
public class SaveKeyProcessor implements Processor {
	
	@Override
	public void process(Exchange exchange) throws Exception {
				
		KeyDTO chave = exchange.getIn().getBody(KeyDTO.class);
			
		exchange.getIn().setHeader(InfinispanConstants.KEY, chave.getKey());
		exchange.getIn().setHeader(InfinispanConstants.VALUE, chave.getValue());
				
	}

}
