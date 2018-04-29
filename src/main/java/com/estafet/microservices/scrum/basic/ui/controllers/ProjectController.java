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
import com.estafet.microservices.scrum.basic.ui.model.Project;
import com.estafet.microservices.scrum.basic.ui.service.HealthCheckService;
import com.estafet.microservices.scrum.basic.ui.service.ProjectService;

@Controller
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private HealthCheckService healthCheckService;

	@RequestMapping("/projects")
	public String projects(Model model) {
		model.addAttribute("projects", projectService.getProjects());
//		model.addAttribute("projectServiceIsAlive",
//				healthCheckService.serviceHealthCheck(ServiceName.PROJECT_SERVICE).getStatus().equals("UP") ? true
//						: false);

		return "projects";
	}

	@RequestMapping("/project/{id}")
	public String project(@PathVariable int id, Model model) {
		model.addAttribute("project", projectService.getProject(id));
		model.addAttribute("projectId", id);
//		model.addAttribute("projectServiceIsAlive",
//				healthCheckService.serviceHealthCheck(ServiceName.PROJECT_SERVICE).getStatus().equals("UP") ? true
//						: false);
//		model.addAttribute("sprintServiceIsAlive",
//				healthCheckService.serviceHealthCheck(ServiceName.SPRINT_SERVICE).getStatus().equals("UP") ? true
//						: false);
//		model.addAttribute("storyServiceIsAlive",
//				healthCheckService.serviceHealthCheck(ServiceName.STORY_SERVICE).getStatus().equals("UP") ? true
//						: false);

		return "project";
	}

	@GetMapping("/newproject")
	public String newProjectForm(Model model) {
		model.addAttribute("project", new Project().init());
//		model.addAttribute("projectServiceIsAlive",
//				healthCheckService.serviceHealthCheck(ServiceName.PROJECT_SERVICE).getStatus().equals("UP") ? true
//						: false);

		return "newproject";
	}

	@PostMapping("/newproject")
	public String newProjectSubmit(@ModelAttribute Project project) {
		project = projectService.createProject(project);

		if (project.getId() == null) {
			return "redirect:/error/503";
		}

		return "redirect:/project/" + project.getId();
	}

	@RequestMapping("/project/{id}/burndown")
	public String burndown(@PathVariable int id, Model model) {
		model.addAttribute("project", projectService.getBurndown(id));
		model.addAttribute("projectId", id);
		return "projectburndown";
	}

}
