package com.estafet.microservices.scrum.basic.ui.model;

import java.util.StringTokenizer;

public class API {

	private String version;

	public API(String version) {
		StringTokenizer tokenizer = new StringTokenizer(version.replaceAll("\\-SNAPSHOT", ""), ".");
		String p1 = tokenizer.nextToken();
		String p2 = tokenizer.nextToken();
		String p3 = tokenizer.nextToken();
		this.version = p1 + "." + p2 + "." + Integer.toString(Integer.parseInt(p3) - 1);
	}

	public String getVersion() {
		return version;
	}

}
