package com.twoleader.backend.domain.webRTC.service;

import com.twoleader.backend.global.result.webSocket.OutputMessage;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public final class StompMessagingService {

  private final SimpMessagingTemplate simpMessagingTemplate;

  public <T> void sendToUser(UUID userUuid, OutputMessage<T> message) {
    log.info("[ws] sendMessage to {} message {}", userUuid.toString(), message);
    simpMessagingTemplate.convertAndSend("/topic/" + userUuid, message);
  }
}
