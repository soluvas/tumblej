/**
 * 
 */
package com.daneshzaki.tumblej;

import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
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
	public void testPostText() throws Exception {
		// response: {"meta":{"status":201,"msg":"Created"},"response":{"id":8868275314}}
		JSONObject response = tj.postText("Sebuah Kisah Cinta",
				"Di suatu malam abdi bertemu gadis geulis...",
				"malam,bikin,happy", null);
		assertNotNull(response);
		assertEquals(201, response.getJSONObject("meta").get("status"));
		assertNotNull(response.getJSONObject("response").get("id"));
	}

	/**
	 * From http://wiwiadawiyah.tumblr.com/post/8731316169
	 * http://30.media.tumblr.com/tumblr_lk5tyx3Zqo1qb67gho1_500.jpg
	 * @throws Exception
	 */
	@Test
	public void testPostPhoto() throws Exception {
		// response: {"meta":{"status":201,"msg":"Created"},"response":{"id":8868275314}}
		JSONObject response = tj.postPhoto("Smile",
				"http://30.media.tumblr.com/tumblr_lk5tyx3Zqo1qb67gho1_500.jpg",
				"Senyum itu indah dan menyenangkan, apalagi dari mojang geulis :)",
				"smile,senyum", null);
		assertNotNull(response);
		assertEquals(201, response.getJSONObject("meta").get("status"));
		assertNotNull(response.getJSONObject("response").get("id"));
	}

	/**
	 * For some strange reason, this immediately triggers
	 * an 401 Not Authorized. :-(
	 * However, old access token is still usable for doing non-upload posts.
	 * @throws Exception
	 */
	@Test
	public void testPostPhotoData() throws Exception {
		// response: {"meta":{"status":201,"msg":"Created"},"response":{"id":8868275314}}
		byte[] data = IOUtils.toByteArray(getClass().getResourceAsStream("/apache-maven_logo.gif"));
		JSONObject response = tj.postPhotoData("Apache Maven",
				data, "Build system that everybody loves and hates.",
				"apache,maven,build,system,project", null);
		assertNotNull(response);
		assertEquals(201, response.getJSONObject("meta").get("status"));
		assertNotNull(response.getJSONObject("response").get("id"));
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
	public void testPostLink() throws Exception {
		// response: {"meta":{"status":201,"msg":"Created"},"response":{"id":8868275314}}
		JSONObject response = tj.postLink("GaulDong", "http://www.gauldong.net/",
				"Situs Gaul pualing yahuuuuud",
				"gaul,keren,cantik,geulis", null);
		assertNotNull(response);
		assertEquals(201, response.getJSONObject("meta").get("status"));
		assertNotNull(response.getJSONObject("response").get("id"));
	}

	@Test
	public void testPostChat() throws Exception {
		// response: {"meta":{"status":201,"msg":"Created"},"response":{"id":8868275314}}
		JSONObject response = tj.postChat("Aku dan Dia",
			"Hendy kasep: Kamu sayang aku ngga sich?\n" +
			"Neng geulis: Suayaaaaang pisan!\n" +
			"Hendy kasep: Ah masaaaa... Nuhun nya'!",
			"cinta,sayang,cantik,geulis", null);
		assertNotNull(response);
		assertEquals(201, response.getJSONObject("meta").get("status"));
		assertNotNull(response.getJSONObject("response").get("id"));
	}

	@Test
	public void testPostAudio() throws Exception {
		// response: {"meta":{"status":201,"msg":"Created"},"response":{"id":8868275314}}
		JSONObject response = tj.postAudio(
				"http://freemusicarchive.org/music/download/e381bd7ab67b8d06016f22bbea433740ad61d14f",
				"Sure (by Poland)", "sure,poland", null);
		assertNotNull(response);
		assertEquals(201, response.getJSONObject("meta").get("status"));
		assertNotNull(response.getJSONObject("response").get("id"));
	}
	
	/**
	 * Requires Ubuntu package 'ubuntu-sounds'.
	 * /usr/share/sounds/ubuntu/stereo/phone-incoming-call.ogg
	 * @throws Exception
	 */
//	@Test
//	public void testPostAudioData() throws Exception {
//		// response: {"meta":{"status":201,"msg":"Created"},"response":{"id":8868275314}}
//		byte[] data = FileUtils.readFileToByteArray(new File("/usr/share/sounds/purple/alert.wav"));
//		JSONObject response = tj.postAudioData(data, "Phone incoming call", "phone,call", null);
//		assertNotNull(response);
//		assertEquals(201, response.getJSONObject("meta").get("status"));
//		assertNotNull(response.getJSONObject("response").get("id"));
//	}
	
	/**
	 * From http://wiwiadawiyah.tumblr.com/post/8859456571
	 * <object width="640" height="390"><param name="movie" value="http://www.youtube.com/v/Pmw8UPKHbqs&rel=0&hl=en_US&feature=player_embedded&version=3"></param><param name="allowFullScreen" value="true"></param><param name="allowScriptAccess" value="always"></param><embed src="http://www.youtube.com/v/Pmw8UPKHbqs&rel=0&hl=en_US&feature=player_embedded&version=3" type="application/x-shockwave-flash" allowfullscreen="true" allowScriptAccess="always" width="640" height="390"></embed></object>
	 * 
	 * Hmm... when tried on Aug 13, 2011, Tumblr returned
	 * 500 Internal Server Error? :-(
	 * 
	 * @throws Exception
	 */
	@Test
	public void testPostVideo() throws Exception {
		// response: {"meta":{"status":201,"msg":"Created"},"response":{"id":8868275314}}
		JSONObject response = tj.postVideo(
			"<object width=\"640\" height=\"390\"><param name=\"movie\" value=\"http://www.youtube.com/v/Pmw8UPKHbqs&rel=0&hl=en_US&feature=player_embedded&version=3\"></param><param name=\"allowFullScreen\" value=\"true\"></param><param name=\"allowScriptAccess\" value=\"always\"></param><embed src=\"http://www.youtube.com/v/Pmw8UPKHbqs&rel=0&hl=en_US&feature=player_embedded&version=3\" type=\"application/x-shockwave-flash\" allowfullscreen=\"true\" allowScriptAccess=\"always\" width=\"640\" height=\"390\"></embed></object>",
			"Aku Peduli",
			"peduli,kasih", null);
		assertNotNull(response);
		assertEquals(201, response.getJSONObject("meta").get("status"));
		assertNotNull(response.getJSONObject("response").get("id"));
	}

}
