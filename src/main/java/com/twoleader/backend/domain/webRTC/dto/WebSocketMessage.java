package com.twoleader.backend.domain.webRTC.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class WebSocketMessage {
  private UUID from;
  private String type;
  private UUID data;
  private Object candidate;
  private Object sdp;
}
