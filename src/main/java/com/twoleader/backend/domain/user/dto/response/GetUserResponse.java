package com.twoleader.backend.domain.user.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetUserResponse {
  private UUID userUuid;
  private String userName;
}
