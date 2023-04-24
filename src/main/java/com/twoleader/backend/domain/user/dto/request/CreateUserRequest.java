package com.twoleader.backend.domain.user.dto.request;

import com.twoleader.backend.domain.user.entity.User;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserRequest {
  @NotBlank private String user_name;

  public User toEntity() {
    return User.builder().user_name(this.user_name).build();
  }
}
