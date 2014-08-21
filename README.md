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

*. Modify the appengine-web.xml file and turn on sessions and channel presence.
```
<sessions-enabled>true</sessions-enabled>

<inbound-services>
    <service>channel_presence</service>
</inbound-services>
```
*. Add the GAE Studio dependency
```xml
<dependency>
    <groupId>com.arcbees.gaestudio</groupId>
    <artifactId>gae-studio-webapp</artifactId>
    <version>${gaestudio.version}</version>
</dependency>
```

*. Install the `GaeStudioModule()` into the app's server module.
```
import com.arcbees.gaestudio.server.guice.GaeStudioServerModule;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        // ...
        install(new GaeStudioModule());
    }
}
```

##Running GAE Studio
1. Compile and run the project in which you plugged GAE Studio
2. Header over to `{your application's domain}/gae-studio-admin/`
3. Grab a beer

##Build Server
[Build Server](http://teamcity-private.arcbees.com/project.html?projectId=project7&tab=projectOverview)

##Contributing
If you want to contribute to this project, here's a short description of the modules:

* gae-studio-client : client code
* gae-studio-companion : since gae-studio is plugged into another project, this is a sample server-side project with gae-studio integrated to it.
* gae-studio-companion-ear : this is needed for deployment
* gae-studio-server : server code for gae-studio
* gae-studio-webapp : the web app

# Launch configurations
Here's an example of launch configuration to use with IntelliJ
Server : ![server configuration](https://drive.google.com/a/arcbees.com/file/d/0B8nEpoIyH0cWMUtrdk52LVBrSEE/edit?usp=sharing)
SDM : ![super dev mode](https://drive.google.com/a/arcbees.com/file/d/0B8nEpoIyH0cWRVVmWm1IMnNpTkU/edit?usp=sharing)

If you want to use super dev mode, you can use the "sdm" profile in maven (`mvn clean install -Psdm`)

# Accessing the app
- The url to access the application is http://localhost:port/gae-studio-admin/
The port is specified in the server config. (the default port is 8888 if you use a gwt config to launch the server)

- To login to the application you have to check the "Sign in as Administrator" and then click on "Log In"
