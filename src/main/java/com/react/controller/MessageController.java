package com.react.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.react.model.Message;
import com.react.model.User;
import com.react.service.MessageService;
import com.react.service.UserService;

@RestController
public class MessageController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/messages/chat/{chatId}")
	public Message createMessage(@RequestBody Message message,@RequestHeader("Authorization")String jwt,@PathVariable("chatId")Integer chatId) throws Exception {
		User reqUser=userService.findUserByJwt(jwt);
		return messageService.createdMessage(reqUser, chatId, message);
	}
	@GetMapping("/api/messages/chat/{chatId}")
	public List<Message> findChatsMessages(@RequestHeader("Authorization")String jwt,@PathVariable("chatId")Integer chatId) throws Exception{
		User reqUser=userService.findUserByJwt(jwt);
		
		return messageService.findChatsMessages(chatId);
	}
}
