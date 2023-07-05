package com.twoleader.backend.domain.studyRoom.entity;

import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.global.common.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ToString(
    of = {"studyRoomId", "roomUuid", "roomName", "information", "password", "tnop"},
    callSuper = true)
@Table(name = "study_rooms")
public class StudyRoom extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long studyRoomId;

  @Column(nullable = false, columnDefinition = "BINARY(16)")
  @Builder.Default
  private UUID roomUuid = UUID.randomUUID();

  @Column(nullable = false)
  private String roomName;

  @Column private String information;

  @Column private String password;

  @Column(nullable = false)
  private int totalNop;

  @Column(nullable = false)
  private int nowTotalNop;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User constructor;

  @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "studyRoom",
      fetch = FetchType.LAZY,
      orphanRemoval = true)
  private List<RoomUser> roomUsers = new ArrayList<>();

  public void addRoomUser() {
    this.nowTotalNop++;
  }

  public void deleteRoomUser() {
    this.nowTotalNop--;
  }
}
