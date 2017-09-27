package com.microservices.scrum.basic.ui.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.scrum.basic.ui.messages.StartSprint;
import com.microservices.scrum.basic.ui.model.Sprint;
import com.microservices.scrum.basic.ui.model.Story;

@Service
public class SprintService {

	@Autowired
	private StoryService storyService;

	public Sprint getSprint(int projectId, int sprintId) {
		Sprint sprint = new RestTemplate().getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/sprint/{id}",
				Sprint.class, sprintId);
		sprint.setProjectId(projectId);
		List<Story> stories = storyService.getProjectStories(projectId);
		return sprint.addStories(stories);
	}

	@SuppressWarnings({ "rawtypes" })
	public List<Sprint> getProjectSprints(int projectId) {
		List objects =  new RestTemplate().getForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/project/{id}/sprints",
				List.class, projectId);
		List<Sprint> sprints = new ArrayList<Sprint>();
		ObjectMapper mapper = new ObjectMapper();
		for (Object object : objects) {
			Sprint sprint = mapper.convertValue(object, new TypeReference<Sprint>() {
			});
			sprints.add(sprint);
		}
		return sprints;
		
	}

	public Sprint addStoryToSprint(int projectId, int sprintId, int storyId) {
		storyService.addStoryToSprint(sprintId, storyId);
		Sprint sprint = getSprint(projectId, sprintId);
		return sprint;
	}

	public void startSprint(int projectId, StartSprint startSprint) {
		startSprint.setProjectId(projectId);
		new RestTemplate().postForObject(System.getenv("SPRINT_API_SERVICE_URI") + "/start-sprint", startSprint,
				Sprint.class);
	}

}
