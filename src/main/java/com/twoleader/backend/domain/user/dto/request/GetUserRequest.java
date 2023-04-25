package com.twoleader.backend.domain.user.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class GetUserRequest {
  private UUID user_uuid;
}
