package com.twoleader.backend.domain.studyRoom.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetStudyRoomResponse {
  private UUID roomUuid;
  private String roomName;
  private Boolean checkUser;
}
