package com.twoleader.backend.domain.chat.mapper;

import com.twoleader.backend.domain.chat.document.Chat;
import com.twoleader.backend.domain.chat.dto.request.ChatRequest;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {
  public Chat toEntity(UUID roomUuid, ChatRequest request) {
    return Chat.builder()
        .roomUuid(roomUuid)
        .userId(request.getUserId())
        .message(request.getMessage())
        .build();
  }
}
