

# fuse-rhdg-quickstart
The purpose of this project is demonstrate the integration between Jboss Fuse 7 Spring Boot and Red Hat Datagrid.  

**JBoss Fuse Version:** 7.5.0.fuse-750029-redhat-00002
**Red Hat Datagrid:** jboss-datagrid-7.3.0 (Infinispan 9)

## Requirements
Before of run this project, ensure that an instance of RHDG is running with a cache configured. 
The default cache expected is **cache1** and can be changed in the file applicarion.properties. See the property **custom.rhdg.cache.name**.


## Implementations
### 1 - PUT a key 

To insert a key (put) in RHDG use the API [http://localhost:8083/fuse/rhdg](http://localhost:8083/fuse/rhdg)
This api should send a JSON below

    {
    	"key": "10",
    	"value": "Value to Key"
    }

The expected result is a ***HTTP status 200*** and a new key inserted in **RHDG**.

### 2 - GET a Key

To get a Key use the API [(http://localhost:8083/fuse/rhdg/{key}](http://localhost:8083/fuse/rhdg/{key})

### 3 - Verify  number of actives nodes

The **route health-check-route**  execute a request in the Management REST API of RHDG to get the number of actives nodes.
The url is below:

    http://<RHDG HOST><Management PORT>/management/subsystem/datagrid-infinispan/cache-container/<CACHE CONTAINER NAME>>/health/HEALTH?operation=resource&include-runtime=true&json.pretty=1
	
This API requires basic authentication. Use the username and password configured during RHDG instalation.

# Running
## For Linux or Windows

    mvn clean spring-boot:run

## For Openshift

   Use this template to create a configMap: 
    
    oc create -f configMap.yml 
    
    oc policy add-role-to-user view  system:serviceaccount:openshift:fuse-sb-rhdg-quickstart-s2i-7
To deploy on OpenShift

    mvn clean fabric8:deploy -Popenshift
   

# References

[Health Check API ](https://access.redhat.com/documentation/en-us/red_hat_data_grid/7.2/html/administration_and_configuration_guide/the_health_check_api)

[List of editable properties of Hot Rod API](https://docs.jboss.org/infinispan/9.4/apidocs/org/infinispan/client/hotrod/configuration/package-summary.html)






