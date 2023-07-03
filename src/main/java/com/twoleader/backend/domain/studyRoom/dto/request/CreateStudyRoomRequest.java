package com.twoleader.backend.domain.studyRoom.dto.request;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudyRoomRequest {

  @NotBlank private String roomName;
  @NotNull private UUID userUuid;
  private String userName;
  private String information;
  private String password;
  private int totalNop;
}
