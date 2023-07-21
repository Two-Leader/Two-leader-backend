package com.twoleader.backend.domain.chat.service;

import com.twoleader.backend.domain.chat.dto.ChatMessage;
import com.twoleader.backend.domain.chat.dto.response.GetChatResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ChatService {
  void saveChat(ChatMessage request);

  void sendChatMessage(String topic, ChatMessage chatMessage);

  public Page<GetChatResponse> getChat(UUID roomUuid, Pageable pageable);
}
