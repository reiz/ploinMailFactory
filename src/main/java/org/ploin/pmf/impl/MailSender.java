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
package org.ploin.pmf.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.ploin.pmf.IMailSender;
import org.ploin.pmf.IPropertiesLoader;
import org.ploin.pmf.MailFactoryException;
import org.ploin.pmf.entity.MailConfig;
import org.ploin.pmf.entity.Recipient;
import org.ploin.pmf.entity.SendingResult;
import org.ploin.pmf.entity.ServerConfig;

import java.io.Serializable;
import java.net.URL;
import java.util.Map;

/**
 * Description: Implementation of the {@link IMailSender} interface.
 * This class is responsible to send the e-mails.<br>
 * <p/>
 * $LastChangedBy: r.reiz $<br>
 * $Revision: 77 $<br>
 * $Date: 2010-03-15 13:13:45 +0100 (Mon, 15 Mar 2010) $<br>
 */
public class MailSender implements Serializable, IMailSender{

	private static final long serialVersionUID = -1569301969627707368L;
	private static Log log = LogFactory.getLog(MailSender.class);

	private IPropertiesLoader propertiesLoader;

	// *** Public Interface Methods ***

	public SendingResult sendMail(String plain, String html, MailConfig mailConfig, ServerConfig serverConfig, Map<String, String> map) throws MailFactoryException {
		try {
			if (html == null || html.trim().equals("")){
				MultiPartEmail email = new MultiPartEmail();
				if (mailConfig.isAttachementsEmpty()){
					email.setContent(plain, "text/plain; charset=iso-8859-1");
				} else {
					email.setMsg(plain);
				}
				return send(email, mailConfig, serverConfig);
			}

			HtmlEmail email = new HtmlEmail();
			setEmbeds(email, map, html);
			if (plain != null && !"".equals(plain.trim()))
				email.setTextMsg(plain);

			email.setHtmlMsg(html);
			return send(email, mailConfig, serverConfig);
		} catch (Exception e) {
			throw new MailFactoryException(e);
		}
	}

	// *** Private Helper Methods ***

	/**
	 *
	 * @param email - the MultipartEmail
	 * @param mailConfig - the mail config object
	 * @param serverConfig - the server config object
	 * @return SendingResult object
	 */
	private SendingResult send(MultiPartEmail email, MailConfig mailConfig, ServerConfig serverConfig) throws MailFactoryException{
		try {
			email.setCharset(Email.ISO_8859_1);
			setServerProperties(email, serverConfig);
			setMailProperties(email, mailConfig);
			setAttachements(email, mailConfig);  // here is a bug in apache commons email ... problems with html and attachements
			String sendMessage = email.send();
			log.debug("email.send() = " + sendMessage);
			SendingResult sendingResult = new SendingResult();
			sendingResult.setResult(sendMessage);
			sendingResult.setMimeMessage(email.getMimeMessage());
			return sendingResult;
		} catch (Exception e) {
			throw new MailFactoryException(e);
		}
	}

	/**
	 *
	 * @param email - the email object
	 * @param serverConfig - the server config object
	 */
	private void setServerProperties(Email email, ServerConfig serverConfig) throws MailFactoryException {
		try {
			String host = serverConfig.getHost();
			String fromEmail = serverConfig.getFromEmail();
			String fromName = serverConfig.getFromName();
			String authUser = serverConfig.getAuthUser();
			String authPassword = serverConfig.getAuthPassword();
			String replyTo = serverConfig.getReplyTo();

			email.setHostName(host);
			email.setFrom(fromEmail, fromName);
			if (isNotEmpty(authUser) && isNotEmpty(authPassword))
				email.setAuthentication(authUser, authPassword);

			email.addReplyTo((replyTo != null && !"".equals(replyTo)) ? replyTo : fromEmail);
		} catch (Exception e) {
			throw new MailFactoryException(e);
		}
	}

	private void setMailProperties(Email email, MailConfig mailConfig) throws MailFactoryException {
		try {
			email.setSubject(mailConfig.getSubject());

			for (Recipient toRecipient: mailConfig.getToRecipients()){
				if (toRecipient.getName() != null){
					email.addTo(toRecipient.getEmail(), toRecipient.getName());
				} else {
					email.addTo(toRecipient.getEmail());
				}
			}

			if (!mailConfig.isCcRecipientEmpty()){
				for (Recipient ccRecipient: mailConfig.getCcRecipients()){
					if (ccRecipient.getName() != null){
						email.addCc(ccRecipient.getEmail(), ccRecipient.getName());
					} else {
						email.addCc(ccRecipient.getEmail());
					}
				}
			}

			if (!mailConfig.isBccRecipientEmpty()){
				for (Recipient bccRecipient: mailConfig.getBccRecipients()){
					if (bccRecipient.getName() != null){
						email.addBcc(bccRecipient.getEmail(), bccRecipient.getName());
					} else {
						email.addBcc(bccRecipient.getEmail());
					}
				}
			}
		} catch (Exception e) {
			throw new MailFactoryException(e);
		}
	}

	/**
	 *
	 * @param email - the email object
	 * @param mailConfig - the mail config object containing the attachements
	 */
	private void setAttachements(MultiPartEmail email, MailConfig mailConfig) throws MailFactoryException{
		try {
			if (mailConfig.isAttachementsEmpty())
				return;
			for (EmailAttachment attachement: mailConfig.getAttachements()){
				email = email.attach(attachement);
			}
		} catch (Exception e) {
			throw new MailFactoryException(e);
		}
	}

	/**
	 *
	 * @param email - the email object
	 * @param map - the map with the values and variables
	 * @param mail - the mail as string
	 */
	private void setEmbeds(HtmlEmail email, Map<String, String> map, String mail) throws MailFactoryException{
		try {
			for (String key: map.keySet()){
				if (key.startsWith("embed")) {
					URL url = null;
					String urlString = map.get(key);
					url = new URL(urlString);
					if (url != null){
						String cid = email.embed(url, key);
						String htmlVal = "cid:" + cid;
						mail = mail.replaceAll(key, htmlVal);
					}
				}
			}
		} catch (Exception e) {
			throw new MailFactoryException(e);
		}
	}

	private boolean isNotEmpty(String value){
		return (value != null && !"".equals(value.trim()));
	}

	// *** Getter and Setter Methods ***
	
    public IPropertiesLoader getPropertiesLoader() {
		return propertiesLoader;
	}

	public void setPropertiesLoader(IPropertiesLoader propertiesLoader) {
		this.propertiesLoader = propertiesLoader;
	}

}