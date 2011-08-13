package com.daneshzaki.tumblej;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;

/**
 * <b>TumbleJ</b> is a Java API for Tumblr. It aims to perform posting and
 * retrieving content.
 * 
 * @author <a href="http://www.daneshzaki.com">Danesh Zaki</a>
 *         <p />
 * 
 *         Revised on 3 July 2008
 *         <p />
 * 
 *         Version 1.1
 *         <p />
 * 
 *         This code is released as open-source under the LGPL license. See <a
 *         href
 *         ="http://www.gnu.org/licenses/lgpl.html">http://www.gnu.org/licenses
 *         /lgpl.html</a> for license details. This code comes with no warranty
 *         or support.
 * 
 * 
 */

public class TumbleJ {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private String consumerKey;
	private String consumerSecret;
	private String token;
	private String tokenSecret;
	
	/**
	 * Default constructor.
	 */
	public TumbleJ() { }
	

	/**
	 * @param consumerKey
	 * @param consumerSecret
	 * @param baseHostname
	 */
	public TumbleJ(String consumerKey, String consumerSecret, String baseHostname) {
		super();
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.baseHostname = baseHostname;
	}

	/**
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessToken
	 * @param accessTokenSecret
	 * @param baseHostname
	 */
	public TumbleJ(String consumerKey, String consumerSecret, String baseHostname,
			String accessToken, String accessTokenSecret) {
		super();
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.token = accessToken;
		this.tokenSecret = accessTokenSecret;
		this.baseHostname = baseHostname;
	}

	/**
	 * This constructor should be used for reading posts from the tumblog URL.
	 * 
	 * @param tumblogUrl
	 *            tumblelog URL
	 */

	public TumbleJ(String tumblogUrl) throws Exception {
		this.baseHostname = tumblogUrl;

	}

	/**
	 * This method sets the tumblelog URL for reading posts
	 * 
	 * @param tumblogUrl
	 *            tumblelog URL
	 */

	public void setTumblogUrl(String tumblogUrl) {
		this.baseHostname = tumblogUrl;
	}

	/**
	 * This method sets the numberOfPosts to be read
	 * 
	 * @param numberOfPosts
	 *            numberOfPosts to be read
	 */

	public void setNumberOfPosts(String numberOfPosts) {
		this.numberOfPosts = numberOfPosts;
	}

	/**
	 * This method sets the post number from where posts are to be read
	 * 
	 * @param startPostNo
	 *            tumblelog URL
	 */

	public void setStartPostNo(String startPostNo) {
		this.startPostNo = startPostNo;
	}

	/**
	 * This method sets the type of posts to be read
	 * 
	 * @param postType
	 *            type of posts to be read
	 */

	public void setPostType(String postType) {
		this.postType = postType;
	}

	/**
	 * This method writes text posts to the Tumblr tumblog.
	 * 
	 * @param postTitle
	 *            title of the post
	 * @param body
	 *            body of the post
	 * @param tags
	 *            tags for the post
	 * @param date
	 *            date of the post
	 */

	public JSONObject postText(String title, String body, String tags, String date)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		params.put("body", body);
		// call post method
		return post(POST_TEXT, tags, date, params);
	}

	/**
	 * This method writes quote posts to the Tumblr tumblog.
	 * 
	 * @param quote
	 *            quote to post
	 * @param source
	 *            source of quote
	 * @param tags
	 *            tags for the post
	 * @param date
	 *            date of the post
	 */

	public JSONObject postQuote(String quote, String source, String tags, String date)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("quote", quote);
		if (source != null && source != "")
			params.put("source", source);
		return post(POST_QUOTE, tags, date, params);
	}

	/**
	 * This method writes link posts to the Tumblr tumblog. Link description is
	 * not yet supported.
	 * 
	 * @param name
	 *            name of URL
	 * @param url
	 *            link to post
	 * @param description A user-supplied description, HTML allowed
	 * @param tags
	 *            tags for the post
	 * @param date
	 *            date of the post
	 */

	public JSONObject postLink(String name, String url, String description,
			String tags, String date) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", name);
		params.put("url", url);
		params.put("description", description);
		return post(POST_LINK, tags, date, params);
	}

	/**
	 * This method writes chat posts to the Tumblr tumblog.
	 * 
	 * @param title The title of the chat.
	 * @param conversation The text of the conversation/chat, with dialogue labels (no HTML)
	 * @param tags
	 *            tags for the post
	 * @param date
	 *            date of the post
	 */
	public JSONObject postChat(String title, String conversation,
			String tags, String date) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("title", title);
		params.put("conversation", conversation);
		return post(POST_CHAT, tags, date, params);
	}

	/**
	 * This method writes audio posts to the Tumblr tumblog.
	 * To upload audio file, use {@link postAudioData()}.
	 * 
	 * @param externalUrl Web-site URL of the audio file
	 * @param caption caption for the audio file
	 * @param tags tags for the post
	 * @param date date of the post
	 * @return API Response.
	 */
	public JSONObject postAudio(String externalUrl, String caption, String tags,
			String date) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("external_url", externalUrl);
		if (caption != null && caption != "")
			params.put("source", caption);
		return post(POST_AUDIO, tags, date, params);
	}

	/**
	 * This method writes audio posts to the Tumblr tumblog.
	 * Only MP3 format is supported by Tumblr as of August 13, 2011.
	 * 
	 * @param data An MP3 audio file (max 5 MB).
	 * @param caption caption for the audio file
	 * @param tags tags for the post
	 * @param date date of the post
	 * @return API Response.
	 */
	public JSONObject postAudioData(byte[] data, String caption, String tags,
			String date) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		if (caption != null && caption != "")
			params.put("source", caption);
		return post(POST_AUDIO, tags, date, params);
	}

	/**
	 * This method writes video posts to the Tumblr tumblog using embed.
	 * To use file uploads, see {@link postVideoData()}.
	 * 
	 * @param embed Web-site URL of the video file or HTML code for embedding video
	 * @param caption caption for the video file
	 * @param tags tags for the post
	 * @param date date of the post
	 */
	public JSONObject postVideo(String embed, String caption, String tags,
			String date) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("embed", embed);
		if (caption != null && caption != "")
			params.put("source", caption);
		return post(POST_VIDEO, tags, date, params);
	}

	/**
	 * This method writes video posts to the Tumblr tumblog using embed.
	 * To use file uploads, see {@link postVideoData()}.
	 * 
	 * @param embed Web-site URL of the video file or HTML code for embedding video
	 * @param caption caption for the video file
	 * @param tags tags for the post
	 * @param date date of the post
	 */
	public JSONObject postData(byte[] data, String caption, String tags,
			String date) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		if (caption != null && caption != "")
			params.put("source", caption);
		return post(POST_VIDEO, tags, date, params);
	}

	/**
	 * This method writes posts to the Tumblr tumblog. It is recommended to use
	 * the specific post methods as the array parameters are set in those
	 * methods.
	 * 
	 * @param type
	 *            The type of post to create. Specify one of the following:  text, photo, quote, link, chat, audio, video
	 * @param tags
	 *            tags for the post
	 * @param date
	 *            date of the post
	 * @param specificParams
	 *            array containing parameters for different type of posts
	 */

	public JSONObject post(String type, String tags, String date,
			Map<String, Object> specificParams) throws Exception {
		MultivaluedMapImpl data = new MultivaluedMapImpl();
		data.add("type", type);
		if (tags != null && tags != "")
			data.add("tags", tags);
		if (date != null && date != "")
			data.add("date", date);
		for (Entry<String, Object> entry : specificParams.entrySet()) {
			data.add(entry.getKey(), entry.getValue());
		}
		
		WebResource resource = client.resource("http://api.tumblr.com/v2/blog/" + baseHostname + "/post");
		prepareOauth(resource);
		JSONObject response = resource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(JSONObject.class, data);
		return response;
	}

	/**
	 * This method gets the posts from the tumblelog and returns an array of
	 * Posts
	 */

	public Post[] readPosts() throws Exception {

		// setup parser
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// get posts at URL
		Document document = builder.parse(baseHostname + "/api/read");
		NodeList postNodes = document.getElementsByTagName("post");

		// create a post array to store posts
		Post[] posts = new Post[postNodes.getLength()];

		// get each post
		for (int i = 0; i < postNodes.getLength(); i++) {

			// parse post nodes to get contents
			Node node = postNodes.item(i);
			NodeList nodes = node.getChildNodes();

			posts[i] = new Post();

			// get post contents
			for (int k = 0; k < nodes.getLength(); k++) {
				Node topNode = nodes.item(k);

				if (topNode.getNodeName().equals("regular-title")) {
					posts[i].setTitle(topNode.getFirstChild().getNodeValue());

				} else if (topNode.getNodeName().equals("regular-body")) {
					// set post body
					posts[i].setBody(topNode.getFirstChild().getNodeValue());

				}

			}
		}

		return posts;

	}

	// main method to use the API
	public static void main(String args[]) throws Exception {
		// writing stuff
		/*
		 * String tags = "TumbleJ, API, Java"; String date = new
		 * Date().toString();
		 * 
		 * TumbleJ tumblr = new TumbleJ(args[0], args[1]); tumblr.postText(
		 * "Testing the TumbleJ API",
		 * "Posting some random text using the TumbleJ API... ", tags, date);
		 * tumblr.postQuote(
		 * "It has become appallingly obvious that our technology has exceeded our humanity. "
		 * , "Albert Einstein", tags, date); tumblr.postLink(
		 * "API Dashboard: ProgrammableWeb",
		 * "http://www.programmableweb.com/apis", tags, date);
		 * 
		 * String embedCode =
		 * "<object width=\"300\" height=\"80\"><param name=\"movie\" value=\"http://media.imeem.com/m/OfasYbCCNZ/aus=false/\"></param><param name=\"wmode\" value=\"transparent\"></param><embed src=\"http://media.imeem.com/m/OfasYbCCNZ/aus=false/\" type=\"application/x-shockwave-flash\" width=\"300\" height=\"110\" wmode=\"transparent\"></embed><a href=\"http://www.imeem.com/people/Q-SRsZ/music/1S1DHU-5/i_dil_uyahoocom_tanhai/\">Tanhai - i_dil_u@yahoo.com</a></object>"
		 * ; tumblr.postAudio( embedCode, "Ustad NFAK - Tanhai", tags,
		 * "2008-07-04");
		 * 
		 * embedCode =
		 * "<object width=\"425\" height=\"344\"><param name=\"movie\" value=\"http://www.youtube.com/v/J20UuIDylAg&hl=en&fs=1\"></param><param name=\"allowFullScreen\" value=\"true\"></param><embed src=\"http://www.youtube.com/v/J20UuIDylAg&hl=en&fs=1\" type=\"application/x-shockwave-flash\" allowfullscreen=\"true\" width=\"425\" height=\"344\"></embed></object>"
		 * ; tumblr.postVideo( embedCode, "Landing in Chennai", tags,
		 * "2008-07-04");
		 */

		// reading stuff
		TumbleJ tumblr = new TumbleJ(args[0]);
		Post reads[] = tumblr.readPosts();

		for (int i = 0; i < reads.length; i++) {
			System.out.println("Post[" + i + "] title = " + reads[i].getTitle()
					+ " body = " + reads[i].getBody());
		}

	}
	
	/**
	 * Perform 3-legged OAuth authorization. 
	 * @return URL needed to continue authorization.
	 */
//	private URL authorize() {
//		OAuthParameters parameters = new OAuthParameters().consumerKey(consumerKey);
//		OAuthSecrets secrets = new OAuthSecrets().consumerSecret(consumerSecret);
//		return null;
//	}
	
	/**
	 * Return all posts of the blog.
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray getPosts() throws JSONException {
		logger.info("Get Posts from " + baseHostname);
		WebResource resource = client.resource("http://api.tumblr.com/v2/blog/" + baseHostname + "/posts");
		JSONObject response = resource.get(JSONObject.class);
		JSONArray posts = response.getJSONObject("response").getJSONArray("posts");
		return posts;
	}

	public void init() {
		logger.info("Initializing TumbleJ");
		client = new Client();
		client.addFilter(new LoggingFilter());
	}
	
	private void prepareOauth(WebResource resource) {
		OAuthParameters parameters = new OAuthParameters().consumerKey(consumerKey).token(token);//.signatureMethod("PLAINTEXT");
		OAuthSecrets secrets = new OAuthSecrets().consumerSecret(consumerSecret).tokenSecret(tokenSecret);
		OAuthClientFilter oauthFilter = new OAuthClientFilter(client.getProviders(), parameters, secrets);
		resource.addFilter(oauthFilter);
	}

	// parameters for reading posts
	private String baseHostname = "";
	private String numberOfPosts = "";
	private String startPostNo = "";
	private String postType = "";

	// format
	private static final String ENCODING = "UTF-8";

	// URL
	private static final String TUMBLR_WRITE_URL = "http://www.tumblr.com/api/write";

	// literals
	private static final String POST_TEXT = "text";
	private static final String POST_QUOTE = "quote";
	private static final String POST_LINK = "link";
	private static final String POST_CHAT = "chat";
	private static final String POST_VIDEO = "video";
	private static final String POST_AUDIO = "audio";
	private Client client;

	/**
	 * @return the consumerKey
	 */
	public String getConsumerKey() {
		return consumerKey;
	}

	/**
	 * @param consumerKey the consumerKey to set
	 */
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	/**
	 * @return the consumerSecret
	 */
	public String getConsumerSecret() {
		return consumerSecret;
	}

	/**
	 * @param consumerSecret the consumerSecret to set
	 */
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the tokenSecret
	 */
	public String getTokenSecret() {
		return tokenSecret;
	}

	/**
	 * @param tokenSecret the tokenSecret to set
	 */
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

}