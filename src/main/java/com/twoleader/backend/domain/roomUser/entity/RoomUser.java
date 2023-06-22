package com.twoleader.backend.domain.roomUser.entity;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;

@Entity
@ToString(callSuper = true)
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "room_users")
public class RoomUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(nullable = false, columnDefinition = "BINARY(16)")
  @Builder.Default
  private UUID userUuid = UUID.randomUUID();

  @Column(nullable = false)
  private String userName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id", nullable = false)
  private StudyRoom studyRoom;
}
