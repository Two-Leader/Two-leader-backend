package com.twoleader.backend.domain.studyRoom.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudyRoomRequest {

  @NotBlank private String room_name;
}
