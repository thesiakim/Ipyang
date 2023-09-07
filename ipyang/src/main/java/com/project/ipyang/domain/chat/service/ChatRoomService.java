package com.project.ipyang.domain.chat.service;

import com.project.ipyang.common.response.ResponseDto;
import com.project.ipyang.config.SessionUser;
import com.project.ipyang.domain.chat.dto.ChatResponseDto;
import com.project.ipyang.domain.chat.entity.ChatRoom;
import com.project.ipyang.domain.chat.repository.ChatMessageRepository;
import com.project.ipyang.domain.chat.repository.ChatRepositoryImpl;
import com.project.ipyang.domain.chat.repository.ChatRoomRepository;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatRepositoryImpl chatRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final HttpSession session;

    //roomId 중복 검사
    public String roomIdCheck(String roomId) {
        String checkId = roomId;
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByRoomId(checkId);
        while (!chatRooms.isEmpty()) {
            checkId = String.valueOf(ThreadLocalRandom.current().nextInt(100000000, 1000000000));
            chatRooms = chatRoomRepository.findAllByRoomId(checkId);
        }
        return checkId;
    }

    //채팅방 생성
    @Transactional
    public ResponseDto createRoom(String nickName) {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();
        Member fromMember = memberRepository.findById(memberId).get();

        Optional<Member> member = memberRepository.findByNickname(nickName);
        if(member.isEmpty()) return new ResponseDto("존재하지 않는 회원입니다", HttpStatus.INTERNAL_SERVER_ERROR.value());
        Member toMember = member.get();

        List<String> getRoomId = chatRepository.getRoomId(fromMember.getId(), toMember.getId());
        if(getRoomId.size() > 0) {
            log.info("채팅한 적 있음");
            return new ResponseDto(getRoomId.get(0), HttpStatus.OK.value());
        }

        String roomId = roomIdCheck(String.valueOf(ThreadLocalRandom.current().nextInt(100000000, 1000000000)));
        ChatRoom chatRoom1 = new ChatRoom(fromMember, toMember, roomId);
        ChatRoom chatRoom2 = new ChatRoom(toMember, fromMember, roomId);
        chatRoomRepository.save(chatRoom1);
        chatRoomRepository.save(chatRoom2);

        return new ResponseDto(roomId, HttpStatus.OK.value());
    }

    //채팅방 목록 조회
    @Transactional
    public ResponseDto getRoomList() {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();

        List<Map<Object, Object>> lists = chatRoomRepository.findRoomList(memberId);

        List<ChatResponseDto.RoomIdResponseDto> collect = lists.stream().map(list -> {
                Long toMemberId = Long.valueOf(String.valueOf(list.get("to_member_id")));
                Member toMember = memberRepository.findById(toMemberId).orElseThrow();
                Long count = chatRepository.countMessage(String.valueOf(list.get("room_id")), memberId);
                return new ChatResponseDto.RoomIdResponseDto(list, toMember, count);
            }).collect(Collectors.toList());

        return new ResponseDto(collect, HttpStatus.OK.value());
    }

    //채팅방 나가기
    @Transactional
    public void deleteRooms(String roomId) {
        chatRoomRepository.deleteAllByRoomId(roomId);
        chatMessageRepository.deleteAllByRoomId(roomId);
    }

    public ResponseDto totalCountMessage() {
        SessionUser loggedInUser = (SessionUser) session.getAttribute("loggedInUser");
        Long memberId = loggedInUser.getId();
        Long count = chatRepository.countTotalMessage(memberId);

        ChatResponseDto.TotalCountMessageDto totalCountMessageDto = new ChatResponseDto.TotalCountMessageDto(count);

        return new ResponseDto(totalCountMessageDto, HttpStatus.OK.value());
    }
}
