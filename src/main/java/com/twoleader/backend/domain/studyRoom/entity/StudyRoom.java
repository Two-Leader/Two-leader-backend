package com.twoleader.backend.domain.studyRoom.entity;

import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
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

  @Column(nullable = false,columnDefinition = "BINARY(16)")
  @Builder.Default
  private UUID room_uuid = UUID.randomUUID();

  @Column(nullable = false)
  private String room_name;

  //  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
  //  private List<User> users = new ArrayList<>();

  public GetStudyRoomResponse toDto(Boolean hasUser) {
    return GetStudyRoomResponse.builder()
        .room_uuid(this.room_uuid)
        .room_name(this.room_name)
        .hasUser(hasUser)
        .build();
  }
  public GetStudyRoomResponse toDto() {
    return GetStudyRoomResponse.builder()
            .room_uuid(this.room_uuid)
            .room_name(this.room_name)
            .build();
  }
}
