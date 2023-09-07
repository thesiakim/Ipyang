package com.project.ipyang.domain.chat.repository;

import com.project.ipyang.domain.chat.dto.ChatRoomListResponseDto;
import com.project.ipyang.domain.chat.entity.ChatMessage;
import com.project.ipyang.domain.member.entity.Member;

import java.util.List;

public interface ChatRepositoryCustom {
    List<ChatRoomListResponseDto> chatRoomList(Member member);
    List<String> getRoomId(Long memberId1, Long memberId2);
    Long countMessage(String roomId, Long memberId);
    List<ChatMessage> falseMessage(String roomId, Long memberId);
    Long countTotalMessage(Long memberId);
}
