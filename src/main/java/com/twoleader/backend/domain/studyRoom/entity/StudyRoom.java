package com.twoleader.backend.domain.studyRoom.entity;

import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.global.common.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@ToString(callSuper = true)
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

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false) // nullable을 false로 해야 INNER JOIN함. => 성능 향상
  private User constructor;

  @OneToMany(mappedBy = "studyRoom", fetch = FetchType.LAZY)
  private List<RoomUser> users = new ArrayList<>();
}
