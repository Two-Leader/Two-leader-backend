package com.twoleader.backend.domain.roomUser.entity;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.global.common.BaseEntity;
import javax.persistence.*;
import lombok.*;

@Entity
@ToString(callSuper = true)
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "room_users")
public class RoomUser extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long roomUserId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String roomUserName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id", nullable = false)
  private StudyRoom studyRoom;
}
