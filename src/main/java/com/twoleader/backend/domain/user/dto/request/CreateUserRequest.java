package com.twoleader.backend.domain.user.dto.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
@Builder
public class CreateUserRequest {
  private String user_name;
}
