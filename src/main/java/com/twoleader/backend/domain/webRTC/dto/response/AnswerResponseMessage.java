package com.twoleader.backend.domain.webRTC.dto.response;

import java.util.UUID;
import lombok.Builder;

@Builder
public class AnswerResponseMessage {
  private UUID userUuid;
  private String sdp;
}
