package com.project.ipyang.domain.chat.repository;

import com.project.ipyang.domain.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    ChatMessage findByRoomId(String roomId);
    List<ChatMessage> findAllByRoomId(String roomId);
    Optional<ChatMessage> findTopByRoomIdOrderByIdDesc(String roomId);
    void deleteAllByRoomId(String roomId);
}
