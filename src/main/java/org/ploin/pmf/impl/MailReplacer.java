/**
 * Copyright [20098] [PLOIN GmbH -> http://www.ploin.de]
 * 
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
package org.ploin.pmf.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ploin.pmf.IMailReplacer;
import org.ploin.pmf.MailFactoryException;

import java.io.Serializable;
import java.util.Map;

/**
 * Description: Implementation of the {@link IMailReplacer} interface.<br>
 * This class offers a method to replace all string-snippets which are
 * matching the keys of the replaceMap.<br/>
 * <p/>
 * $LastChangedBy: j.schwarz $<br>
 * $Revision: 76 $<br>
 * $Date: 2010-03-04 14:45:28 +0100 (Thu, 04 Mar 2010) $<br>
 */
public class MailReplacer implements Serializable, IMailReplacer{

	private static final long serialVersionUID = 4989576818526924877L;

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(MailReplacer.class);

	/**
	 * Every occurence of each key in the templateText is replaced by the corresponding value.<br>
	 * <br>
	 * There are two "kinds" of keys you can use:<br>
	 * * Keys of the (old) form ":key" will trigger the replacement of all occurences of ":key" in the templateText. <br>
	 * * Keys without starting ":" like "key" will trigger the replacement of all occurences of "#{key}" in the templateText.
	 */
	public String replace(String templateText, final Map<String, String> replaceMap) throws MailFactoryException {

		try {
			String text = templateText;
			
			for (String key : replaceMap.keySet()) {
				String value = (String) replaceMap.get(key);
				
				if (!(key.startsWith(":"))) 
					key = "#\\{" + key + "\\}";
				
				text = text.replaceAll(key, (String) value);
			}
			
			return text;
			
		} catch (Exception e) {
			throw new MailFactoryException(e);
		}
	}
}
