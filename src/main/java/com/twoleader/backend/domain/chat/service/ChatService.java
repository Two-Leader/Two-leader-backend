package com.twoleader.backend.domain.chat.service;

import com.twoleader.backend.domain.chat.dto.ChatMessage;

public interface ChatService {
  void saveChat(ChatMessage request);

  void sendChatMessage(String topic, ChatMessage chatMessage);
}
