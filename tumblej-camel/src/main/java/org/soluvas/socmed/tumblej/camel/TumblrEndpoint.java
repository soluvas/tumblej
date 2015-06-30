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
package org.soluvas.socmed.tumblej.camel;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * Represents a TumbleJ endpoint.
 */
public class TumblrEndpoint extends DefaultEndpoint {

	public static enum PostType { text, photo, quote, link, chat, audio, video };
	
	private String consumerKey;
	private String consumerSecret;
	private String baseHostname = "";
	private String token;
	private String tokenSecret;
	
    public TumblrEndpoint() {
        super();
    }

    public TumblrEndpoint(String uri, TumblrComponent component) {
        super(uri, component);
    }

    public Producer createProducer() throws Exception {
        return new TumblrProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        throw new UnsupportedOperationException("Consuming from Tumblr blogs is not implemented.");
    }

    public boolean isSingleton() {
        return true;
    }

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
	 * @return the baseHostname
	 */
	public String getBaseHostname() {
		return baseHostname;
	}

	/**
	 * @param baseHostname the baseHostname to set
	 */
	public void setBaseHostname(String baseHostname) {
		this.baseHostname = baseHostname;
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
