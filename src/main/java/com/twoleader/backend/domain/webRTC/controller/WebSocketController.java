package com.twoleader.backend.domain.webRTC.controller;

import com.twoleader.backend.domain.webRTC.dto.WebSocketMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketController {
  private final SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping("/{roomUuid}/webRTC")
  public void offerMessage(
      @Payload WebSocketMessage message, @DestinationVariable("roomUuid") String roomUuid) {
    log.debug("[ws] offerMessage : messageType {}, roomUuid {}", message.getType(), roomUuid);
    simpMessagingTemplate.convertAndSend("/topic/" + roomUuid, message);
  }
}
