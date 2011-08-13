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

import java.util.Properties;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.language.ConstantExpression;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class TumbleJComponentTest extends CamelTestSupport {

    @Test
    public void testTimerInvokesBeanMethod() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);       
        
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
    	Properties props = new Properties();
		props.load(getClass().getResourceAsStream("/tumblej.properties"));
		final String uri = "tumblej:" + props.getProperty("base.hostname") + "?consumerKey=" + props.getProperty("consumer.key")
				+ "&consumerSecret=" + props.getProperty("consumer.secret")
				+ "&token=" + props.getProperty("token") + "&tokenSecret=" + props.getProperty("token.secret");
        return new RouteBuilder() {
            public void configure() {
            	from("timer:2000").setBody(new ConstantExpression("Mojang geulis bikin lieur euy"))
            		.setHeader("type", new ConstantExpression("quote"))
            		.setHeader("source", new ConstantExpression("Kev"))
            		.to(uri).to("mock:result");
//                from("helloworld://foo")    // will send a message every 500ms
//                  .to("helloworld://bar")   // prints message to stdout
//                  .to("mock:result");       // to actually test that a message arrives
            }
        };
    }
}
