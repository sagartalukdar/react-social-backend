package com.react.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.react.model.Chat;
import com.react.model.User;
import com.react.request.CreateChatRequest;
import com.react.service.ChatService;
import com.react.service.UserService;

@RestController
public class ChatController {

	@Autowired
	private ChatService chatService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/chats")
	public Chat creatChat(@RequestBody CreateChatRequest req,@RequestHeader("Authorization") String jwt) throws Exception {
		User reqUser=userService.findUserByJwt(jwt);
		User user2=userService.findUserById(req.getUserId());
		return chatService.createChat(reqUser, user2);
	}
	@GetMapping("/api/chats")
	public List<Chat> findUsersChats(@RequestHeader("Authorization") String jwt){
		User user=userService.findUserByJwt(jwt);
		return chatService.findUsersChat(user.getId());
	}
}
