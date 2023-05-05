package com.twoleader.backend.domain.user.dto.request;

import com.twoleader.backend.domain.user.entity.User;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserRequest {
  @NotBlank private String userName;


}
