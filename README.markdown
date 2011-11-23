Google App Engine Query Logger
==============================

This is a brief proof of concept for automatically logging executed queries in GAE.  All you need to do is place
gae-query-logger.jar in your CLASSPATH and configure a Logger instance for injection.  If you're using Guice, you
can do this by simply adding this somewhere in your Guice configuration:

    install(new QueryLoggerModule());

Once this is done, your queries should automatically be logged to INFO upon execution.  Any log format customization
and filtering should be done directly in your logger configuration.

Future Direction
================

* Converting the projects to Maven
* Unit testing
