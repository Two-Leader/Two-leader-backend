package com.twoleader.backend.domain.studyRoom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long room_id;

    @Builder.Default
    private UUID room_uuid = UUID.randomUUID();

    @Column(nullable = false)
    private String room_name;
}
