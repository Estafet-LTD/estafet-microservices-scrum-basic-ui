package com.microservices.scrum.basic.ui.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.scrum.basic.ui.messages.AcceptanceCriteriaDetails;
import com.microservices.scrum.basic.ui.messages.AddSprintStory;
import com.microservices.scrum.basic.ui.model.AcceptanceCriterion;
import com.microservices.scrum.basic.ui.model.Story;
import com.microservices.scrum.basic.ui.model.Task;

@Service
public class StoryService {

	@Autowired
	private ProjectService projectService;

	@SuppressWarnings({ "rawtypes" })
	public List<Story> getProjectStories(int projectId) {
		List objects = new RestTemplate().getForObject(System.getenv("STORY_API_SERVICE_URI") + "/project/{id}/stories",
				List.class, projectId);
		List<Story> stories = new ArrayList<Story>();
		ObjectMapper mapper = new ObjectMapper();
		for (Object object : objects) {
			Story story = mapper.convertValue(object, new TypeReference<Story>() {
			});
			stories.add(story);
		}
		return stories;
	}

	public void addStoryToSprint(int sprintId, int storyId) {
		new RestTemplate().postForObject(System.getenv("STORY_API_SERVICE_URI") + "/add-story-to-sprint",
				new AddSprintStory().setSprintId(sprintId).setStoryId(storyId), Story.class);
	}

	public Story getStory(int storyId) {
		Story story = new RestTemplate().getForObject(System.getenv("STORY_API_SERVICE_URI") + "/story/{id}", Story.class,
				storyId);
		story.setProject(projectService.getProject(story.getProjectId()));
		return story;
	}

	public Story addAcceptanceCriteria(int storyId, AcceptanceCriterion criteria) {
		return new RestTemplate().postForObject(System.getenv("STORY_API_SERVICE_URI") + "/story/{id}/criteria",
				new AcceptanceCriteriaDetails().setCriteria(criteria.getDescription()), Story.class, storyId);
	}

	public Story addTask(int storyId, Task task) {
		return new RestTemplate().postForObject(System.getenv("STORY_API_SERVICE_URI") + "/story/{id}/task", task, Story.class,
				storyId);
	}

	public Story addStory(int projectId, Story story) {
		return new RestTemplate().postForObject(System.getenv("STORY_API_SERVICE_URI") + "/project/{id}/story", story, Story.class,
				projectId);
	}

}
