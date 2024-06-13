package com.react.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.react.model.Reels;
import com.react.model.User;
import com.react.service.ReelsService;
import com.react.service.UserService;

@RestController
public class ReelsController {

	@Autowired
	private UserService userService;
	@Autowired
	private ReelsService reelsService;
	
	@PostMapping("/api/reels")
	public Reels createReels(@RequestBody Reels reel,@RequestHeader("Authorization")String jwt){
		User reqUser=userService.findUserByJwt(jwt);
		Reels createdReel=reelsService.createReel(reel, reqUser);
		return createdReel;
	}
	@GetMapping("/api/reels")
	public List<Reels> findAllReels(){
		return reelsService.findAllReels();
	}
	@GetMapping("/api/reels/user/{userId}")
	public List<Reels> findUserReels(@PathVariable("userId")Integer userId) throws Exception {
	   return reelsService.findUserReels(userId);
	}
}
