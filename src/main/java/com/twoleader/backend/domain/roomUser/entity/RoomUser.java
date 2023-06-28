package com.twoleader.backend.domain.roomUser.entity;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.global.common.BaseEntity;
import javax.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@ToString
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
// Openvidu 연동 시 특정 Id가 필요해 복합 기본키가 아닌 복합 Unique키로 설정.
@Table(name = "room_users",uniqueConstraints = {@UniqueConstraint(name = "UniqueUserAndStudyRoom",columnNames = {"users_id","study_rooms_id"})})
public class RoomUser extends BaseEntity {

  @Column(nullable = false)
  private String roomUserName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="study_rooms_id",nullable = false) //INNER JOIN을 하기위해 nullable을 false로 설정
  private StudyRoom studyRoom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "users_id",nullable = false) //INNER JOIN을 하기위해 nullable을 false로 설정
  private User user;
}
