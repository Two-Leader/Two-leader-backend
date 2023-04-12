package com.twoLeader.twoLeader.domain.studyRoom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @GeneratedValue(strategy = GenerationType.UUID)
    @Builder.Default
    private UUID room_uuid = UUID.randomUUID();

    @Column(nullable = false)
    private String room_name;
}
