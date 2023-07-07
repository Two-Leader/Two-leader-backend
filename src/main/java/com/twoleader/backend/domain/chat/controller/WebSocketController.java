package com.twoleader.backend.domain.chat.controller;

import static com.twoleader.backend.global.result.WebSocket.OutputMessageCode.WEBSOCKET_SUCCESS_CHAT;

import com.twoleader.backend.domain.chat.dto.request.ChatRequest;
import com.twoleader.backend.domain.chat.service.ChatService;
import com.twoleader.backend.global.result.WebSocket.OutputMessage;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1/chat")
@RestController
@RequiredArgsConstructor
public class WebSocketController {
  private final ChatService chatService;
  private final SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping("/join/{userId}")
  public void addUser(@DestinationVariable Long userId, SimpMessageHeaderAccessor headerAccessor) {
    log.info("[ws] join User : {}", userId);
    // Add username in web socket session
    Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("userId", userId);
  }

  @MessageMapping("/chat/{roomUuid}")
  public void chatMessage(
      @DestinationVariable("roomUuid") UUID roomUuid, @Payload ChatRequest request) {
    log.info(
        "[ws] chatMessage : roomUuid {}, sender {}, message {}",
        roomUuid,
        request.getUserId(),
        request.getMessage());
    chatService.saveChat(roomUuid, request);
    simpMessagingTemplate.convertAndSend(
        "/topic/" + roomUuid, new OutputMessage<>(WEBSOCKET_SUCCESS_CHAT, request));
  }

  //  @EventListener
  //  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
  //    StompHeaderAccessor headerAccesor = StompHeaderAccessor.wrap(event.getMessage());
  //    int userId =
  // Integer.parseInt(headerAccesor.getSessionAttributes().get("userId").toString());
  //    log.info("[ws] sessionId Disconnected : {}", userId);
  //    roomUserService.deleteUserById(userId);
  //  }
}
