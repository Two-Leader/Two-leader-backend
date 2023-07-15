package com.twoleader.backend.domain.chat.mapper;

import com.twoleader.backend.domain.chat.document.Chat;
import com.twoleader.backend.domain.chat.dto.ChatMessage;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {
  public Chat toEntity(UUID roomUuid, ChatMessage request) {
    return Chat.builder()
        .roomUuid(roomUuid)
        .userId(request.getUserId())
        .message(request.getMessage())
        .build();
  }
}
