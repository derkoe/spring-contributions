This is a Spring extension providing a mechanism similar to the Tapestry IoC Configurations (http://tapestry.apache.org/tapestry-ioc-configuration.html) or Eclipse Plugin mechanism.

Spring Contributions allow extensibility and modularity for your services (Spring beans). It is basically dependency injection with your dependencies defined in modules that you do not know about. This enables Eclipse-like extensions of your current services.

See also [Overview](Overview.md)

The current version is 2.0.0 - for the history of changes see [ReleaseNotes](ReleaseNotes.md)

The binaries are distributed via Maven Central (the central Maven repository). If your are using maven just add this to your dependencies:
```
<dependency>
    <groupId>com.googlecode.spring-contributions</groupId>
    <artifactId>spring-contributions</artifactId>
    <version>2.0.0</version>
</dependency>
```