package com.twoleader.backend.domain.studyRoom.entity;


import com.twoleader.backend.domain.user.entity.User;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ToString(callSuper = true)
@Table(name = "studyRooms")
public class StudyRoom {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long roomId;

  @Column(nullable = false, columnDefinition = "BINARY(16)")
  @Builder.Default
  private UUID roomUuid = UUID.randomUUID();

  @Column(nullable = false)
  private String roomName;

  @OneToMany(
      mappedBy = "studyRoom",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<User> users;
}
