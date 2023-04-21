package com.twoleader.backend.domain.user.entity;


import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String user_uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private StudyRoom room;
}
