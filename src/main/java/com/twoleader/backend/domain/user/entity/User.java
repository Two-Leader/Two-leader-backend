package com.twoleader.backend.domain.user.entity;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
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
  private Long user_id;

  //  @GeneratedValue(generator = "uuid2")
  //  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(nullable = false, columnDefinition = "BINARY(16)")
  @Builder.Default
  private UUID user_uuid = UUID.randomUUID();

  @Column(nullable = false)
  private String user_name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id")
  private StudyRoom room;
}
