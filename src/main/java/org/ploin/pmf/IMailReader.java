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

import org.ploin.pmf.entity.TemplateConfig;

/**
 * Description: This Interface offers methods for reading the template files.
 * <p/>
 * $LastChangedBy: r.reiz $<br>
 * $Revision: 77 $<br>
 * $Date: 2010-03-15 13:13:45 +0100 (Mon, 15 Mar 2010) $<br>
 */
public interface IMailReader {

	/**
	 * This method returns the plain-mail template as String for a given mailName.
	 * If there is no plain-mail-template, the method returns null. 
	 * 
	 * @param templateConfig containing client, locale, template-name and map.
	 * @return String or NULL
	 * @throws Exception
	 */
	public String getPlainMail(TemplateConfig templateConfig) throws MailFactoryException;

	/**
	 * This method returns the html-mail-template to the mailName as String.
	 * If there is no html-mail-template the method returns null.
	 * 
	 * @param templateConfig containing client, locale, template-name and map.
	 * @return String or NULL
	 * @throws Exception
	 */
	public String getHtmlMail(TemplateConfig templateConfig) throws MailFactoryException;

	/**
	 * Returns the directory where the email-templates and the clients are in.
	 * 
	 * @return String
	 */
	public String getDirectory();

	/**
	 * Set teh directory where the email-templates and the clients are in.
	 * 
	 * @param directory
	 */
	public void setDirectory(String directory);
	
	/**
	 * returns the html extension. 
	 * 
	 * @return
	 */
	public String getHtmlExtension();

	/**
	 * Set the html extension. The standard is ".html"
	 * 
	 * @param htmlExtension
	 */
	public void setHtmlExtension(String htmlExtension);
	
	/**
	 * returns the plain extension. 
	 * 
	 * @return
	 */
	public String getPlainExtension();

	/**
	 * Set the plain extension. The standard is ".txt"
	 * 
	 * @param plainExtendsion
	 */
	public void setPlainExtension(String plainExtendsion);
	
}
