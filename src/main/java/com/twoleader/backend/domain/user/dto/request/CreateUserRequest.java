package com.twoleader.backend.domain.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class CreateUserRequest {
  private String user_name;
}
