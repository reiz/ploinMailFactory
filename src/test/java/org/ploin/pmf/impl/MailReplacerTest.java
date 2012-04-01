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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.ploin.pmf.IMailReplacer;
import org.ploin.pmf.MailFactoryException;

import java.util.HashMap;
import java.util.Map;

/**
 * Here are the testcases for the standardimplementation of the IMailReplacer interface.
 * <br/>
 * <p/>
 * $LastChangedBy: r.reiz $<br>
 * $Revision: 80 $<br>
 * $Date: 2010-03-18 11:39:35 +0100 (Thu, 18 Mar 2010) $<br>
 */
public class MailReplacerTest {

	private static Log log = LogFactory.getLog(MailReplacerTest.class);
	
	IMailReplacer mailReplacer;

	@Before
	public void init(){
		mailReplacer = new MailReplacer();
	}


	/**
	 * In this positiv testcase we test the method with the simple String <br/>
	 * ":designation: is a :test:". for the text and the given map we <br/>
	 * await "Dear Sir, is a TEST".
	 * @throws MailFactoryException 
	 */
	@Test
	public void doReplcae() throws MailFactoryException{
		String text = ":designation: is a :test:";
		Map<String, String> map = new HashMap<String, String>();
		map.put("designation", "D");
		map.put(":designation:", "Dear Sir,");
		map.put(":test:", "TEST");
		String result = mailReplacer.replace(text, map);
		assertNotNull(result);
		assertEquals("Dear Sir, is a TEST", result);
	}


	/**
	 * In this negative testcase we test the method with a null-value <br/>
	 * for the text. We await as result a NULL value. 
	 */
	@Test
	public void doReplcae2(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(":designation:", "Dear Sir,");

		try {
			String result = mailReplacer.replace(null, map);
			fail(result);
		} catch (MailFactoryException e) {
			log.debug(e);
		}
	}


	/**
	 * In this negative testcase we test the method with a empty string value <br/>
	 * for the text. We await as result a NULL value.
	 */
	@Test
	public void doReplcae3() throws MailFactoryException{
		Map<String, String> map = new HashMap<String, String>();
		map.put(":designation:", "Dear Sir,");

		String result = mailReplacer.replace("", map);
		assertTrue(result.isEmpty());
	}

}
