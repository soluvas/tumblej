# TumbleJ Tumblr API Camel Component

URI :

    tumblej:<yourblog>.tumblr.com?consumerKey=xxx&consumerSecret=xxx&token=xxx&tokenSecret=xxx

## Posting

Default post `type` is `text`.
All post types (including `Link` post) use the exchange message body for primary post content, HTML allowed.

Optional headers:

1. `tags`
2. `date`

### Posting Text

Header: `type = text` (optional)

### Posting Photo

Header: `type = photo`

Headers:

1. `caption`: The user-supplied caption, HTML allowed
2. `source` (or Camel message body): The photo source URL
3. `link`: The "click-through URL" for the photo

### Posting Quote

Header: `type = quote`

Headers:

1. `quote` (or Camel message body): The full text of the quote, HTML entities must be escaped 
2. `source`: Cited source, HTML allowed

### Posting Link

Header: `type = link`

Headers:

1. `title`: The title of the page the link points to, HTML entities should be escaped	
2. `url` (or Camel message body): The link	
3. `description`: A user-supplied description, HTML allowed	

### Posting Chat

Header: `type = chat`

Headers:

1. `title`: The title of the chat

### Posting Audio

Header: `type = audio`

### Posting Video

Header: `type = video`

Headers:

1. `caption`: The user-supplied caption
2. `embed`: HTML embed code for the video. Required: Yes, either embed or data
3. `data`: A video file. Required: Yes, either embed or data 


## Deploying to Karaf

### Patch Karaf jre.properties

Overwrite `karaf/etc/jre.properties` using `karaf/etc/jre.properties.cxf`
(see https://issues.apache.org/jira/browse/CAMEL-5434 )

### Install camel-core

In Karaf, with features you run:

	features:addUrl mvn:org.apache.camel.karaf/apache-camel/2.10.0/xml/features
	features:install -v camel-core

Without features: (untested)

    install mvn:org.apache.camel/camel-core/2.10.0

### Install Jersey dependencies

	install mvn:org.codehaus.jettison/jettison/1.3.1
	install mvn:org.codehaus.jackson/jackson-core-asl/1.9.8
	install mvn:org.codehaus.jackson/jackson-mapper-asl/1.9.8
	install mvn:org.codehaus.jackson/jackson-jaxrs/1.9.8

jettison is required by jersey-json
jackson 1.9 is required by jersey-json

### Install jersey-core, jersey-json, jersey-client, oauth-signature, oauth-client

	install mvn:com.sun.jersey.contribs.jersey-oauth/oauth-client/1.13
	install mvn:com.sun.jersey/jersey-core/1.13
	install mvn:com.sun.jersey/jersey-json/1.13
	install mvn:com.sun.jersey/jersey-client/1.13
	install mvn:com.sun.jersey.contribs.jersey-oauth/oauth-signature/1.13

Then start all these installed bundles.

If you want to uninstall stuff:

	uninstall jersey-core
	uninstall jersey-client
	uninstall jersey-json
	uninstall oauth-signature
	uninstall oauth-client

### Install tumblej and tumblej-camel

1. Build and install *tumblej*

	cd ~/git/tumblej
	mvn clean install -DskipTests

2. Install the packages into Karaf

	install mvn:com.daneshzaki.tumblej/tumblej/2.0.0-SNAPSHOT
	install mvn:org.soluvas.social/tumblej-camel/1.0.0-SNAPSHOT

For rapid development:

	dev:watch mvn:*


## Troubleshooting: com.sun.jersey.api.client.ClientHandlerException: com.sun.jersey.oauth.signature.UnsupportedSignatureMethodException: HMAC-SHA1

I'm not sure why, but uninstalling the Jersey and Jersey-OAuth bundles, then reinstalling (in this order):

* oauth-client
* jersey-core
* jersey-json
* jersey-client
* oauth-signature

then starting these bundles, make it work. Probably something to do with ordering and/or start state of bundles.  

## Deploying to ServiceMix (untested)

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
* tumblej-*.jar
