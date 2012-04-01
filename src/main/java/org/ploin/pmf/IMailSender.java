/**
 * Copyright [2009] [PLOIN GmbH -> http://www.ploin-gmbh.de]
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

import org.ploin.pmf.entity.MailConfig;
import org.ploin.pmf.entity.SendingResult;
import org.ploin.pmf.entity.ServerConfig;

import java.util.Map;

/**
 * Description: This class is responsible for sending the e-mails
 * <br/> 
 * <p/>
 * $LastChangedBy: r.reiz $<br>
 * $Revision: 77 $<br>
 * $Date: 2010-03-15 13:13:45 +0100 (Mon, 15 Mar 2010) $<br>
 */
public interface IMailSender {

	/**
	 * This method sends the mail.
	 * 
	 * @param plain - the plain mail message.
	 * @param html - the html mail message.
	 * @param mailConfig - the mail config object.
	 * @param serverConfig - the server config object.
	 * @param map - the map containing embeds and attachements 
	 * @return An Object array with two elements. The first Element is the String given back
	 *         by the commons-email API email.send(). The second Element is the send mail
	 *         as MimeMessage. 
	 */
	SendingResult sendMail(String plain, String html, MailConfig mailConfig, ServerConfig serverConfig, Map<String, String> map) throws MailFactoryException;

	/**
	 *
	 * @return the PropertiesLoader used to read the config properties file.
	 */
	IPropertiesLoader getPropertiesLoader();

	/**
	 * Set the ProperitesLoader which should be used to read properties from
	 * the config files. 
	 *
	 *
	 * @param propertiesLoader - the new PropertiesLoader
	 */
	void setPropertiesLoader(IPropertiesLoader propertiesLoader);

}