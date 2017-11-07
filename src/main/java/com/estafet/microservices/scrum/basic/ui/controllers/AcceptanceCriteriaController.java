package com.estafet.microservices.scrum.basic.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.estafet.microservices.scrum.basic.ui.model.AcceptanceCriterion;
import com.estafet.microservices.scrum.basic.ui.service.StoryService;

@Controller
public class AcceptanceCriteriaController {

	@Autowired
	private StoryService storyService;
	
	@GetMapping("/criteria/{id}")
	public String criteriaForm(@PathVariable int id,  Model model) {
		model.addAttribute("criteria", new AcceptanceCriterion().init());
		model.addAttribute("storyId", id);
		return "criteria";
	}
	
	@PostMapping("/criteria/{id}")
	public String criteriaSubmit(@PathVariable int id, @ModelAttribute AcceptanceCriterion criteria) {
		storyService.addAcceptanceCriteria(id, criteria);
		return "redirect:/story/" + id;
	}
	
}

