package com.twoleader.backend.domain.webRTC.dto.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IceCandidateRequestMessage {
  private UUID from;
  private Object candidate;
}
