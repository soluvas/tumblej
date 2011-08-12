package com.daneshzaki.tumblej;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <b>TumbleJ</b> is a Java API for Tumblr. It aims to perform posting and retrieving content. 
 *
 * @author <a href="http://www.daneshzaki.com">Danesh Zaki</a> <p />
 * 
 * Revised on 3 July 2008 <p />
 *
 * Version 1.1 <p /> 
 *
 * This code is released as
 * open-source under the LGPL license. See
 * <a href="http://www.gnu.org/licenses/lgpl.html">http://www.gnu.org/licenses/lgpl.html</a>
 * for license details. This code comes with no warranty or support.
 *
 *
 */

public class TumbleJ
{

	/**
	 * This constructor should be used for reading posts from the tumblog URL. 
	 * @param tumblogUrl tumblelog URL
	 */
	
	public TumbleJ(String tumblogUrl)  throws Exception
	{
		this.tumblogUrl = tumblogUrl;

	}

	/**
	 * This constructor sets the username and password for writing posts.
	 * @param username email ID used as username 
	 * @param password password 	 	 
	 */
	
	public TumbleJ(String username, String password)  
	{
		this.username = username;
		this.password = password;

	}

	/**
	 * This method sets the tumblelog URL for reading posts
	 * @param tumblogUrl tumblelog URL	 
	 */

	public void setTumblogUrl(String tumblogUrl)
	{
		this.tumblogUrl = tumblogUrl;	
	}

	/**
	 * This method sets the numberOfPosts to be read
	 * @param numberOfPosts numberOfPosts to be read 
	 */

	public void setNumberOfPosts(String numberOfPosts)
	{
		this.numberOfPosts = numberOfPosts;	
	}

	/**
	 * This method sets the post number from where posts are to be read
	 * @param startPostNo tumblelog URL	 
	 */

	public void setStartPostNo(String startPostNo)
	{
		this.startPostNo = startPostNo;	
	}

	/**
	 * This method sets the type of posts to be read
	 * @param postType type of posts to be read
	 */

	public void setPostType(String postType)
	{
		this.postType = postType;	
	}




	/**
	 * This method writes text posts to the Tumblr tumblog. 
	 * @param postTitle title of the post
	 * @param body body of the post 	 
	 * @param tags tags for the post 	 
	 * @param date date of the post
	 */
	 
	public void postText(String title, String body, String tags, String date) throws Exception
	{
		String[] textPostParams = new String[4];

		textPostParams[0]= "&title=";
		textPostParams[1]= title;
		textPostParams[2]= "&body=";
		textPostParams[3]= body;
	
		 
		//call post method
		post(POST_REGULAR_TEXT, tags, date, textPostParams);
	}
	

	/**
	 * This method writes quote posts to the Tumblr tumblog. 
	 * @param quote quote to post
	 * @param source source of quote 	 
	 * @param tags tags for the post 	 
	 * @param date date of the post
	 */
	 
	public void postQuote(String quote, String source, String tags, String date) throws Exception
	{
		String[] quotePostParams = new String[4];

		quotePostParams[0]= "&quote=";
		quotePostParams[1]= quote;
		quotePostParams[2]= "&source=";
		quotePostParams[3]= source;
	
		 
		//call post method
		post(POST_QUOTE, tags, date, quotePostParams);
	}


	/**
	 * This method writes link posts to the Tumblr tumblog. Link description is not yet supported. 
	 * @param name name of URL
	 * @param url link to post 	
	 * @param tags tags for the post 	 
	 * @param date date of the post
	 */
	 
	public void postLink(String name, String url, String tags, String date) throws Exception
	{
		String[] linkPostParams = new String[4];

		linkPostParams[0]= "&name=";
		linkPostParams[1]= name;
		linkPostParams[2]= "&url=";
		linkPostParams[3]= url;
	
		 
		//call post method
		post(POST_LINK, tags, date, linkPostParams);
	}

	/**
	 * This method writes conversation posts to the Tumblr tumblog. 
	 * @param title title of conversation
	 * @param conversation conversation to post
	 * @param tags tags for the post 	 
	 * @param date date of the post
	 */
	 
	public void postConversation(String title, String conversation, String tags, String date) throws Exception
	{
		String[] conversationPostParams = new String[4];

		conversationPostParams[0]= "&title=";
		conversationPostParams[1]= title;
		conversationPostParams[2]= "&conversation=";
		conversationPostParams[3]= conversation;
	
		 
		//call post method
		post(POST_CONVERSATION, tags, date, conversationPostParams);
	}

	/**
	 * This method writes audio posts to the Tumblr tumblog. Since file uploads are not yet supported, it embeds audio URLs as video posts. 
	 * @param audioFileURL Web-site URL of the audio file 
	 * @param caption caption for the audio file
	 * @param tags tags for the post 	 
	 * @param date date of the post
	 */
	 
	public void postAudio(String audioFileURL, String caption, String tags, String date) throws Exception
	{
		//workaround: calling postVideo method as file uploads are not yet implemented
		postVideo(audioFileURL, caption, tags, date);
	}

	/**
	 * This method writes video posts to the Tumblr tumblog. File uploads are not yet supported.
	 * @param videoFileURL Web-site URL of the video file or HTML code for embedding video 
	 * @param caption caption for the video file
	 * @param tags tags for the post 	 
	 * @param date date of the post
	 */
	 
	public void postVideo(String videoFileURL, String caption, String tags, String date) throws Exception
	{
		String[] videoPostParams = new String[4];

		videoPostParams[0]= "&embed=";
		videoPostParams[1]= videoFileURL;
		videoPostParams[2]= "&caption=";
		videoPostParams[3]= caption;
	
		 
		//call post method
		post(POST_VIDEO, tags, date, videoPostParams);
	}
	
	/**
	 * This method writes posts to the Tumblr tumblog. It is recommended to use the specific post methods as the array parameters are set in those methods.
	 * @param type presently set to "regular" for text posts 	 
	 * @param tags tags for the post 	 
	 * @param date date of the post
	 * @param specificParams array containing parameters for different type of posts
	 */
	 
	public void post(String type, String tags, String date, String[] specificParams) throws Exception
	{
	
		openConnection(TUMBLR_WRITE_URL);

		//do a HTTP POST with the parameters
		printout = new DataOutputStream (connection.getOutputStream ());				

		//add content with the parameters
		StringBuffer content = new StringBuffer();
		
		//set initial parameters for post
		content = setInitialParameters(content, type);
		
		//set post specific parameters	
		content = setPostSpecificParameters(content, specificParams);

		//set optional parameters for post
		content = setOptionalParameters(content, tags, date);
	
		System.out.println("HTTP Request:\n\n"+ content.toString());


		//write to the URL stream
		printout.writeBytes (content.toString());
		printout.flush ();
		printout.close ();
		
		//get the status
		System.out.println("HTTP Response:\n\n"+getStatus());
		
	}

	//This method sets the initial parameters for posting
	
	private StringBuffer setInitialParameters(StringBuffer content, String type) throws Exception
	{
	
		content.append("email=");
		content.append(URLEncoder.encode (username, ENCODING));

		content.append("&password=");
		content.append(URLEncoder.encode (password, ENCODING));

		content.append("&type=");
		content.append(URLEncoder.encode (type, ENCODING));
		
		return content;
		
	}


	//This method sets the parameters specific to the post
		 
	private StringBuffer setPostSpecificParameters(StringBuffer content, String[] specificParams) throws Exception
	{
		content.append(specificParams[0]);
		content.append(URLEncoder.encode (specificParams[1], ENCODING));

		content.append(specificParams[2]);
		content.append(URLEncoder.encode (specificParams[3], ENCODING));
		
		return content;
		
	}


	//This method sets the optional parameters 
		 
	private StringBuffer setOptionalParameters(StringBuffer content, String tags, String date) throws Exception
	{
		content.append("&tags=");
		content.append(URLEncoder.encode (tags, ENCODING));

		content.append("&date=");
		content.append(URLEncoder.encode (date, ENCODING));
		
		return content;
		
	}

	

	 //This method opens the connection to the Tumblr URL
	
	private void openConnection(String tumblrUrl) throws Exception
	{
		URL url;
		
		// URL of tumblr.com
		url = new URL (tumblrUrl);
		
		connection = url.openConnection();
		
		//to get the status of the write operation
		connection.setDoInput (true);
		
		//to write the post to the URL
		connection.setDoOutput (true);
		
		//instruction not to use cache but the fresh connection
		connection.setUseCaches (false);
		
		//encode the contents of the form
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	
		
	}



	//This method gets the status of the write operation
	 
	private String getStatus() throws Exception
	{
		BufferedReader     input;

		//get the response 
		input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		String status = null;
		
		status = input.readLine();

		input.close ();	
			
		return status;
	
	}
		

	/**
	 * This method gets the posts from the tumblelog and returns an array of Posts 
	 */
	 
	public Post[] readPosts() throws Exception
	{

		//setup parser
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		//get posts at URL
		Document document = builder.parse(tumblogUrl+"/api/read");
		NodeList postNodes = document.getElementsByTagName("post");
		
		//create a post array to store posts
		Post[] posts = new Post[postNodes.getLength()];
		
		//get each post
		for(int i=0;i<postNodes.getLength();i++)
		{
			
			//parse post nodes to get contents 
			Node node = postNodes.item(i);		
			NodeList nodes = node.getChildNodes();
			
			posts[i] = new Post();
			
			//get post contents
			for(int k=0;k<nodes.getLength();k++)
			{
				Node topNode = nodes.item(k);								
				
				if(topNode.getNodeName().equals("regular-title"))
				{
					posts[i].setTitle(topNode.getFirstChild().getNodeValue());
					
				}
				else if(topNode.getNodeName().equals("regular-body"))
				{
					//set post body
					posts[i].setBody(topNode.getFirstChild().getNodeValue());

				}						
				
			}
		}
				
		return posts; 
	
	}
		

	//main method to use the API
	public static void main(String args[]) throws Exception
	{
		//writing stuff
		/*
		String tags = "TumbleJ, API, Java";
		String date = new Date().toString();
		 
		TumbleJ tumblr = new TumbleJ(args[0], args[1]);
		tumblr.postText( "Testing the TumbleJ API", "Posting some random text using the TumbleJ API... ",  tags, date);	
		tumblr.postQuote( "It has become appallingly obvious that our technology has exceeded our humanity. ", "Albert Einstein", tags, date);	
		tumblr.postLink( "API Dashboard: ProgrammableWeb", "http://www.programmableweb.com/apis", tags, date);	
	
		String embedCode = "<object width=\"300\" height=\"80\"><param name=\"movie\" value=\"http://media.imeem.com/m/OfasYbCCNZ/aus=false/\"></param><param name=\"wmode\" value=\"transparent\"></param><embed src=\"http://media.imeem.com/m/OfasYbCCNZ/aus=false/\" type=\"application/x-shockwave-flash\" width=\"300\" height=\"110\" wmode=\"transparent\"></embed><a href=\"http://www.imeem.com/people/Q-SRsZ/music/1S1DHU-5/i_dil_uyahoocom_tanhai/\">Tanhai - i_dil_u@yahoo.com</a></object>";
		tumblr.postAudio( embedCode, "Ustad NFAK - Tanhai", tags, "2008-07-04");	
		
		embedCode = "<object width=\"425\" height=\"344\"><param name=\"movie\" value=\"http://www.youtube.com/v/J20UuIDylAg&hl=en&fs=1\"></param><param name=\"allowFullScreen\" value=\"true\"></param><embed src=\"http://www.youtube.com/v/J20UuIDylAg&hl=en&fs=1\" type=\"application/x-shockwave-flash\" allowfullscreen=\"true\" width=\"425\" height=\"344\"></embed></object>";
		tumblr.postVideo( embedCode, "Landing in Chennai", tags, "2008-07-04");	
		*/
		
		//reading stuff
		TumbleJ tumblr = new TumbleJ(args[0]);
		Post reads[] = tumblr.readPosts();
		
		for(int i=0;i<reads.length;i++)
		{
			System.out.println("Post["+i+"] title = "+reads[i].getTitle()+" body = "+reads[i].getBody());
		}

			
	}	
	

	//connection to Tumblr
	private URLConnection connection = null;
	
	private DataOutputStream printout = null;				
	
	//credentials
	private String username = "";
	
	private String password = "";

	//parameters for reading posts
	private String tumblogUrl = "";
	private String numberOfPosts = "";	
	private String startPostNo = "";		
	private String postType = "";		


			 
	//format
	private static final String ENCODING = "UTF-8";
	
	//URL
	private static final String TUMBLR_WRITE_URL = "http://www.tumblr.com/api/write";
	
	//literals
	private static final String POST_REGULAR_TEXT = "regular";
	private static final String POST_QUOTE = "quote";
	private static final String POST_LINK	= "link";
	private static final String POST_CONVERSATION	= "conversation";
	private static final String POST_VIDEO	= "video";		
	private static final String POST_AUDIO	= "audio";	
	
	
}