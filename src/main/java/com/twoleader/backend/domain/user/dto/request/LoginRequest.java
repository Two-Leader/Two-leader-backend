package com.twoleader.backend.domain.user.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginRequest {
  @NotBlank private String email;

  @NotBlank private String password;
}
