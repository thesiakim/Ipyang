package com.project.ipyang.domain.chat.entity;

import com.project.ipyang.common.entity.BaseEntity;
import com.project.ipyang.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_member_id")
    private Member toMember;

    private int countUser;

    public ChatRoom(Member member1, Member member2, String roomId) {
        this.member = member1;
        this.toMember = member2;
        this.roomId = roomId;
    }

    public void plusUser() {
        countUser++;
    }

    public void minusUser() {
        countUser--;
    }
}
