package com.twoleader.backend.domain.studyRoom.entity;

import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString(callSuper = true)
public class StudyRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long room_id;

    @Column(nullable = false)
    @Builder.Default
    private UUID room_uuid = UUID.randomUUID();

    @Column(nullable = false)
    private String room_name;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<User> users = new ArrayList<>();

    public GetStudyRoomResponse toDto(){
        return GetStudyRoomResponse.builder()
                .room_uuid(this.room_uuid)
                .room_name(this.room_name)
                .build();
    }
}
