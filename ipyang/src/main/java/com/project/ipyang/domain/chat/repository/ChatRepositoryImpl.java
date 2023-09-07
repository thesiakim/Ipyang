package com.project.ipyang.domain.chat.repository;

import com.project.ipyang.domain.chat.dto.ChatRoomListResponseDto;
import com.project.ipyang.domain.chat.entity.ChatMessage;
import com.project.ipyang.domain.member.entity.Member;
import com.project.ipyang.domain.member.entity.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.ipyang.domain.chat.entity.QChatMessage.chatMessage;
import static com.project.ipyang.domain.chat.entity.QChatRoom.chatRoom;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatRoomListResponseDto> chatRoomList(Member member) {
        List<ChatRoomListResponseDto> chatRoomList = queryFactory
                .select(Projections.fields(ChatRoomListResponseDto.class,
                        QMember.member.nickname,
                        chatMessage.message,
                        chatMessage.createdAt.max().as("createdAt"))
                ).from(chatRoom)
                .leftJoin(chatMessage)
                .on(chatRoom.roomId.eq(chatMessage.roomId))
                .leftJoin(chatMessage)
                .on(chatRoom.toMember.id.eq(chatMessage.member.id))
                .where(chatRoom.member.id.eq(member.getId()))
                .groupBy(QMember.member.nickname)
                .fetch();
        return chatRoomList;
    }



    @Override
    public List<String> getRoomId(Long memberId1, Long memberId2) {
        List<String> getRoomId = queryFactory
                .select(chatRoom.roomId)
                .from(chatRoom)
                .where(chatRoom.member.id.eq(memberId1)
                        .or(chatRoom.member.id.eq(memberId2)))
                .groupBy(chatRoom.roomId)
                .having(chatRoom.roomId.count().gt(1))
                .fetch();
        return getRoomId;
    }

    @Override
    public List<ChatMessage> falseMessage(String roomId, Long memberId) {
        List<ChatMessage> falseMessage = queryFactory
                .select(chatMessage)
                .from(chatMessage)
                .where(chatMessage.roomId.eq(roomId)
                        .and(
                                chatMessage.member.id.notIn(memberId)
                        )
                        .and(
                                chatMessage.readUser.eq(false)
                        )
                ).fetch();
        return falseMessage;
    }

    @Override
    public Long countMessage(String roomId, Long memberId) {
        Long CountMessage = queryFactory
                .select(chatMessage.readUser.count())
                .from(chatMessage)
                .where(chatMessage.readUser.eq(false)
                        .and(
                                chatMessage.roomId.eq(roomId)
                        )
                        .and(
                                chatMessage.member.id.notIn(memberId)
                        )
                ).fetchOne();
        return CountMessage;
    }

    public Long countTotalMessage(Long memberId) {
        Long CountTotalMessage = queryFactory
                .select(chatMessage.count())
                .from(chatRoom, chatMessage)
                .where(chatRoom.member.id.eq(memberId)
                        .and(
                                chatRoom.roomId.eq(chatMessage.roomId)
                        )
                        .and(
                                chatMessage.readUser.eq(false)
                        )
                        .and(
                                chatMessage.member.id.notIn(memberId)
                        )
                ).fetchOne();
        return CountTotalMessage;
    }
}
