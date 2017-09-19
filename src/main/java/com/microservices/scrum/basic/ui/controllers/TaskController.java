package com.microservices.scrum.basic.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.microservices.scrum.basic.ui.model.Task;
import com.microservices.scrum.basic.ui.services.StoryService;

@Controller
public class TaskController {

	@Autowired
	private StoryService storyService;
	
	@GetMapping("/task/{id}")
	public String taskForm(@PathVariable int id,  Model model) {
		model.addAttribute("task", new Task());
		model.addAttribute("storyId", id);
		return "task";
	}
	
	@PostMapping("/task/{id}")
	public String taskSubmit(@PathVariable int id, @ModelAttribute Task task) {
		storyService.addTask(id, task);
		return "redirect:/story/" + id;
	}
	
}
