package com.twoLeader.twoLeader.domain.user.entity;

import com.twoLeader.twoLeader.domain.studyRoom.entity.StudyRoom;
import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @GeneratedValue(strategy = GenerationType.UUID)
    private String user_uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private StudyRoom room;
}
