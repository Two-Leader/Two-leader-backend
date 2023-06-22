package com.twoleader.backend.domain.roomUser.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetRoomUserResponse {
  private UUID userUuid;
  private String userName;
}
