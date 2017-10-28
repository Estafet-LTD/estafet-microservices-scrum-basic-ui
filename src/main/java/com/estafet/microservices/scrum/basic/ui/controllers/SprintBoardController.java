package com.estafet.microservices.scrum.basic.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estafet.microservices.scrum.basic.ui.service.SprintBoardService;

@Controller
public class SprintBoardController {

	@Autowired
	private SprintBoardService sprintBoardService;

	@RequestMapping("/project/{projectId}/sprint/{sprintId}/board")
	public String sprintBoard(@PathVariable int projectId, @PathVariable int sprintId, Model model) {
		model.addAttribute("board", sprintBoardService.getSprintBoard(sprintId));
		model.addAttribute("projectId", projectId);
		return "board";
	}

	@RequestMapping("/project/{projectId}/sprint/{sprintId}/task/{taskId}/claim")
	public String claimTask(@PathVariable int projectId, @PathVariable int sprintId, @PathVariable int taskId,
			Model model) {
		model.addAttribute("board", sprintBoardService.claim(sprintId, taskId));
		model.addAttribute("projectId", projectId);
		return "redirect:/project/" + projectId + "/sprint/" + sprintId + "/board";
	}

	@RequestMapping("/project/{projectId}/sprint/{sprintId}/task/{taskId}/complete")
	public String completeTask(@PathVariable int projectId, @PathVariable int sprintId, @PathVariable int taskId,
			Model model) {
		model.addAttribute("board", sprintBoardService.complete(sprintId, taskId));
		model.addAttribute("projectId", projectId);
		return "redirect:/project/" + projectId + "/sprint/" + sprintId + "/board";
	}


}
