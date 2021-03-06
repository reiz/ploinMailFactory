package org.ploin.pmf.impl;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.ploin.pmf.IMailSender;
import org.ploin.pmf.IPropertiesLoader;
import org.ploin.pmf.ISecondThreadSending;
import org.ploin.pmf.MailFactoryException;
import org.ploin.pmf.entity.MailConfig;
import org.ploin.pmf.entity.ServerConfig;
import org.ploin.pmf.entity.TemplateConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * $LastChangedBy: r.reiz $<br>
 * $Revision: 80 $<br>
 * $Date: 2010-03-18 11:39:35 +0100 (Thu, 18 Mar 2010) $<br>
 * <p/>
 * Created by: robert
 * Created date: Nov 16, 2009 - 10:37:01 PM
 */
public class SecondThreadSendingTest {

	private static Log log = LogFactory.getLog(SecondThreadSendingTest.class);
	
	private ISecondThreadSending secondThreadSending;

	/**
	 * Here we set up the testscenario
	 */
	@Before
	public void init(){

	}


	/**
	 * In this testcase we test the method getLoadbalancedNode 
	 * @throws MailFactoryException 
	 */
	@Test
	public void getterAndSetterForPropertiesLoader() throws MailFactoryException{
		IPropertiesLoader propertiesLoader = new PropertiesLoader();
		propertiesLoader.setDirectory("businesslogic/mail");
		propertiesLoader.setPropFile("mailSecondThreadTest1.properties");
		MailConfig mailConfig = new MailConfig();
		TemplateConfig templateConfig = new TemplateConfig();
		secondThreadSending = new SecondThreadSending(null, propertiesLoader, mailConfig, templateConfig, null, null, null);
		ServerConfig serverConfig = secondThreadSending.getLoadbalancedNode();
		assertNotNull(serverConfig);
		assertEquals(serverConfig.getHost(), "mail.gmx.net");
	}

	/**
	 * In this testcase we test the method getLoadbalancedNode
	 * @throws MailFactoryException 
	 */
	@Test
	public void getterAndSetterForPropertiesLoader2() throws MailFactoryException{
		IPropertiesLoader propertiesLoader = new PropertiesLoader();
		propertiesLoader.setDirectory("businesslogic/mail");
		propertiesLoader.setPropFile("mailSecondThreadTest2.properties");
		MailConfig mailConfig = new MailConfig();
		TemplateConfig templateConfig = new TemplateConfig();
		secondThreadSending = new SecondThreadSending(null, propertiesLoader, mailConfig, templateConfig, null, null, null);
		ServerConfig serverConfig = secondThreadSending.getLoadbalancedNode();
		assertNotNull(serverConfig);
		assertEquals(serverConfig.getHost(), "mail.gmx.net");
	}

	/**
	 * In this testcase we test the method getLoadbalancedNode
	 */
	@Test
	public void getterAndSetterForPropertiesLoader3(){
		IPropertiesLoader propertiesLoader = new PropertiesLoader();
		propertiesLoader.setDirectory("businesslogic/mail");
		propertiesLoader.setPropFile("notExist.properties");
		MailConfig mailConfig = new MailConfig();
		TemplateConfig templateConfig = new TemplateConfig();
		secondThreadSending = new SecondThreadSending(null, propertiesLoader, mailConfig, templateConfig, null, null, null);

		try {
			secondThreadSending.getLoadbalancedNode();
			fail();
		} catch (MailFactoryException e) {
			log.debug(e);
		}
	}



	/**
	 * In this testcase we test the method getServerConfig
	 * @throws MailFactoryException 
	 */
	@Test
	public void getServerConfig1() throws MailFactoryException{
		IPropertiesLoader propertiesLoader = new PropertiesLoader();
		propertiesLoader.setDirectory("businesslogic/mail");
		propertiesLoader.setPropFile("mailSecondThreadTest1.properties");
		MailConfig mailConfig = new MailConfig();
		TemplateConfig templateConfig = new TemplateConfig();
		secondThreadSending = new SecondThreadSending(null, propertiesLoader, mailConfig, templateConfig, null, null, null);
		ServerConfig serverConfig = secondThreadSending.getServerConfig(null, "server1.");
		assertNotNull(serverConfig);
		assertEquals(serverConfig.getHost(), "mail.gmx.net");
	}

	/**
	 * In this testcase we test the method getServerConfig
	 * @throws MailFactoryException 
	 */
	@Test
	public void getServerConfig2(){
		IPropertiesLoader propertiesLoader = new PropertiesLoader();
		propertiesLoader.setDirectory("businesslogic/mail");
		propertiesLoader.setPropFile("mailSecondThreadTest1.properties");
		MailConfig mailConfig = new MailConfig();
		TemplateConfig templateConfig = new TemplateConfig();
		secondThreadSending = new SecondThreadSending(null, propertiesLoader, mailConfig, templateConfig, null, null, null);
		
		try {
			secondThreadSending.getServerConfig(null, "server11111");
			fail();
		} catch (MailFactoryException e) {
			log.debug(e);
		}
	}



	/**
	 * In this testcase we test the method sendMail
	 * @throws MailFactoryException 
	 */
	@Test
	public void sendMail1() throws MailFactoryException{
		IPropertiesLoader propertiesLoader = new PropertiesLoader();
		propertiesLoader.setDirectory("businesslogic/mail");
		propertiesLoader.setPropFile("mailSecondThreadTest3.properties");
		MailConfig mailConfig = new MailConfig();
		mailConfig.setSubject("test");
		mailConfig.addToRecipient("Rob", "time@ploin.de");
		IMailSender mailSender = new MailSender();
		mailSender.setPropertiesLoader(propertiesLoader);
		Map<String, String> map = new HashMap<String, String>();
		TemplateConfig templateConfig = new TemplateConfig();
		secondThreadSending = new SecondThreadSending(mailSender, propertiesLoader, mailConfig, templateConfig, map, "testmail", null);

		try {
			secondThreadSending.send();
			fail();
		} catch (MailFactoryException e) {
			log.debug(e);
		}
	}

}