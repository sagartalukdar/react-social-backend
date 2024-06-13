package com.react.service;

import java.util.List;

import com.react.model.Story;
import com.react.model.User;

public interface StoryService {

	public Story createStory(Story story,User user);
	
	public List<Story> findStoryByUserId(Integer userId)throws Exception;
}
