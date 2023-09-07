package com.project.ipyang.domain.chat.dto;

import com.project.ipyang.common.IpyangEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequestDto {
    private IpyangEnum.MessageType type;
    private String roomId;
    private String sender;
    private String message;
}

