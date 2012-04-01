package org.ploin.pmf.entity;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * $LastChangedBy: r.reiz $<br>
 * $Revision: 77 $<br>
 * $Date: 2010-03-15 13:13:45 +0100 (Mon, 15 Mar 2010) $<br>
 * <p/>
 * Created by: robert
 * Created date: Nov 17, 2009 - 8:44:26 PM
 */
public class TemplateConfig implements Serializable {

	private static final long serialVersionUID = 1100200797928677705L;
	
	private String client;
	private Locale locale;
	private String name;
	private Map<String, String> map;

	public TemplateConfig(){}

	public TemplateConfig(String client, Locale locale, String name, Map<String, String> map){
		this.client = client;
		this.locale = locale;
		this.name = name;
		this.map = map;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Client    : " + client + "\n");
		sb.append("Locale    : " + locale + "\n");
		sb.append("Name      : " + name + "\n");
		sb.append("Map       :\n");
		for (Entry<String, String> entry : map.entrySet()) {
			sb.append("- " + entry.getKey() + " : " + entry.getValue() + "\n");
		}

		return sb.toString();
	}
}
