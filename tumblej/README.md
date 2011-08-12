Testing with Scala
------------------
	mvn -Pscala exec:exec

    import com.daneshzaki.tumblej._
    
    var tj = new TumbleJ(consumerKey, consumerSecret, baseHostname, token, tokenSecret)
    tj.postQuote("Keren<br>Bo'!", "Gadis Cantik", "malam,bikin,happy", null)
