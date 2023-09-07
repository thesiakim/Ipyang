package com.project.ipyang.domain.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.domain.chat.dto.ChatMessageRequestDto;
import com.project.ipyang.domain.chat.dto.ChatResponseDto;
import com.project.ipyang.domain.chat.entity.ChatMessage;
import com.project.ipyang.domain.chat.entity.ChatRoom;
import com.project.ipyang.domain.chat.repository.ChatMessageRepository;
import com.project.ipyang.domain.chat.repository.ChatRepositoryImpl;
import com.project.ipyang.domain.chat.repository.ChatRoomRepository;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatMessageService {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRepositoryImpl chatRepository;
    private final MemberRepository memberRepository;

    //메시지 처리
    @Transactional
    public void message(ChatMessageRequestDto request, String myNickName) throws JsonProcessingException {
        Optional<Member> member = memberRepository.findByNickname(myNickName);
        if(member.isEmpty()) throw new IllegalArgumentException("해당 회원이 존재하지 않습니다");
        Member findMember = member.get();

        if (IpyangEnum.MessageType.ENTER.equals(request.getType())) {
            List<ChatRoom> chatRooms = chatRoomRepository.findAllByRoomId(request.getRoomId());
            chatRooms.forEach(ChatRoom::plusUser);
            List<ChatMessage> chatMessages = chatRepository.falseMessage(request.getRoomId(), findMember.getId());
            chatMessages.forEach(ChatMessage::TrueReadUser);
            log.info("CHAT ENTER");
        }
        if (IpyangEnum.MessageType.QUIT.equals(request.getType())) {
            List<ChatRoom> chatRooms = chatRoomRepository.findAllByRoomId(request.getRoomId());
            chatRooms.forEach(ChatRoom::minusUser);
            log.info("CHAT QUIT");
        }
        if (IpyangEnum.MessageType.TALK.equals(request.getType())) {
            ChatRoom chatRoom = chatRoomRepository.findByRoomIdAndMemberId(request.getRoomId(), findMember.getId()).orElse(new ChatRoom());
            boolean countUser = chatRoom.getCountUser() == 2;
            ChatMessage chatMessage = new ChatMessage(request, findMember, countUser);
            chatMessageRepository.save(chatMessage);
            messagingTemplate.convertAndSend("/sub/chat/room/" + request.getRoomId(), request);
        }
    }

    //채팅 내역 조회
    @Transactional
    public ResponseDto getMessage(String roomId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByRoomId(roomId);
        List<ChatResponseDto> chatResponseDtos = chatMessages.stream().map(ChatResponseDto::new).collect(Collectors.toList());

        return new ResponseDto(chatResponseDtos, HttpStatus.OK.value());
    }
}