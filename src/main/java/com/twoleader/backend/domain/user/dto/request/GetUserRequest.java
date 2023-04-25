package com.twoleader.backend.domain.user.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Builder
public class GetUserRequest {
  @NotBlank
  private UUID user_uuid;
}
