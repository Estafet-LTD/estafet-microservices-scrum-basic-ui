package com.estafet.microservices.scrum.basic.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estafet.microservices.scrum.basic.ui.config.ServiceName;
import com.estafet.microservices.scrum.basic.ui.model.Story;
import com.estafet.microservices.scrum.basic.ui.service.HealthCheckService;
import com.estafet.microservices.scrum.basic.ui.service.StoryService;

@Controller
public class StoryController {

	@Autowired
	private StoryService storyService;

	@Autowired
	private HealthCheckService healthCheckService;

	@RequestMapping("/story/{id}")
	public String story(@PathVariable int id, Model model) {
		Story story = storyService.getStory(id);
		model.addAttribute("story", story);
		model.addAttribute("storyId", story.getId());
		model.addAttribute("projectId", story.getProjectId());
		model.addAttribute("sprintId", story.getSprintId());
//		model.addAttribute("storyServiceIsAlive", healthCheckService.serviceHealthCheck(ServiceName.STORY_SERVICE));

		return "story";
	}

	@GetMapping("/addstory/{id}")
	public String addStoryForm(@PathVariable int id, Model model) {
		model.addAttribute("story", new Story().init());
		model.addAttribute("projectId", id);
//		model.addAttribute("storyServiceIsAlive", healthCheckService.serviceHealthCheck(ServiceName.STORY_SERVICE));

		return "addstory";
	}

	@PostMapping("/addstory/{id}")
	public String addStorySubmit(@PathVariable int id, @ModelAttribute Story story) {
		story = storyService.addStory(id, story);

		if (story.getId() == null) {
			return "redirect:/error/503";
		}

		return "redirect:/project/" + id;
	}

}
