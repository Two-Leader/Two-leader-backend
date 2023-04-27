package com.twoleader.backend.domain.user.dto.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class GetUserRequest {
  private UUID room_uuid;
  private UUID user_uuid;
}
