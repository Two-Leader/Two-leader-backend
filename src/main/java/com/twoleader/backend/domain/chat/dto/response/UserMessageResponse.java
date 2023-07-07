package com.twoleader.backend.domain.chat.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserMessageResponse {
  private UUID userUuid;
  private String userName;
}
