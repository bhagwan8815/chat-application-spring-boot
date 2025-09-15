package com.rana.chatapp.chatapp.controller;

import com.rana.chatapp.chatapp.entity.ChatMessage;
import com.rana.chatapp.chatapp.repository.ChatMessageRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/messages")
public class MessageController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ResponseEntity<List<ChatMessage>> getPrivateMessages(@RequestParam String user1 , @RequestParam String user2){
        List<ChatMessage>messages = chatMessageRepository.findPrivateMessagesBetweenTwoUsers(user1, user2);
        return ResponseEntity.ok(messages);
    }
}
