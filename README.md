# fuse-rhdg-quickstart
The purpose of this project is demonstrate the integration between Jboss Fuse 7 Spring Boot and Red Hat Datagrid.  

## Requirements
Before of run this project, ensure that a instance of RHDG is running with a cache configured.


## PUT a key

To insert a key (put) in RHDG use the API [http://localhost:8083/cache/newKey](http://localhost:8083//cache/newKey)
This api should send a JSON below

    {
    	"key": "10",
    	"value": "Value to Key"
    }

The expected result is a ***HTTP status 200*** and a new key inserted in **RHDG**.

## GET a Key

To get a Key use the API [http://localhost:8083/cache/retrieveKey/{key}](http://localhost:8083/cache/retrieveKey/{key})




# References
List of editable properties of Hot Rod API / 
Lista de propriedades editáveis para a API Hot Rod

[https://docs.jboss.org/infinispan/9.4/apidocs/org/infinispan/client/hotrod/configuration/package-summary.html](https://docs.jboss.org/infinispan/9.4/apidocs/org/infinispan/client/hotrod/configuration/package-summary.html)
