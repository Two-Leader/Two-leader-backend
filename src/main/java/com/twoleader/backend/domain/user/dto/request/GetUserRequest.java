package com.twoleader.backend.domain.user.dto.request;

import java.util.UUID;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GetUserRequest {
  @NotBlank private UUID userUuid;
}
