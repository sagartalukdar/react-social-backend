package com.react.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.react.model.Story;
import com.react.model.User;
import com.react.service.StoryService;
import com.react.service.UserService;

@RestController
public class StoryController {

	@Autowired
	private StoryService storyService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/story")
	public Story createStory(@RequestBody Story story,@RequestHeader("Authorization")String jwt) {
		User reqUser=userService.findUserByJwt(jwt);		
		Story createdStory=storyService.createStory(story, reqUser);
		return createdStory;
	}
	@GetMapping("/api/story/user/{userId}")
	public List<Story> findUserStory(@PathVariable("userId")Integer userId,@RequestHeader("Authorization")String jwt) throws Exception{
		User reqUser=userService.findUserByJwt(jwt);
		List<Story> stories=storyService.findStoryByUserId(userId);
		return stories;
	}
}
