package com.microservices.scrum.basic.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microservices.scrum.basic.ui.messages.StartSprint;
import com.microservices.scrum.basic.ui.services.SprintService;

@Controller
public class SprintController {

	@Autowired
	private SprintService sprintService;

	@RequestMapping("/project/{projectId}/sprint/{sprintId}")
	public String sprint(@PathVariable int projectId, @PathVariable int sprintId, Model model) {
		model.addAttribute("sprint", sprintService.getSprint(projectId, sprintId));
		return "sprint";
	}

	@RequestMapping("/project/{projectId}/sprint/{sprintId}/story/{storyId}/addtosprint")
	public String addToSprint(@PathVariable int projectId, @PathVariable int sprintId, @PathVariable int storyId,
			Model model) {
		model.addAttribute("sprint", sprintService.addStoryToSprint(projectId, sprintId, storyId));
		return "redirect:/project/" + projectId	+ "/sprint/" + sprintId;
	}
	
	@GetMapping("/startsprint/{id}")
	public String taskForm(@PathVariable int id,  Model model) {
		model.addAttribute("startSprint", new StartSprint());
		model.addAttribute("projectId", id);
		return "startsprint";
	}
	
	@PostMapping("/startsprint/{id}")
	public String taskSubmit(@PathVariable int id, @ModelAttribute StartSprint startSprint) {
		sprintService.startSprint(id, startSprint);
		return "redirect:/project/" + id;
	}

}
