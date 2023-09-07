package com.project.ipyang.domain.chat.repository;

import com.project.ipyang.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByRoomId(String roomId);
    Optional<ChatRoom> findByRoomId(String roomId);
    List<ChatRoom> findAllByMemberId(String memberId);
    void deleteAllByRoomId(String roomId);
    Optional<ChatRoom> findByRoomIdAndMemberId(String roomId, Long member_id);

    //채팅방 정보와 해당 채팅방의 최신 메시지를 함께 조회
    @Query(value = "select message, M.room_id, to_member_id, member_id, created_at from (\n" +
            "       select room_id, to_member_id from chat_room where member_id = :member_id) R, (\n" +
            "       select * from (\n" +
            "       select * from chat_message where (room_id, created_at) in (select room_id, max(created_at) as a from chat_message group by room_id)) A)\n" +
            "       M where M.room_id = R.room_id order by created_at desc;", nativeQuery = true)
    List<Map<Object, Object>> findRoomList(@Param("member_id") Long member_id);

}
