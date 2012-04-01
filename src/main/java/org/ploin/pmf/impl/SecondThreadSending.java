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
package org.ploin.pmf.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ploin.pmf.IMailSender;
import org.ploin.pmf.IPropertiesLoader;
import org.ploin.pmf.ISecondThreadSending;
import org.ploin.pmf.MailFactoryException;
import org.ploin.pmf.entity.MailConfig;
import org.ploin.pmf.entity.SendingResult;
import org.ploin.pmf.entity.ServerConfig;
import org.ploin.pmf.entity.TemplateConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Description: Implementation of the {@link ISecondThreadSending} interface.<br>
 * It is responsible for sending the mail. If in the configuration<br/>
 * singleThread is false, this class is running in a <br/>
 * second thread.
 * <p/>
 * $LastChangedBy: r.reiz $<br>
 * $Revision: 80 $<br>
 * $Date: 2010-03-18 11:39:35 +0100 (Thu, 18 Mar 2010) $<br>
 */
public class SecondThreadSending implements ISecondThreadSending{

	private static final long serialVersionUID = 1L;
	
	private static Log log = LogFactory.getLog(SecondThreadSending.class);
	
	private IMailSender mailSender;
	private IPropertiesLoader propertiesLoader;
	private MailConfig mailConfig;
	private TemplateConfig templateConfig;
	private Map<String, String> map;
	private String plainReplaced;
	private String htmlReplaced;
	private Random random = new Random(System.currentTimeMillis());

	// *** Constructor ***
	
	public SecondThreadSending(IMailSender mailSender, 
										IPropertiesLoader propertiesLoader, 
										MailConfig mailConfig,
										TemplateConfig templateConfig, 
										Map<String, String> map, 
										String plainReplaced, 
										String htmlReplaced) {
		
		this.plainReplaced = plainReplaced;
		this.htmlReplaced = htmlReplaced;
		this.mailSender = mailSender;
		this.map = map;
		this.propertiesLoader = propertiesLoader;
		this.mailConfig = mailConfig;
		this.templateConfig = templateConfig;
	}

	// *** Public Interface Methods ***
	
	public void run() {
		log.debug("singleThread = false. Start second Thread to send mail in the world wide waiting web. ");
		try {
			send();
		} catch (MailFactoryException e) {
			log.error(e);
		}
	}

	public SendingResult send() throws MailFactoryException {
		ServerConfig serverConfig = getLoadbalancedNode();
		try {
			return sendMail(serverConfig);
		} catch (MailFactoryException e) {
			List<String> fallbackServers = getFallbackServers();
			for (String fallBack : fallbackServers){
				try {
					fallBack = fallBack + ".";
					serverConfig = getServerConfig(templateConfig.getClient(), fallBack);
					return sendMail(serverConfig);
				} catch (MailFactoryException mfe) {
					try {
						Thread.sleep(serverConfig.getDelay());
					} catch (Exception exception){
						log.error("ERROR in Thread.sleep(): ", exception);
					}
				}
			}
		}
		throw new MailFactoryException("Failed to send the mail");
	}

	public ServerConfig getServerConfig(String client, String selectedNode) throws MailFactoryException {
		String hostKey = selectedNode + "host";
		String fromEmailKey = selectedNode + "fromEmail";
		String fromNameKey = selectedNode + "fromName";
		String authUserKey = selectedNode + "authUser";
		String authPasswordKey = selectedNode + "authPassword";
		String replyToKey = selectedNode + "replyTo";
		String retryKey = selectedNode + "retry";
		String delayKey = selectedNode + "delay";

		ServerConfig serverConfig = new ServerConfig();

		serverConfig.setHost(propertiesLoader.getValue(client, hostKey));
		serverConfig.setFromEmail(propertiesLoader.getValue(client, fromEmailKey));
		serverConfig.setFromName(propertiesLoader.getValue(client, fromNameKey));
		serverConfig.setAuthUser(propertiesLoader.getValue(client, authUserKey));
		serverConfig.setAuthPassword(propertiesLoader.getValue(client, authPasswordKey));

		try {
			serverConfig.setReplyTo(propertiesLoader.getValue(client, replyToKey));
		} catch (Exception e){
			log.info("ReplyTo not set. Using fromEmail: " + serverConfig.getFromEmail());
			serverConfig.setReplyTo(serverConfig.getFromEmail());
		}

		try {
			String retry = propertiesLoader.getValue(client, retryKey);
			serverConfig.setReTry(Integer.parseInt(retry) + 1);
		} catch (Exception e){
			log.info("Retry not set. Using default: " + serverConfig.getReTry());
		}

		try {
			String delay = propertiesLoader.getValue(client, delayKey);
			serverConfig.setDelay(Long.parseLong(delay));
		} catch (Exception e){
			log.info("Delay not set. Using default: " + serverConfig.getDelay());
		}

		return serverConfig;
	}

	public ServerConfig getLoadbalancedNode() throws MailFactoryException {
		String client = templateConfig.getClient();
		String loadNodes = propertiesLoader.getValue(client, "loadbalancing.nodes");

		String[] nodes = loadNodes.split(",");
		int randLoad = 0;
		int min = 0;
		int max = nodes.length - 1;
		int border = max - min;
		if (border > 0){
			randLoad = random.nextInt(border);
		}
		String selectedNode = nodes[randLoad];
		selectedNode = selectedNode.trim() + ".";
		return getServerConfig(client, selectedNode);
	}


	// *** Private Helper Methods ***

	private SendingResult sendMail(ServerConfig serverConfig) throws MailFactoryException {
		int maxRetries = serverConfig.getReTry();
		if (maxRetries < 1)
			maxRetries = 0;
		maxRetries++;
		
		for (int retries = 1; retries <= maxRetries; retries++){
			try {
				return mailSender.sendMail(plainReplaced, htmlReplaced, mailConfig, serverConfig, map);
			} catch (MailFactoryException e) {
				log.info("sendMail() failed for host " + serverConfig.getHost() + " (" + retries + "/" + maxRetries + ")");
				log.debug(serverConfig, e);
			}
		}
		
		throw new MailFactoryException("sendMail() " + serverConfig.getHost() + " failed!");
	}

	private List<String> getFallbackServers() throws MailFactoryException {
		String client = templateConfig.getClient();
		String fallBackNodes = propertiesLoader.getValue(client, "fallback.nodes");
		if (fallBackNodes == null || "".equals(fallBackNodes.trim()))
			throw new MailFactoryException("No fallback servers defined.");

		String[] nodes = fallBackNodes.split(",");
		List<String> result = new ArrayList<String>();
		for (String node: nodes){
			result.add(node.trim());
		}
		return result;
	}

}