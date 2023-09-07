package com.project.ipyang.domain.chat.entity;


import com.project.ipyang.common.IpyangEnum;
import com.project.ipyang.common.entity.BaseEntity;
import com.project.ipyang.domain.chat.dto.ChatMessageRequestDto;
import com.project.ipyang.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class ChatMessage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String roomId;

    private String message;

    @Enumerated(EnumType.STRING)
    private IpyangEnum.MessageType type;

    private boolean readUser;

    public ChatMessage(ChatMessageRequestDto message, Member member, boolean readUser) {
        this.roomId = message.getRoomId();
        this.message = message.getMessage();
        this.member = member;
        this.readUser = readUser;
    }

    public void TrueReadUser() {
        readUser = true;
    }

}
