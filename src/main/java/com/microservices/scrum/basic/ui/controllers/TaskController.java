package com.microservices.scrum.basic.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.microservices.scrum.basic.ui.messages.UpdateRemainingTime;
import com.microservices.scrum.basic.ui.model.Task;
import com.microservices.scrum.basic.ui.services.SprintService;
import com.microservices.scrum.basic.ui.services.StoryService;
import com.microservices.scrum.basic.ui.services.TaskService;

@Controller
public class TaskController {

	@Autowired
	private StoryService storyService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private SprintService sprintService;

	@GetMapping("/task/{id}")
	public String taskForm(@PathVariable int id, Model model) {
		model.addAttribute("task", new Task());
		model.addAttribute("storyId", id);
		return "task";
	}

	@PostMapping("/task/{id}")
	public String taskSubmit(@PathVariable int id, @ModelAttribute Task task) {
		storyService.addTask(id, task);
		return "redirect:/story/" + id;
	}

	@GetMapping("/project/{projectId}/sprint/{sprintId}/task/{taskId}/update")
	public String update(@PathVariable int projectId, @PathVariable int sprintId, @PathVariable int taskId,
			Model model) {
		String today = sprintService.getSprintDay(sprintId);
		Task task = taskService.getTask(taskId);
		if (task.getRemainingUpdated() == null) {
			task.setRemainingUpdated(today);
		}
		model.addAttribute("task", task);
		model.addAttribute("projectId", projectId);
		model.addAttribute("sprintId", sprintId);
		model.addAttribute("taskId", taskId);
		model.addAttribute("days", sprintService.getSprintDays(sprintId, task));
		model.addAttribute("today", today);
		return "updateremainingtime";
	}

	@PostMapping("/project/{projectId}/sprint/{sprintId}/task/{taskId}/update")
	public String updateSubmit(@PathVariable int projectId, @PathVariable int sprintId, @PathVariable int taskId,
			@ModelAttribute Task task) {
		taskService.updateRemainingTime(new UpdateRemainingTime().setTaskId(taskId)
				.setRemainingTime(task.getRemainingHours()).setUpdateTime(task.getRemainingUpdated()));
		return "redirect:/project/" + projectId + "/sprint/" + sprintId + "/board";
	}

}
