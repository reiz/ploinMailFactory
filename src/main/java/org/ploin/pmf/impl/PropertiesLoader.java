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
import org.ploin.pmf.IPropertiesLoader;
import org.ploin.pmf.MailFactoryException;
import org.ploin.pmf.entity.MailConfig;
import org.ploin.pmf.entity.Recipient;
import org.ploin.pmf.entity.TemplateConfig;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Description: Implementation of the {@link IPropertiesLoader} interface.<br>
 * This class is responsible to read properties files.<br/>
 * <p/>
 * $LastChangedBy: r.reiz $<br>
 * $Revision: 83 $<br>
 * $Date: 2010-08-09 11:14:25 +0200 (Mon, 09 Aug 2010) $<br>
 */
public class PropertiesLoader implements IPropertiesLoader, Serializable {

	private static final long serialVersionUID = 5587041290421008476L;

	private static Log log = LogFactory.getLog(PropertiesLoader.class);

	private String directory;
	private String propertiesFilename = "mail.properties";

	private Map<String, Properties> propertiesCache = new HashMap<String, Properties>();

	// *** Public Interface Methods ***

	public String getValue(String key) throws MailFactoryException{
		return getValue(null, key);
	}

	public String getValue(String client, String key) throws MailFactoryException {
		try {
			Properties properties = getPropertiesOnlyClient(client, propertiesFilename);
			return getValueFromProperties(properties, key);
		} catch (MailFactoryException mfe) {
			log.debug("No client-specific properties");
		}

		try {
			Properties properties = getPropertiesOnlyDirectory(propertiesFilename);
			return getValueFromProperties(properties, key);
		} catch (MailFactoryException mfe) {
			log.debug("No properties in directory");
		}

		Properties properties = getPropertiesOnlyRoot(propertiesFilename);
		return getValueFromProperties(properties, key);
	}

	public void replaceVariables(TemplateConfig templateConfig) throws MailFactoryException {
		String client = templateConfig.getClient();
		Map<String, String> map = templateConfig.getMap();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			String value = map.get(key);
			String replacedValue = getReplacedValue(client, value);
			map.put(key, replacedValue);
		}
	}

	public void replaceVariables(String client, MailConfig mailConfig) throws MailFactoryException {
		for (Recipient recipient : mailConfig.getToRecipients()) {
			String value = recipient.getEmail();
			recipient.setEmail(getReplacedValue(client, value));
		}

		for (Recipient recipient : mailConfig.getToRecipients()) {
			String value = recipient.getEmail();
			recipient.setEmail(getReplacedValue(client, value));
		}

		for (Recipient recipient : mailConfig.getToRecipients()) {
			String value = recipient.getEmail();
			recipient.setEmail(getReplacedValue(client, value));
		}

		String value = mailConfig.getSubject();
		mailConfig.setSubject(getReplacedValue(client, value));
	}

	public Properties getProperties(String client, String name) throws MailFactoryException{
		try {
			return getPropertiesOnlyClient(client, name);
		} catch (MailFactoryException mfe) {
			log.debug("No client-specific property");
		}

		try {
			return getPropertiesOnlyDirectory(name);
		} catch (MailFactoryException mfe) {
			log.debug("No property in directory");
		}

		return getPropertiesOnlyRoot(name);
	}

	public Properties getPropertiesOnlyClient(String client, String name) throws MailFactoryException{
		String key = getKeyForPropertiesCache(client, name);

		Properties properties = propertiesCache.get(key);
		if (properties != null) 
			return properties;

		StringBuffer path = new StringBuffer();
		path.append(directory);
		path.append("/");
		path.append(client);
		path.append("/");
		path.append(name);
		
		properties = loadProperties(path.toString());
		propertiesCache.put(key, properties);
		
		return properties;
	}

	public Properties getPropertiesOnlyDirectory(String name) throws MailFactoryException{
		String key = getKeyForPropertiesCache(directory, name);

		Properties properties = propertiesCache.get(key);
		if (properties != null)
			return properties;

		StringBuffer path = new StringBuffer();
		path.append(directory);
		path.append("/");
		path.append(name);

		properties = loadProperties(path.toString());
		propertiesCache.put(key, properties);

		return properties;
	}

	public Properties getPropertiesOnlyRoot(String name) throws MailFactoryException {
		String key = getKeyForPropertiesCache("root", name);

		Properties properties = propertiesCache.get(key);
		if (properties != null)
			return properties;

		properties = loadProperties(name);
		propertiesCache.put(key, properties);

		return properties;
	}

	// *** Private Methods ***

	/**
	 * 
	 * @param name
	 * @return
	 */
	private Properties loadProperties(String name) throws MailFactoryException {
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			if (loader == null)
				loader = ClassLoader.getSystemClassLoader();
			InputStream in = loader.getResourceAsStream(name);
			Properties result = new Properties();
			result.load(in);
			return result;
		} catch (Exception e) {
			throw new MailFactoryException("Error trying to load properties " + name);
		}
	}

	/**
	 * 
	 * @param part1
	 * @param part2
	 * @return
	 */
	private String getKeyForPropertiesCache(String part1, String part2) throws MailFactoryException {
		if (part2 == null || "".equals(part2))
			throw new MailFactoryException("Illegal argument in getKeyForPropertiesCache()");
		StringBuffer key = new StringBuffer();
		key.append(part1);
		key.append("_");
		key.append(part2);
		return key.toString();
	}

	private String getValueFromProperties(Properties properties, String key) throws MailFactoryException {
		try {
			String value = properties.getProperty(key);
			if (value == null)
				throw new MailFactoryException("Property '" + key + "' not found");
			return value;
		} catch (Exception e) {
			throw new MailFactoryException(e);
		}
	}

	private String getReplacedValue(String client, String value) throws MailFactoryException {
		if (value == null)
			value = "";
		if (value.startsWith("${") && value.endsWith("}")){
			String propertyKey = value.substring(2, value.length() - 1);
			return getValue(client, propertyKey);
		}
		return value;
	}

	// *** Getter and Setter Methods ***
	
	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getPropFile() {
		return propertiesFilename;
	}

	public void setPropFile(String propFile) {
		this.propertiesFilename = propFile;
	}
}