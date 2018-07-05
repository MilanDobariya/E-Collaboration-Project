package com.niit.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.niit.model.Chat;

@Controller
public class SockController {

private final SimpMessagingTemplate messagingTemplate;
private List<String> users=new ArrayList<String>();

@Autowired
public SockController(SimpMessagingTemplate messagingTemplate){
	super();
	this.messagingTemplate = messagingTemplate;
	
}
@SubscribeMapping("/join/{username}")
public List<String> join(@DestinationVariable String username){
	System.out.println("Newly joined usernmae is "+ username);
	if(!users.contains(username))
		users.add(username);
	messagingTemplate.convertAndSend("/topic/join", username);
	return users;
	
}

@MessageMapping(value="/chat")
public void chatReceived(Chat chat){
	if(chat.getTo().equals("all")){
		//System.out.println("in chat Middle controller");
		System.out.println("IN CHAT REVEIVED " + chat.getMessage() + " " + chat.getFrom() + " to " + chat.getTo());
		messagingTemplate.convertAndSend("/queue/chats", chat);
	}
	else{
		//System.out.println("in chat Middle controller 1");
		

		System.out.println("CHAT TO " + chat.getTo() + " From " + chat.getFrom() + " Message " + chat.getMessage());

		messagingTemplate.convertAndSend("/queue/chats/"+chat.getTo(), chat);
		messagingTemplate.convertAndSend("/queue/chats/"+chat.getFrom(), chat);
		//System.out.println("after chat Middle controller");
	}
}
}
