package com.twoleader.backend.domain.user.dto.request;

import com.twoleader.backend.domain.user.entity.User;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CreateUserRequest {
  @NotBlank private String userName;


}
