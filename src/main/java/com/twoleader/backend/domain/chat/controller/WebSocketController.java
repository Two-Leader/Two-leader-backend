package com.twoleader.backend.domain.chat.controller;

import static com.twoleader.backend.global.result.WebSocket.OutputMessageCode.WEBSOCKET_SUCCESS_CHAT;

import com.twoleader.backend.domain.chat.dto.request.ChatRequest;
import com.twoleader.backend.domain.roomUser.service.RoomUserService;
import com.twoleader.backend.domain.chat.service.ChatService;
import com.twoleader.backend.global.result.WebSocket.OutputMessage;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@RequestMapping("/api/v1/chat")
@RestController
@RequiredArgsConstructor
public class WebSocketController {
  private final ChatService chatService;
  private final SimpMessagingTemplate simpMessagingTemplate;
  private final RoomUserService roomUserService;


  @MessageMapping("/join/{userId}")
  public void addUser(@DestinationVariable Long userId, SimpMessageHeaderAccessor headerAccessor) {
    Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("userId", userId);
    roomUserService.changeOnline(userId);
  }

  @MessageMapping("/chat/{roomUuid}")
  public void chatMessage(@DestinationVariable("roomUuid") UUID roomUuid,@Payload ChatRequest request) {
    chatService.saveChat(roomUuid, request);
    simpMessagingTemplate.convertAndSend("/topic/" + roomUuid, new OutputMessage<>(WEBSOCKET_SUCCESS_CHAT, request));
  }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
      StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
      long userId = Long.parseLong(Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userId").toString());
      roomUserService.changeOffline(userId);
    }
}
