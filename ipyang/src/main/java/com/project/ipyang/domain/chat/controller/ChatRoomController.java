package com.project.ipyang.domain.chat.controller;

import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/chat")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    //채팅방 생성
    @PostMapping("/rooms/{nickname}")
    public ResponseDto createRoom(@PathVariable String nickName) {
        return chatRoomService.createRoom(nickName);
    }

    //채팅방 목록 조회
    @GetMapping("/rooms")
    public ResponseDto getRoomsList() {
        return chatRoomService.getRoomList();
    }

    //채팅방 나가기
    @DeleteMapping("/rooms/{roomId}")
    public void deleteRooms(@PathVariable String roomId) {
        chatRoomService.deleteRooms(roomId);
    }

    @GetMapping("/message/count")
    public ResponseDto totalCountMessage() {
        return chatRoomService.totalCountMessage();
    }
}