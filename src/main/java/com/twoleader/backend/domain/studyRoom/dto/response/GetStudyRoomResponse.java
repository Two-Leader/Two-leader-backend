package com.twoleader.backend.domain.studyRoom.dto.response;

import java.util.List;
import java.util.UUID;

import com.twoleader.backend.domain.user.dto.response.GetUserResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetStudyRoomResponse {
  private UUID roomUuid;
  private String roomName;
  private Boolean checkUser;
  private List<GetUserResponse> users;
}
