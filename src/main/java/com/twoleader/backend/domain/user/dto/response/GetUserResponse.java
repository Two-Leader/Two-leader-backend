package com.twoleader.backend.domain.user.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetUserResponse {
  private UUID user_uuid;
  private String user_name;
}
