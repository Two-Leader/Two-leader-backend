package com.twoleader.backend.domain.webRTC.service;

import com.twoleader.backend.global.result.WebSocket.OutputMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public final class SendMessagingService {
  private final SimpMessagingTemplate simpMessagingTemplate;

  public <T> void sendToUser(String roomUuid, OutputMessage<T> message) {
    log.info("[ws] sendMessage to {} message {}", roomUuid, message);
    simpMessagingTemplate.convertAndSend("/topic/" + roomUuid, message);
  }
}
