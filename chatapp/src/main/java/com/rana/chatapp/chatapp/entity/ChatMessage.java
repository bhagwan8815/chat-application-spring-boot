package com.rana.chatapp.chatapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String content;
    private String sender;
    private String recepient;
    private String color;
    @Column(nullable = false)
    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    private MessageType type;

    public enum MessageType {
        CHAT, PRIVATE_MESSAGE, JOIN, LEAVE, TYPING
    }
}
