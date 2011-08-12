/**
 * 
 */
package com.daneshzaki.tumblej;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
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
		Assert.assertNotNull(tj.getPosts());
	}
	
	@Test
	public void testPostQuote() throws Exception {
		Assert.assertNotNull(tj.postQuote("Keren<br>Bo'!", "Gadis Cantik", "malam,bikin,happy", null));
	}

}
