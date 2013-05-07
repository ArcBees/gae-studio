##Requirements
The project in which you want to plug GAE Studio must repect the following conditions:

1. It must use Guice on the server-side
2. It must use the Java App Engine datastore

##Installing GAE Studio
1. Clone the gae-studio code
2. cd {cloned folder}
3. Install gae-studio to your local .m2: `mvn clean install -Pbuild-jar`

##Plugging GAE Studio in your project
To plug GAE Studio into your project, follow these steps:

*. Modify the appengine-web.xml file and turn on sessions.
```
<sessions-enabled>true</sessions-enabled>
```
*. Add the GAE Studio dependency
```
<dependency>
    <groupId>com.arcbees.gaestudio</groupId>
    <artifactId>gae-studio</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
*. Install the `GaeStudioServerModule()` into the app's server module. 
```
import com.arcbees.gaestudio.server.guice.GaeStudioServerModule;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        // ...
        install(new GaeStudioServerModule());
    }
}
```

##Running GAE Studio
1. Compile and run the project in which you plugged GAE Studio
2. Header over to `{your application's domain}/gae-studio-admin`
3. Grab a beer

##Build Server
[Build Server](http://teamcity-private.arcbees.com/project.html?projectId=project7&tab=projectOverview)
