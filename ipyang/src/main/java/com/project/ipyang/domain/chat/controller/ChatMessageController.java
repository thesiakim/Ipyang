package com.project.ipyang.domain.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.domain.chat.dto.ChatMessageRequestDto;
import com.project.ipyang.domain.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    //메시지 처리
    @MessageMapping(value = "/v1/chat/message")
    public void message(ChatMessageRequestDto message, @Header("myNickName") String myNickName) throws JsonProcessingException {
        chatMessageService.message(message, myNickName);
    }

    //채팅 내역 조회
    @GetMapping(value = "/v1/chat/message/{roomId}")
    public ResponseDto getMessage(@PathVariable String roomId) {
        return chatMessageService.getMessage(roomId);
    }
}
