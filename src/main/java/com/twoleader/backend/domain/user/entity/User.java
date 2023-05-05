package com.twoleader.backend.domain.user.entity;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.user.dto.response.GetUserResponse;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;

@Entity
@ToString(callSuper = true)
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(nullable = false, columnDefinition = "BINARY(16)")
  @Builder.Default
  private UUID userUuid = UUID.randomUUID();

  @Column(nullable = false)
  private String userName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id")
  private StudyRoom room;


}
