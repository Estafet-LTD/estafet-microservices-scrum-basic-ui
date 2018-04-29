package com.estafet.microservices.scrum.basic.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estafet.microservices.scrum.basic.ui.config.ServiceName;
import com.estafet.microservices.scrum.basic.ui.model.Sprint;
import com.estafet.microservices.scrum.basic.ui.service.HealthCheckService;
import com.estafet.microservices.scrum.basic.ui.service.SprintService;

@Controller
public class SprintController {

	@Autowired
	private SprintService sprintService;

	@Autowired
	private HealthCheckService healthCheckService;

	@RequestMapping("/sprint/{sprintId}")
	public String sprint(@PathVariable int sprintId, Model model) {
		Sprint sprint = sprintService.getSprint(sprintId);
		return "redirect:/project/" + sprint.getProjectId() + "/sprint/" + sprintId;
	}

	@RequestMapping("/project/{projectId}/sprint/{sprintId}")
	public String sprint(@PathVariable int projectId, @PathVariable int sprintId, Model model) {
		model.addAttribute("sprint", sprintService.getSprint(projectId, sprintId));
		model.addAttribute("projectId", projectId);
		model.addAttribute("sprintId", sprintId);
//		model.addAttribute("sprintServiceIsAlive",
//				healthCheckService.serviceHealthCheck(ServiceName.SPRINT_SERVICE).getStatus().equals("UP") ? true
//						: false);

		return "sprint";
	}

	@RequestMapping("/project/{projectId}/sprint/{sprintId}/story/{storyId}/addtosprint")
	public String addStoryToSprint(@PathVariable int projectId, @PathVariable int sprintId, @PathVariable int storyId,
			Model model) {
		model.addAttribute("sprint", sprintService.addStoryToSprint(projectId, sprintId, storyId));
		model.addAttribute("projectId", projectId);
		model.addAttribute("sprintId", sprintId);
//		model.addAttribute("sprintServiceIsAlive",
//				healthCheckService.serviceHealthCheck(ServiceName.SPRINT_SERVICE).getStatus().equals("UP") ? true
//						: false);

		return "redirect:/project/" + projectId + "/sprint/" + sprintId;
	}

	@RequestMapping("/project/{projectId}/sprint/{sprintId}/burndown")
	public String sprintBurndown(@PathVariable int projectId, @PathVariable int sprintId, Model model) {
		model.addAttribute("sprint", sprintService.getSprintBurndown(sprintId));
		model.addAttribute("projectId", projectId);
		model.addAttribute("sprintId", sprintId);
//		model.addAttribute("sprintServiceIsAlive",
//				healthCheckService.serviceHealthCheck(ServiceName.SPRINT_SERVICE).getStatus().equals("UP") ? true
//						: false);

		return "sprintburndown";
	}

}
