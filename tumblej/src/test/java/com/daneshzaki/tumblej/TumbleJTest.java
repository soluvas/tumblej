/**
 * 
 */
package com.daneshzaki.tumblej;

import java.util.Properties;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ceefour
 *
 */
public class TumbleJTest {

	private TumbleJ tj;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Properties props = new Properties();
		props.load(getClass().getResourceAsStream("/tumblej.properties"));
		tj = new TumbleJ(props.getProperty("consumer.key"), props.getProperty("consumer.secret"),
				props.getProperty("base.hostname"),
				props.getProperty("token"), props.getProperty("token.secret"));
		tj.init();
	}

	/**
	 * Test method for {@link com.daneshzaki.tumblej.TumbleJ#getPosts()}.
	 * @throws JSONException 
	 */
	@Test
	public void testGetPosts() throws JSONException {
		assertNotNull(tj.getPosts());
	}
	
	@Test
	public void testPostQuote() throws Exception {
		// response: {"meta":{"status":201,"msg":"Created"},"response":{"id":8868275314}}
		JSONObject response = tj.postQuote("Keren<br>Bo'!", "Gadis Cantik", "malam,bikin,happy", null);
		assertNotNull(response);
		assertEquals(201, response.getJSONObject("meta").get("status"));
		assertNotNull(response.getJSONObject("response").get("id"));
	}

	@Test
	public void testPostText() throws Exception {
		// response: {"meta":{"status":201,"msg":"Created"},"response":{"id":8868275314}}
		JSONObject response = tj.postText("Sebuah Kisah Cinta",
				"Di suatu malam abdi bertemu gadis geulis...",
				"malam,bikin,happy", null);
		assertNotNull(response);
		assertEquals(201, response.getJSONObject("meta").get("status"));
		assertNotNull(response.getJSONObject("response").get("id"));
	}

}
