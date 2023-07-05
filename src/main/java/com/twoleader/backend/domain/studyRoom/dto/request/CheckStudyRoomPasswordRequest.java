package com.twoleader.backend.domain.studyRoom.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckStudyRoomPasswordRequest {
  @NotBlank private String password;
}
