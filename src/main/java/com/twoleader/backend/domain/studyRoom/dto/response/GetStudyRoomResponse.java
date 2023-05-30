package com.twoleader.backend.domain.studyRoom.dto.response;

import com.twoleader.backend.domain.user.dto.response.GetUserResponse;
import java.util.List;
import java.util.UUID;
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
