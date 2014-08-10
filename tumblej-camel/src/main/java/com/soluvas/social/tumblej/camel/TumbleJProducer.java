/**
 * Copyright (C) 2011 Soluvas. All Rights Reserved.
 * 
 * Soluvas licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.soluvas.social.tumblej.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daneshzaki.tumblej.TumbleJ;
import com.soluvas.social.tumblej.camel.TumbleJEndpoint.PostType;

/**
 * The TumbleJ producer.
 */
public class TumbleJProducer extends DefaultProducer {
    private static final transient Logger LOG = LoggerFactory.getLogger(TumbleJProducer.class);
    private final TumbleJEndpoint endpoint;
    private final TumbleJ tumbleJ;

    public TumbleJProducer(TumbleJEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
        tumbleJ = new TumbleJ(endpoint.getConsumerKey(), endpoint.getConsumerSecret(), 
        		endpoint.getBaseHostname(), endpoint.getToken(), endpoint.getTokenSecret());
        tumbleJ.init();
    }

    @Override
	public void process(Exchange exchange) throws Exception {
    	Message inMsg = exchange.getIn();
    	PostType type = inMsg.getHeader("type", PostType.text, PostType.class);
    	String tags = inMsg.getHeader("tags", String.class);
    	String date = inMsg.getHeader("date", String.class);
    	Object body = inMsg.getBody();
    	LOG.info("Host: {}", endpoint.getBaseHostname());
    	LOG.info("Type: {}", type);
    	JSONObject response;
    	switch (type) {
    	case text:
    		String title = inMsg.getHeader("title", String.class);
    		response = tumbleJ.postText(title, (String)body, tags, date);
    		break;
    	case photo:
    		String caption = inMsg.getHeader("caption", String.class);
    		String source = inMsg.getHeader("source", body, String.class);
    		String link = inMsg.getHeader("link", String.class);
    		response = tumbleJ.postPhoto(caption, source, link, tags, date);
    		break;
    	case link:
    		String linkTitle = inMsg.getHeader("title", String.class);
    		String url = inMsg.getHeader("url", body, String.class);
    		String description = inMsg.getHeader("description", String.class);
    		response = tumbleJ.postLink(linkTitle, url, description, tags, date);
    		break;
    	case quote:
    		String quote = inMsg.getHeader("quote", body, String.class);
    		String quoteSource = inMsg.getHeader("source", String.class);
    		response = tumbleJ.postQuote(quote, quoteSource, tags, date);
    		break;
    	case chat:
    		String chatTitle = inMsg.getHeader("title", String.class);
    		String conversation = inMsg.getHeader("conversation", body, String.class);
    		response = tumbleJ.postChat(chatTitle, conversation, tags, date);
    		break;
    	case audio:
    		String audioCaption = inMsg.getHeader("caption", String.class);
    		String externalUrl = inMsg.getHeader("external_url", body, String.class);
    		response = tumbleJ.postAudio(externalUrl, audioCaption, tags, date);
    		break;
    	case video:
    		String videoCaption = inMsg.getHeader("caption", String.class);
    		String embed = inMsg.getHeader("embed", body, String.class);
    		response = tumbleJ.postVideo(embed, videoCaption, tags, date);
    		break;
		default:
			throw new IllegalArgumentException("Unknown post type " + type);
    	}
    	Message out = exchange.getOut();
    	out.setHeader("meta.status", response.getJSONObject("meta").get("status"));
    	out.setHeader("meta.msg", response.getJSONObject("meta").get("msg"));
    	out.setBody(response.get("response"));
    }

}
