/**
 * Copyright [2009] [PLOIN GmbH -> http://www.ploin.de]
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
package org.ploin.pmf;

import java.util.Map;

/**
 * Description: This interface offers a method to replace all string-snippets <br>
 * which are matching the keys of the replaceMap.<br>
 * <p/>
 * $LastChangedBy: r.reiz $<br>
 * $Revision: 77 $<br>
 * $Date: 2010-03-15 13:13:45 +0100 (Mon, 15 Mar 2010) $<br>
 */
public interface IMailReplacer {

	/**
	 * This method replaces all string-snippets which are matching the keys of 
	 * the replaceMap with the values of the replaceMap. 
	 * 
	 * @param mail - the email-template
	 * @param replaceMap - the given map by the user
	 * @return a new email-text
	 */
	public String replace(String mail, Map<String, String> replaceMap) throws MailFactoryException;

}
