package com.twoleader.backend.domain.studyRoom.entity;

import com.twoleader.backend.domain.studyRoom.dto.response.FindStudyRoomDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
public class StudyRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long room_id;

    @Builder.Default
    private UUID room_uuid = UUID.randomUUID();

    @Column(nullable = false)
    private String room_name;
}
