package com.rana.chatapp.chatapp.controller;

import com.rana.chatapp.chatapp.entity.ChatMessage;
import com.rana.chatapp.chatapp.repository.ChatMessageRepository;
import com.rana.chatapp.chatapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatMessageRepository chatMessageRepository;





@MessageMapping("/chat.addUser")
@SendTo("/topic/public")
public ChatMessage addUser(@Payload ChatMessage chatMessage , SimpMessageHeaderAccessor headerAccessor) {

    if(userService.userExists(chatMessage.getSender())){
        //store username in session
        headerAccessor.getSessionAttributes().put("username" , chatMessage.getSender());
        userService.setUserOnlineStatus(chatMessage.getSender(), true);


        System.out.println("User added Successfully "  + chatMessage.getSender() + " with session id " +
                headerAccessor.getSessionId());

        chatMessage.setTimestamp(LocalDateTime.now());
        if(chatMessage.getContent()==null){
            chatMessage.setContent("");
        }

        return chatMessageRepository.save(chatMessage);
    }
    return null;
}


@MessageMapping("/chat.sendMessage")
@SendTo("/topic/public")
public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
    if(userService.userExists(chatMessage.getSender())){
        if(chatMessage.getTimestamp()==null){
            chatMessage.setTimestamp(LocalDateTime.now());
        }

        if(chatMessage.getContent()==null){
            chatMessage.setContent("");
        }

        return chatMessageRepository.save(chatMessage);
    }
    return null;



}



@MessageMapping("/chat.sendPrivateMessage")
public void sendPrivateMessage(@Payload ChatMessage chatMessage , SimpMessageHeaderAccessor headerAccessor){
    




}



}
