package com.react.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.react.model.Chat;
import com.react.model.Message;
import com.react.model.User;
import com.react.repository.ChatRepository;
import com.react.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private ChatService chatService;
	@Autowired
	private ChatRepository chatRepository;
	
	@Override
	public Message createdMessage(User user, Integer chatId, Message message) throws Exception {
		Message createdMessage=new Message();
		Chat chat=chatService.findChatById(chatId);
		
		createdMessage.setChat(chat);
		createdMessage.setContent(message.getContent());
		createdMessage.setImage(message.getImage());
		createdMessage.setUser(user);
		createdMessage.setTimeStamp(LocalDateTime.now());

		Message savedMessage=messageRepository.save(createdMessage);
		chat.getMessages().add(savedMessage);
	    chatRepository.save(chat);
		return savedMessage;
	}

	@Override
	public List<Message> findChatsMessages(Integer chatId) throws Exception {
		Chat chat=chatService.findChatById(chatId);
		
		return messageRepository.findByChatId(chatId);
	}

}
