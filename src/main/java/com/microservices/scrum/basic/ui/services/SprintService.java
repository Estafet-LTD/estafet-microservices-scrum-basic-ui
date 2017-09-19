package com.microservices.scrum.basic.ui.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.scrum.basic.ui.messages.StartSprint;
import com.microservices.scrum.basic.ui.model.Sprint;
import com.microservices.scrum.basic.ui.model.Story;

@Service
public class SprintService {

	@Autowired
	private StoryService storyService;

	public Sprint getSprint(int projectId, int sprintId) {
		Sprint sprint = new RestTemplate().getForObject("http://localhost:8080/sprint-api/sprint/{id}", Sprint.class,
				sprintId);
		sprint.setProjectId(projectId);
		List<Story> stories = storyService.getProjectStories(projectId);
		return sprint.addStories(stories);
	}

	public Sprint addStoryToSprint(int projectId, int sprintId, int storyId) {
		storyService.addStoryToSprint(sprintId, storyId);
		Sprint sprint = getSprint(projectId, sprintId);
		return sprint;
	}

	public void startSprint(int projectId, StartSprint startSprint) {
		startSprint.setProjectId(projectId);
		new RestTemplate().postForObject("http://localhost:8080/sprint-api/start-sprint", startSprint, Sprint.class);
	}

}
