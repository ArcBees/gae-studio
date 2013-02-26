#GAE Studio

##Reference
[GAE Studio Website](http://gaestudio.arcbees.com/)

##Maven
* TODO

##Guice Configuration
* Modify the appengine-web.xml file and turn on sessions.
```xml
<sessions-enabled>true</sessions-enabled>
```

* 1. Install the `GaeStudioServerModule()` into the apps server module. 
```java
public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        install(new DispatchHandlersModule());
        install(new AuthenticationModule());

        // GAE-Studio Guice Bootstrapping
        install(new GaeStudioServerModule());
    }
}
```


#Contributing

##Install Jar
Install the jar to local repo for use for testing

* run `mvn clean install -Pbuild-jar`

##Build Server
[Build Server](http://teamcity-private.arcbees.com/project.html?projectId=project7&tab=projectOverview)
