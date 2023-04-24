package com.twoleader.backend.domain.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetUserRequest {
  private String user_name;
}
