package com.twoleader.backend.domain.studyRoom.entity;

import java.util.UUID;
import javax.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString(callSuper = true)
@Table(name = "studyRooms")
public class StudyRoom {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long room_id;

  @Column(nullable = false, columnDefinition = "BINARY(16)")
  @Builder.Default
  private UUID room_uuid = UUID.randomUUID();

  @Column(nullable = false)
  private String room_name;
}
