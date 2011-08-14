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
