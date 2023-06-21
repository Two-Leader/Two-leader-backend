package com.twoleader.backend.domain.webRTC.dto.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class IceCandidateResponseMessage {
  private UUID userUuid;
  private String sdp;
  private String sdpMid;
  private int sdpMLineIndex;
}
