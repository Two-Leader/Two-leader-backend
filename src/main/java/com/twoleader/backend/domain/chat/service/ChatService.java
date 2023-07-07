package com.twoleader.backend.domain.chat.service;

import com.twoleader.backend.domain.chat.dto.request.ChatRequest;
import java.util.UUID;

public interface ChatService {
  void saveChat(UUID roomUuid, ChatRequest request);
}
