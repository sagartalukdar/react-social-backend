package com.react.service;

import java.util.List;

import com.react.model.Message;
import com.react.model.User;

public interface MessageService {

	public Message createdMessage(User user,Integer chatId,Message message)throws Exception;
	
	public List<Message> findChatsMessages(Integer chatId)throws Exception;
	
}
