/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.fast.maven.plugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.esigate.Driver;
import org.esigate.DriverFactory;
import org.esigate.HttpErrorPage;

/**
 * StaticDriver is initialized with API
 * 
 * @author Alexis Thaveau
 * @author Nicolas Richeton
 */
public class StaticDriver extends Driver {
	private final Map<String, String> resources = new HashMap<String, String>();

	public StaticDriver(String name, Properties props) {
		super(name, props);
		DriverFactory.put(name, this);		
	}

	/**
	 * Add Resource with default charset ISO-8859-1
	 * @param relUrl
	 * @param content
	 * @throws IOException 
	 */
	public void addResource(String relUrl, String content) throws IOException {
		this.addResource(relUrl, content,"ISO-8859-1");
	}
	
	/**
	 * Add Resource
	 * @param relUrl
	 * @param content
	 * @param charset
	 * @throws IOException 
	 */
	public void addResource(String relUrl, String content, String charset) throws IOException {
		resources.put(relUrl, content);
	}

	@Override
	protected String getResourceAsString(String relUrl, HttpEntityEnclosingRequest httpRequest)
			throws HttpErrorPage {
		String result = resources.get(relUrl);
		if( result == null )
			throw new HttpErrorPage(500, "Internal server error", "Template not found");
		
		return  result; 
	}

	
	public void initHttpRequestParams(HttpRequest request, Map<String, String> parameters) throws HttpErrorPage {
		// No request will be sent, so no need to init.
	}
}
