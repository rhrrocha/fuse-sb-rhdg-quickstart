package br.com.redhat.poc;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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

    ConfigurationBuilder builder = new ConfigurationBuilder();
	
	 @Bean
	 public RemoteCacheManager cacheContainer() {
		 builder =  new ConfigurationBuilder();

		 builder.addServer().host("192.168.10.1").port(11222);
		 builder.addServer().host("127.0.0.1").port(11222);

		 
		 return new RemoteCacheManager(builder.build());
	 }	 
	
	
    public static void main(String[] args) {
        org.springframework.context.ApplicationContext ctx = org.springframework.boot.SpringApplication.run(Application.class, args);
    }
}