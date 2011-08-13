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
