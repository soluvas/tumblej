package com.daneshzaki.tumblej;

/**
 * This class is used to encapsulate the posts read from the tumblog. Presently, only regular text posts are supported
 *
 * @author <a href="http://www.daneshzaki.com">Danesh Zaki</a> <p />
 * 
 * Created on 11 July 2008 <p />
 *
 * Version 1.0 <p /> 
 *
 * This code is released as
 * open-source under the LGPL license. See
 * <a href="http://www.gnu.org/licenses/lgpl.html">http://www.gnu.org/licenses/lgpl.html</a>
 * for license details. This code comes with no warranty or support.
 *
 *
 */
public class Post
{

	/**
	 * Create a post with empty title and body 
	 */
	
	public Post()  
	{

	}

	/**
	 * Create a post with title and body 
	 * @param title title of the post	 
	 * @param body body of the post
	 */
	
	public Post(String title, String body)  
	{
		this.title = title;
		this.body = body;

	}


	/**
	 * This method sets the title of the post
	 * @param title title of the post	 
	 */

	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * This method sets the body of the post
	 * @param body body of the post
	 */

	public void setBody(String body)
	{
		this.body = body;
	}
	
	/**
	 * This method gets the title of the post
	 */

	public String getTitle()
	{
		return title;
	}

	/**
	 * This method gets the body of the post
	 */

	public String getBody()
	{
		return body;
	}
	
	//title of the post
	private String title = "";
	
	//body of the post
	private String body = "";
	
}