package com.estafet.microservices.scrum.basic.ui.service;

import com.estafet.microservices.scrum.basic.ui.model.Story;
import com.estafet.microservices.scrum.lib.util.WaitUntil;

public class WaitUntilStoryUpdated extends WaitUntil {

	private Integer storyId;
	private StoryService storyService;
	
	public WaitUntilStoryUpdated(Integer storyId, StoryService storyService) {
		this.storyId = storyId;
		this.storyService = storyService;
	}

	@Override
	public boolean success() {
		Story story = storyService.getStory(storyId);
		return story.getStatus().equals("Planning") || story.getStatus().equals("Progress");
	}

}
