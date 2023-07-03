package com.twoleader.backend.domain.user.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateUserRequest {
  @NotBlank private String email;

  @NotBlank private String password;

  @NotBlank private String nickName;
}
