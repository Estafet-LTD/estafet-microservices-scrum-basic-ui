package com.estafet.microservices.scrum.basic.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estafet.microservices.scrum.basic.ui.model.Project;
import com.estafet.microservices.scrum.basic.ui.services.ProjectService;

@Controller
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@RequestMapping("/projects")
	public String projects(Model model) {
		model.addAttribute("projects", projectService.getProjects());
		return "projects";
	}

	@RequestMapping("/project/{id}")
	public String project(@PathVariable int id, Model model) {
		model.addAttribute("project", projectService.getProject(id));
		return "project";
	}

	@GetMapping("/newproject")
	public String newProjectForm(Model model) {
		model.addAttribute("project", new Project());
		return "newproject";
	}

	@PostMapping("/newproject")
	public String newProjectSubmit(@ModelAttribute Project project) {
		return "redirect:/project/" + projectService.createProject(project).getId();
	}

	@RequestMapping("/project/{id}/burndown")
	public String burndown(@PathVariable int id, Model model) {
		model.addAttribute("project", projectService.getBurndown(id));
		return "projectburndown";
	}

}
