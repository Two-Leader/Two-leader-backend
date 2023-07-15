package com.twoleader.backend.domain.chat.controller;

import static com.twoleader.backend.global.result.WebSocket.OutputMessageCode.WEBSOCKET_SUCCESS_CHAT;

import com.twoleader.backend.domain.chat.dto.ChatMessage;
import com.twoleader.backend.domain.chat.service.ChatService;
import com.twoleader.backend.domain.roomUser.service.RoomUserService;
import com.twoleader.backend.global.result.WebSocket.OutputMessage;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/v1/chat")
@RestController
@RequiredArgsConstructor
public class WebSocketController {
  private final ChatService chatService;
  private final SimpMessagingTemplate simpMessagingTemplate;
  private final RoomUserService roomUserService;

  @MessageMapping("/join/{userId}")
  public void joinUser(@DestinationVariable Long userId, SimpMessageHeaderAccessor headerAccessor) {
    Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("userId", userId);
    roomUserService.changeOnline(userId);
  }

  @MessageMapping("/chat")
  public void receiveChatMessage(@RequestBody ChatMessage request) {
    chatService.sendChatMessage("chatTopic", request);
  }

  @KafkaListener(topics = "chatTopic", groupId = "chat-group")
  public void listen(ChatMessage request) {
    // WebSocket을 통해 브로드캐스트하기
    chatService.saveChat(request);
    simpMessagingTemplate.convertAndSend(
        "/topic/" + request.getRoomUuid(), new OutputMessage<>(WEBSOCKET_SUCCESS_CHAT, request));
  }

  //  @EventListener
  //  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
  //    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
  //    long userId =
  // Long.parseLong(Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userId").toString());
  //    roomUserService.changeOffline(userId);
  //  }
}
