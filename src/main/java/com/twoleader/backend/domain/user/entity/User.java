package com.twoleader.backend.domain.user.entity;


import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.user.dto.response.GetUserResponse;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false)
    @Builder.Default
    private UUID user_uuid = UUID.randomUUID();

    @Column(nullable = false)
    private String user_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private StudyRoom room;

    public GetUserResponse toDto(){
        return GetUserResponse.builder()
                .user_uuid(this.user_uuid)
                .user_name(this.user_name)
                .build();
    }
}
