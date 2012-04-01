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

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.ploin.pmf.IMailReader;
import org.ploin.pmf.entity.TemplateConfig;

import java.util.Locale;

/**
 * Here are the testcases for the standardimplementation of the IMailReader interface.
 * <br/>
 * <p/>
 * $LastChangedBy: r.reiz $<br>
 * $Revision: 80 $<br>
 * $Date: 2010-03-18 11:39:35 +0100 (Thu, 18 Mar 2010) $<br>
 */
public class MailReaderTest {

	private IMailReader mailReader;


	/**
	 * Here we setup our test scenario with the direcotry "businesslogic/mail"
	 */
	@Before
	public void init(){
		mailReader = new MailReader();
		mailReader.setDirectory("businesslogic/mail");
	}


	/**
	 * In this testcase we call the method "getHtmlMail" with an existing
	 * html template and a valid client. We expect "regTemplate1". 
	 */
	@Test
	public void findHtmlInClient(){
		String text = null;
		try {
			TemplateConfig templateConfig = new TemplateConfig();
			templateConfig.setClient("mvv");
			templateConfig.setName("regTemplate");
			text = mailReader.getHtmlMail(templateConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(text);
		assertEquals("regTemplate1", text.trim());
	}


	/**
	 * In this testcase we call the method "getPlainMail" with an existing
	 * plain template and a valid client. We expect "regTemplate1.txt".
	 */
	@Test
	public void findPlainInClient(){
		String text = null;
		try {
			TemplateConfig templateConfig = new TemplateConfig();
			templateConfig.setClient("mvv");
			templateConfig.setName("regTemplate");
			text = mailReader.getPlainMail(templateConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(text);
		assertEquals("regTemplate1.txt", text.trim());
	}


	/**
	 * In this testcase we call the method "getPlainMail" with an existing
	 * plain template, an existing locale and a valid client. <br/>
	 * We expect "regTemplate3Locale".
	 */
	@Test
	public void findPlainInClientWithLocale(){
		String text = null;
		try {
			TemplateConfig templateConfig = new TemplateConfig();
			templateConfig.setClient("mvv");
			templateConfig.setLocale(new Locale("de", "DE"));
			templateConfig.setName("regTemplate");
			text = mailReader.getPlainMail(templateConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(text);
		assertEquals("regTemplate3Locale", text.trim());
	}


	/**
	 * In this testcase we call the method "getHtmlMail" with an existing
	 * html template, an existing locale and a valid client. <br/>
	 * We expect "regTemplate3Locale".
	 */
	@Test
	public void findHtmlInClientWithLocale(){
		String text = null;
		try {
			TemplateConfig templateConfig = new TemplateConfig();
			templateConfig.setClient("mvv");
			templateConfig.setLocale(new Locale("de", "DE"));
			templateConfig.setName("regTemplate");
			text = mailReader.getHtmlMail(templateConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(text);
		assertEquals("regTemplate4Locale", text.trim());
	}


	/**
	 * In this testcase we call the method "getHtmlMail" with an existing
	 * html template in the directory, without client and without locale.<br/>
	 * We expect "regTemplateDirectory1".  
	 */
	@Test
	public void findHtmlInDirectory(){
		String text = null;
		try {
			TemplateConfig templateConfig = new TemplateConfig();
			templateConfig.setName("regTemplate");
			text = mailReader.getHtmlMail(templateConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(text);
		assertEquals("regTemplateDirectory1", text.trim());
	}


	/**
	 * In this testcase we call the method "getPlainMail" with an existing
	 * plain template in the directory, without client and without locale.<br/>
	 * We expect "regTemplateDirectory2".
	 */
	@Test
	public void findPlainInDirectory(){
		String text = null;
		try {
			TemplateConfig templateConfig = new TemplateConfig();
			templateConfig.setName("regTemplate");
			text = mailReader.getPlainMail(templateConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(text);
		assertEquals("regTemplateDirectory2", text.trim());
	}


	/**
	 * In this testcase we call the method "getPlainMail" with an existing
	 * plain template in the directory, without client but with a locale.<br/>
	 * We expect "regTemplateDirectory3".
	 */
	@Test
	public void findPlainInDirectoryWithLocale(){
		String text = null;
		try {
			TemplateConfig templateConfig = new TemplateConfig();
			templateConfig.setName("regTemplate");
			templateConfig.setLocale(new Locale("de", "DE"));
			text = mailReader.getPlainMail(templateConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(text);
		assertEquals("regTemplateDirectory3", text.trim());
	}


	/**
	 * In this testcase we call the method "getHtmlMail" with an existing
	 * plain template in the directory, without client but with a locale.<br/>
	 * We expect "regTemplateDirectory4".
	 */
	@Test
	public void findHtmlInDirectoryWithLocale(){
		String text = null;
		try {
			TemplateConfig templateConfig = new TemplateConfig();
			templateConfig.setName("regTemplate");
			templateConfig.setLocale(new Locale("de", "DE"));
			text = mailReader.getHtmlMail(templateConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(text);
		assertEquals("regTemplateDirectory4", text.trim());
	}


	/**
	 * In this testcase we call the method "getHtmlMail" with an existing
	 * html template in the directory, without client but with a locale object.<br/>
	 * We expect "regTemplateDirectory3".
	 */
	@Test
	public void findHtmlInDirectoryWithLocale4(){
		String text = null;
		try {
			TemplateConfig templateConfig = new TemplateConfig();
			templateConfig.setName("regTemplate");
			templateConfig.setLocale(new Locale("de", "DE"));
			text = mailReader.getHtmlMail(templateConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(text);
		assertEquals("regTemplateDirectory4", text.trim());
	}


	/**
	 * In this testcase we call the method "getHtmlMail" with an existing
	 * html template in the directory, without client but with a wrong locale object.<br/>
	 * We expect "regTemplateDirectory3".
	 */
	@Test
	public void findHtmlInDirectoryWithLocale3(){
		String text = null;
		try {
			TemplateConfig templateConfig = new TemplateConfig();
			templateConfig.setName("regTemplate");
			templateConfig.setLocale(new Locale("br", "DE"));
			text = mailReader.getHtmlMail(templateConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(text);
		assertEquals("regTemplateDirectory1", text.trim());
	}


	/**
	 * In this testcase we call the method "getHtmlMail" with an existing
	 * html template in the root, without client but with a wrong locale object.<br/>
	 * We expect "rootTemplateHtml".
	 */
	@Test
	public void findHtmlInRootWithLocale3(){
		String text = null;
		try {
			TemplateConfig templateConfig = new TemplateConfig();
			templateConfig.setName("template1");
			templateConfig.setLocale(new Locale("br", "DE"));
			text = mailReader.getHtmlMail(templateConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(text);
		assertEquals("rootTemplateHtml", text.trim());
	}


	/**
	 * In this testcase we call the method "getHtmlMail" with an existing
	 * html template in the root, without client and without a locale.<br/>
	 * We expect "rootTemplateHtml".
	 */
	@Test
	public void findHtmlInRoot(){
		String text = null;
		try {
			TemplateConfig templateConfig = new TemplateConfig();
			templateConfig.setName("template1");
			text = mailReader.getHtmlMail(templateConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(text);
		assertEquals("rootTemplateHtml", text.trim());
	}


	/**
	 * In this testcase we call the method "getPlainMail" with an existing
	 * plain template in the root, without client and without a locale.<br/>
	 * We expect "rootTemplateTxt".
	 */
	@Test
	public void findPlainInRoot(){
		String text = null;
		try {
			TemplateConfig templateConfig = new TemplateConfig();
			templateConfig.setName("template1");
			text = mailReader.getPlainMail(templateConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(text);
		assertEquals("rootTemplateTxt", text.trim());
	}


	/**
	 * In this negative testcase we call the method "getPlainMail" with an non existing
	 * plain template, without client and without a locale.<br/>
	 * We expect "rootTemplateTxt".
	 */
	@Test
	public void findNonExistingPlainInRoot(){
		String text = null;
		try {
			TemplateConfig templateConfig = new TemplateConfig();
			templateConfig.setName("huKIllj8");
			text = mailReader.getPlainMail(templateConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNull(text);
	}


	/**
	 * In this testcase we are testing the getter and setter for directory.
	 */
	@Test
	public void getterAndSetterForDirectory(){
		mailReader.setDirectory("test");
		assertNotNull(mailReader.getDirectory());
		assertEquals(mailReader.getDirectory(), "test");
		mailReader.setDirectory(null);
		assertNull(mailReader.getDirectory());
		mailReader.setDirectory("businesslogic/mail");
		assertNotNull(mailReader.getDirectory());
		assertEquals(mailReader.getDirectory(), "businesslogic/mail");		
	}

	/**
	 * In this testcase we are testing the getter and setter for htmlExtension.
	 */
	@Test
	public void getterAndSetterForHtmlExtension(){
		mailReader.setHtmlExtension("tr");
		assertNotNull(mailReader.getHtmlExtension());
		assertEquals(mailReader.getHtmlExtension(), "tr");
		mailReader.setHtmlExtension(null);
		assertNull(mailReader.getHtmlExtension());
		mailReader.setHtmlExtension(".html");
		assertNotNull(mailReader.getHtmlExtension());
		assertEquals(mailReader.getHtmlExtension(), ".html");
	}

	/**
	 * In this testcase we are testing the getter and setter for plainExtension.
	 */
	@Test
	public void getterAndSetterForPlainExtension(){
		mailReader.setPlainExtension("tu");
		assertNotNull(mailReader.getPlainExtension());
		assertEquals(mailReader.getPlainExtension(), "tu");
		mailReader.setPlainExtension(null);
		assertNull(mailReader.getPlainExtension());
		mailReader.setPlainExtension(".txt");
		assertNotNull(mailReader.getHtmlExtension());
		assertEquals(mailReader.getPlainExtension(), ".txt");
	}


}
