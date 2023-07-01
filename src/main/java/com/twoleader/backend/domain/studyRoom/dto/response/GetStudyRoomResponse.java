package com.twoleader.backend.domain.studyRoom.dto.response;

import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetStudyRoomResponse {
  private String roomName;
  private String constructorName;
  private List<GetRoomUserResponse> users;
}
