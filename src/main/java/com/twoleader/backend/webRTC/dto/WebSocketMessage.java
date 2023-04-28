package com.twoleader.backend.webRTC.dto;

import java.util.UUID;
import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessage {
  private UUID from;
  private String type;
  private UUID data;
  private Object candidate;
  private Object sdp;
}
