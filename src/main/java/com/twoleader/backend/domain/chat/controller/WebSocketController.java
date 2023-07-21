package com.twoleader.backend.domain.chat.controller;

import static com.twoleader.backend.global.result.WebSocket.OutputMessageCode.WEBSOCKET_SUCCESS_CHAT;
import static com.twoleader.backend.global.result.api.ResultCode.API_SUCCESS_CHAT_GET_ALL;

import com.twoleader.backend.domain.chat.dto.ChatMessage;
import com.twoleader.backend.domain.chat.dto.response.GetChatResponse;
import com.twoleader.backend.domain.chat.mapper.ChatMapper;
import com.twoleader.backend.domain.chat.service.ChatService;
import com.twoleader.backend.domain.roomUser.service.RoomUserService;
import com.twoleader.backend.global.result.WebSocket.OutputMessage;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
  private final ChatMapper chatMapper;

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
  public void listen(@Payload ChatMessage request) {
    // WebSocket을 통해 브로드캐스트하기\
    chatService.saveChat(request);
    simpMessagingTemplate.convertAndSend(
        "/topic/" + request.getRoomUuid(), new OutputMessage<>(WEBSOCKET_SUCCESS_CHAT, request));
  }

  @GetMapping
  public ResponseEntity getChat(
      @RequestParam("roomUuid") UUID roomUuid,
      @RequestParam(value = "page", defaultValue = "0") int page) {
    Page<GetChatResponse> response = chatService.getChat(roomUuid, PageRequest.of(page, 20));
    return ResponseEntity.ok(
        chatMapper.toModel(API_SUCCESS_CHAT_GET_ALL, response, roomUuid, page));
  }

  //  @EventListener
  //  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
  //    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
  //    long userId =
  // Long.parseLong(Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userId").toString());
  //    roomUserService.changeOffline(userId);
  //  }
}
