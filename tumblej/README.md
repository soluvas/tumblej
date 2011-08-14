Testing with Scala
------------------
	mvn -Pscala exec:exec

    import com.daneshzaki.tumblej._
    
    var tj = new TumbleJ(consumerKey, consumerSecret, baseHostname, token, tokenSecret)
    tj.postQuote("Keren<br>Bo'!", "Gadis Cantik", "malam,bikin,happy", null)

Deploying to ServiceMix
-----------------------
Get the files:

    mvn dependency:copy-dependencies

Needed for ServiceMix:

* jackson-core-asl-1.7.1.jar
* jackson-jaxrs-1.7.1.jar
* jackson-mapper-asl-1.7.1.jar
* jackson-xc-1.7.1.jar
* jersey-client-1.8.jar
* jersey-core-1.8.jar
* jersey-json-1.8.jar
* oauth-client-1.8.jar
* oauth-signature-1.8.jar
* commons-io-2.0.1.jar
* slf4j-api-1.6.1.jar
* slf4j-jcl-1.6.1.jar

Troubleshooting
---------------
If you get this when deploying to ServiceMix:

Error executing command: Unable to resolve module com.sun.jersey.jersey-json [222.0] because it is exposed to package 'javax.ws.rs' from com.sun.jersey.jersey-core [221.0] and org.apache.servicemix.specs.jsr311-api-1.1 [117.0] via two dependency chains.                                                     

Chain 1:
  com.sun.jersey.jersey-json [222.0]
    import: (package=javax.ws.rs)
     |
    export: package=javax.ws.rs
  com.sun.jersey.jersey-core [221.0]

Chain 2:
  com.sun.jersey.jersey-json [222.0]
    import: (&(package=org.codehaus.jackson.jaxrs)(version>=1.7.0))
     |
    export: package=org.codehaus.jackson.jaxrs; uses:=javax.ws.rs
  jackson-jaxrs [217.0]
    import: (package=javax.ws.rs)
     |
    export: package=javax.ws.rs
  org.apache.servicemix.specs.jsr311-api-1.1 [117.0]

It means there is conflict between "org.apache.servicemix.specs.jsr311-api-1.1"
and "jersey-core" due to startup order. The culprit is jackson-jaxrs.
 
To resolve this, simply move jackson-jaxrs-*.jar out of deploy/ directory then
put it back again. ServiceMix will ensure both JARs bind to the jersey-core,
not org.apache.servicemix.specs.jsr311-api-1.1.
 