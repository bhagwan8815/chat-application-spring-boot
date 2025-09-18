package com.rana.chatapp.chatapp.controller;

import com.rana.chatapp.chatapp.entity.ChatMessage;
import com.rana.chatapp.chatapp.repository.ChatMessageRepository;
import com.rana.chatapp.chatapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;




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

    if(userService.userExists(chatMessage.getSender() ) && userService.userExists(chatMessage.getRecipient())){
        if(chatMessage.getTimestamp()==null){
            chatMessage.setTimestamp(LocalDateTime.now());
        }
        chatMessage.setType(ChatMessage.MessageType.PRIVATE_MESSAGE);

        if(chatMessage.getContent()==null){
            chatMessage.setContent("");
        }

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        System.out.println("Message saved successfully with id :" + savedMessage.getId());

       try {
           String recepientDestination = "/user/"+chatMessage.getRecipient()+"/queue/private";
           System.out.println("sending message to recepient destination " + recepientDestination);
           messagingTemplate.convertAndSend(recepientDestination , savedMessage);

           String senderDestination = "/user/"+chatMessage.getSender() + "/queue/private";
           System.out.println("sending message to sender destination " + senderDestination);
           messagingTemplate.convertAndSend(senderDestination , savedMessage);
       }catch (Exception e){
           System.out.println("ERROR occured while sending the message " + e.getMessage());
           e.printStackTrace();
       }

    }else{
        System.out.println("Error:Sender " + chatMessage.getSender() + " or recepient " + chatMessage.getRecipient()+" does not not exits");
    }


}
}
//1.2