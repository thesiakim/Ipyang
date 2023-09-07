package com.project.ipyang.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatRoomListResponseDto {
    String nickname;
    String message;
    LocalDateTime createdAt;

}
