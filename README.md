The tutorial walks you step by step on implementing JAX-RS (Resteasy) with CDI (Weld) on a high performance servlet container (Undertow)

==================
Undertow is a light weight java based webserver and servlet container. It is at the heart of WildFly the application server from Redhat. It performs remarkably well as benchmarked by the [TechEmpower benchmarks](http://www.techempower.com/blog/)

Weld is the reference implementation of [JSR 299](https://jcp.org/en/jsr/detail?id=299). You can learn more about [CDI](http://docs.oracle.com/javaee/6/tutorial/doc/giwhl.html)

Resteasy is Redhat's implementation of [(JSR 311 & JSR 339)](https://jsr311.java.net/) the spec for implementing restful webservices in Java. Jersey being the reference implementation.


Check out the [Wiki](https://github.com/vdevigere/undertow-cdi-jaxrs/wiki) for a details of the implementation:-
* [Lesson 1: Servlets on Undertow](https://github.com/vdevigere/undertow-cdi-jaxrs/wiki/Deploying-Servlets-to-Undertow.)
Learn how to create a servlet container and configure it programmatically. Once configured attach the container to the Undertow server to serve requests.

* [Lesson 2: JAX-RS(Resteasy) on Undertow](https://github.com/vdevigere/undertow-cdi-jaxrs/wiki/Deploying-JAX-RS-Resources-and-Applications-to-Undertow)
Enhance lesson 1 to add JAX-RS capabilities. Add Resources to your application
* [Lesson 3: Inject CDI managed beans into JAX-RS resources ](https://github.com/vdevigere/undertow-cdi-jaxrs/wiki/CDI-(Weld),-JAX-RS-(Resteasy)-on-Undertow)
Enhance lesson 2 to inject beans into your resource classes.
* [Lesson 4: Add OAuth support ](https://github.com/vdevigere/undertow-cdi-jaxrs/wiki/Lesson-4:-Adding-OAuth-support-for-Restful-Webservices)
Example uses Facebook as the OAuth provider.
