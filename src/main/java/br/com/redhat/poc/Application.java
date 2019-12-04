package br.com.redhat.poc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties

@SpringBootApplication
@ImportResource({"classpath:spring/camelContext.xml"})
@PropertySource("classpath:application.properties")
public class Application {

	/*
	 * get the member list of cluster from application.properties
	 * 
	 * Obtem a lista de membros do cluster do arquivo application.properties.
	 */
 	@Value("${infinispan.client.hotrod.server_list}")
 	private  String rhdgHosts;
	
 	@Value("${infinispan.client.hotrod.socket_timeout}")
 	private  Integer socketTimeout;
 	
 	@Value("${infinispan.client.hotrod.connect_timeout}")
 	private  Integer connecionTimeout;
 	
 	@Value("${infinispan.client.hotrod.max_retries}")
 	private  Integer maxRetries;
 	
	
	
	
    public static void main(String[] args) {
        org.springframework.context.ApplicationContext ctx = org.springframework.boot.SpringApplication.run(Application.class, args);
    }
}