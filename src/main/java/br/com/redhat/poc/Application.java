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

    
	
	 @Bean
	 /*
	  * English version:
	  * RemoteCacheManager is used to access remote caches. When started, the RemoteCacheManager instantiates connections to the Hot Rod server (or multiple Hot Rod servers). 
	  * It then manages the persistent TCP connections while it runs. As a result, RemoteCacheManager is resource-intensive. 
      * The recommended approach is to have a single RemoteCacheManager instance for each Java Virtual Machine (JVM).
      * 
      * Portuguese version:
      * RemoteCacheManager é usado para acessar caches remotos. Quando iniciado, o RemoteCacheManager instancia as conexões com o servidor Hot Rod (ou vários servidores Hot Rod). 
      * Em seguida, ele gerencia as conexões TCP persistentes enquanto é executado. Como resultado, o RemoteCacheManager consome muitos recursos.
      * A abordagem recomendada é ter uma única instância do RemoteCacheManager para cada Java Virtual Machine (JVM).
      *
	  */
	 public RemoteCacheManager cacheContainer() {
   		  
		 	/* NOTE:
		 	* All values can be inserted in applicantion.properties or in a specific .properties file to hotrod like hotrod-client.properties
		 	* 
		 	* 
		 	* Todos esses valores podem ser incluidos no arquivo applicaiton.properties ou em um arquivo separado como por exemplo hotrod-client.properties
		 	* 
		 	*/ 
		 
		    ConfigurationBuilder builder =  new ConfigurationBuilder();
                        
            /*
             *List of nodes that make up the cluster to connect.
             *Lista de nodes que formam o cluster            
             * 
             */
            builder.addServers("10.0.0.3:11222;10.0.0.4:11222;10.0.0.5:11222;10.0.0.6:11222;10.0.0.7:11222");
           
            /*
             * This property defines the maximum socket read timeout in milliseconds before giving up waiting for bytes from the server.
             * Essa propriedade define em milesegundos o timeout do socket  de leitura.
             */
            builder.socketTimeout(5000);
            
            /*
             * This property defines the maximum socket connect timeout before giving up connecting to the server.
             * Propriedade que define o tempo maximo de conexão no socket.
             */
            builder.connectionTimeout(5000);
            
            /*
             * It sets the maximum number of retries for each request. A valid value should be greater or equals than 0 (zero). 
             * Zero means no retry will made in case of a network failure. It defaults to 10.
             * 
             * Define a quantidadede retentativas para cada request feita. Os valores devem ser superior a 0, onde 0 significa sem tentativas. O valor default é 10.
			 *
             */
            builder.maxRetries(5000);
        
            builder.connectionPool().minIdle(10);
            builder.connectionPool().maxIdle(50);
		 
		 
		 return new RemoteCacheManager(builder.build());
	 }	 
	
	
    public static void main(String[] args) {
        org.springframework.context.ApplicationContext ctx = org.springframework.boot.SpringApplication.run(Application.class, args);
    }
}