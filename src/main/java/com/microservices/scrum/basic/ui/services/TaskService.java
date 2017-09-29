package com.microservices.scrum.basic.ui.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.scrum.basic.ui.messages.UpdateRemainingTime;
import com.microservices.scrum.basic.ui.model.Task;

@Service
public class TaskService {

	public void claim(int taskId) {
		new RestTemplate().postForObject(System.getenv("SPRINT_BOARD_API_SERVICE_URI") + "/task/{id}/claim", null,
				Task.class, taskId);
	}

	public void complete(int taskId) {
		new RestTemplate().postForObject(System.getenv("SPRINT_BOARD_API_SERVICE_URI") + "/task/{id}/complete", null,
				Task.class, taskId);
	}

	public Task getTask(int taskId) {
		return new RestTemplate().getForObject(System.getenv("STORY_API_SERVICE_URI") + "/task/{id}", Task.class,
				taskId);
	}

	public Task updateRemainingTime(UpdateRemainingTime message) {
		return new RestTemplate().postForObject(System.getenv("STORY_API_SERVICE_URI") + "/update-remaining-time",
				message, Task.class);
	}

}
