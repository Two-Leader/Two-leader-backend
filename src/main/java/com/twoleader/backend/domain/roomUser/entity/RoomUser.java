package com.twoleader.backend.domain.roomUser.entity;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.global.common.BaseEntity;
import javax.persistence.*;
import lombok.*;

@Entity
@ToString(
    of = {"roomUserId", "roomUserName"},
    callSuper = true)
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    name = "room_users",
    uniqueConstraints = { // Openvidu 연동 시 특정 Id가 필요해 복합 기본키가 아닌 복합 Unique키로 설정.
      @UniqueConstraint(
          name = "UniqueUserAndStudyRoom",
          columnNames = {"users_id", "study_rooms_id"})
    })
public class RoomUser extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long roomUserId;

  @Column(nullable = false)
  private String roomUserName;

  @Column(nullable = false)
  private Boolean online;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "study_rooms_id", nullable = false) // INNER JOIN을 하기위해 nullable을 false로 설정
  private StudyRoom studyRoom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "users_id", nullable = false) // INNER JOIN을 하기위해 nullable을 false로 설정
  private User user;

  public void changeOnline() {
    this.online = true;
  }

  public void changeOffline() {
    this.online = false;
  }
}
