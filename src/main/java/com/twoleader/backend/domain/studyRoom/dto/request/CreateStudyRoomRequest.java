package com.twoleader.backend.domain.studyRoom.dto.request;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class CreateStudyRoomRequest {

  @NotBlank private String roomName;


}
