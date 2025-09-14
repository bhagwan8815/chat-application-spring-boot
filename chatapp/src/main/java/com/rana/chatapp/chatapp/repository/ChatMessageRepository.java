package com.rana.chatapp.chatapp.repository;

import com.rana.chatapp.chatapp.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage , Long> {
}
