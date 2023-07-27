package com.twoleader.backend.domain.chat.service;

import com.twoleader.backend.domain.chat.dto.response.ChatMessage;
import com.twoleader.backend.domain.chat.dto.response.GetChatResponse;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatService {
  void saveChat(ChatMessage request);

  void sendChatMessage(String topic, ChatMessage chatMessage);

  public Page<GetChatResponse> getChat(UUID roomUuid, Pageable pageable);
}
