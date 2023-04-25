package com.twoleader.backend.domain.user.dto.request;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetUserRequest {
  @NotBlank private UUID user_uuid;
}
