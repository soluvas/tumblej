TumbleJ Tumblr API Camel Component
==================================

URI :

	tumblej:<yourblog>.tumblr.com?consumerKey=xxx&consumerSecret=xxx&token=xxx&tokenSecret=xxx

Posting
-------
Default post type is text.
All post types (including Link post) use the exchange message body for primary post content.

Optional headers:

1. tags
2. date

Posting Text
------------
Header: type = text (optional)

Posting Photo
------------
Header: type = photo

Posting Quote
-------------
Header: type = quote

Headers:
1. source

Posting Link
-------------
Header: type = link

Posting Chat
-------------
Header: type = chat

Posting Audio
-------------
Header: type = audio

Posting Video
-------------
Header: type = video

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
* tumblej-*.jar
