package com.microservices.scrum.basic.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microservices.scrum.basic.ui.services.ProjectService;

@Controller
public class ProjectsController {

	@Autowired
	private ProjectService projectService;
	
    @RequestMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("projects", projectService.getProjects());
        return "projects";
    }

}
