package com.estafet.microservices.scrum.basic.ui.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.estafet.microservices.scrum.basic.ui.model.API;

@Service
public class APIService {

	@Value("${app.version}")
	private String appVersion;
	
	public API getAPI() {
		return new API(appVersion);
	}

}
