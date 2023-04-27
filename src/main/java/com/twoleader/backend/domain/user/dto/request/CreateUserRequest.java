package com.twoleader.backend.domain.user.dto.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CreateUserRequest {
  private UUID room_uuid;
  private String user_name;
}
