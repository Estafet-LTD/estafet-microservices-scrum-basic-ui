package com.estafet.microservices.scrum.basic.ui.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.scrum.basic.ui.messages.AcceptanceCriteriaDetails;
import com.estafet.microservices.scrum.basic.ui.messages.AddSprintStory;
import com.estafet.microservices.scrum.basic.ui.model.AcceptanceCriterion;
import com.estafet.microservices.scrum.basic.ui.model.Story;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.opentracing.Tracer;

@Service
public class StoryService {
	private static final String STORY_API_SERVICE_URI = System.getenv("STORY_API_SERVICE_URI");
//	private static final String STORY_API_SERVICE_URI = "http://localhost:8089";
			
	@Autowired
	private Tracer tracer;
		
	@Autowired
	private RestTemplate restTemplate;

	@Retryable(maxAttempts = 3, backoff = @Backoff(delay=200))
	@SuppressWarnings({ "rawtypes" })
	public List<Story> getProjectStories(int projectId) {
		tracer.activeSpan().setTag("project.id", projectId);
		List objects = restTemplate.getForObject(STORY_API_SERVICE_URI + "/project/{id}/stories", List.class, projectId);
		List<Story> stories = new ArrayList<Story>();
		ObjectMapper mapper = new ObjectMapper();
		for (Object object : objects) {
			Story story = mapper.convertValue(object, new TypeReference<Story>() {
			});
			story.setRestTemplate(restTemplate);
			stories.add(story);
		}
		return stories;
	}

	public void addStoryToSprint(int sprintId, int storyId) {
		tracer.activeSpan().setTag("sprint.id", sprintId);
		tracer.activeSpan().setTag("story.id", storyId);
		restTemplate.postForObject(STORY_API_SERVICE_URI + "/add-story-to-sprint",
				new AddSprintStory().setSprintId(sprintId).setStoryId(storyId), Story.class);
	}

	@Retryable(maxAttempts = 3, backoff = @Backoff(delay=200))
	public Story getStory(int storyId) {
		tracer.activeSpan().setTag("story.id", storyId);
		Story story = restTemplate.getForObject(STORY_API_SERVICE_URI + "/story/{id}", Story.class,
				storyId);
		story.setRestTemplate(restTemplate);
		return story;
	}

	public Story addAcceptanceCriteria(int storyId, AcceptanceCriterion criteria) {
		tracer.activeSpan().setTag("story.id", storyId);
		Story story = restTemplate.postForObject(STORY_API_SERVICE_URI + "/story/{id}/criteria",
				new AcceptanceCriteriaDetails().setCriteria(criteria.getDescription()), Story.class, storyId);
		story.setRestTemplate(restTemplate);
		return story;
	}

	public Story addStory(int projectId, Story story) {
		tracer.activeSpan().setTag("project.id", projectId);
		story = restTemplate.postForObject(STORY_API_SERVICE_URI + "/project/{id}/story", story, Story.class,
				projectId);
		story.setRestTemplate(restTemplate);
		tracer.activeSpan().setTag("story.id", story.getId());
		return story;
	}

}
