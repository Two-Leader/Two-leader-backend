package com.twoleader.backend.domain.chat.service;

import com.twoleader.backend.domain.chat.document.Chat;
import com.twoleader.backend.domain.chat.dto.request.ChatRequest;
import com.twoleader.backend.global.result.WebSocket.OutputMessage;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface ChatService {
    void saveChat(UUID roomUuid, ChatRequest request);
}
