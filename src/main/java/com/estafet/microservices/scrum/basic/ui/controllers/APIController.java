package com.estafet.microservices.scrum.basic.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estafet.microservices.scrum.basic.ui.model.API;
import com.estafet.microservices.scrum.basic.ui.service.APIService;

@RestController
public class APIController {

	@Autowired
	private APIService apiService;
	
	@GetMapping("/api")
	public API getAPI() {
		return apiService.getAPI();
	}

}
