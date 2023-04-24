package com.twoleader.backend.domain.studyRoom.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetStudyRoomResponse {
  private UUID room_uuid;
  private String room_name;
}
