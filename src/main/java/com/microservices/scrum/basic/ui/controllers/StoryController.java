package com.microservices.scrum.basic.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microservices.scrum.basic.ui.model.Story;
import com.microservices.scrum.basic.ui.services.StoryService;

@Controller
public class StoryController {
	
	@Autowired
	private StoryService storyService;
	
	@RequestMapping("/story/{id}")
	public String story(@PathVariable int id, Model model) {
		model.addAttribute("story", storyService.getStory(id));
		return "story";
	}
	
	@GetMapping("/addstory/{id}")
	public String addStoryForm(@PathVariable int id,  Model model) {
		model.addAttribute("story", new Story());
		model.addAttribute("projectId", id);
		return "addstory";
	}
	
	@PostMapping("/addstory/{id}")
	public String addStorySubmit(@PathVariable int id, @ModelAttribute Story story) {
		storyService.addStory(id, story);
		return "redirect:/project/" + id;
	}
	
}
