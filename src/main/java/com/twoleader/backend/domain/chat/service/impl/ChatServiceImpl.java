package com.twoleader.backend.domain.chat.service.impl;

import com.twoleader.backend.domain.chat.dto.request.ChatRequest;
import com.twoleader.backend.domain.chat.mapper.ChatMapper;
import com.twoleader.backend.domain.chat.repository.ChatRepository;
import com.twoleader.backend.domain.chat.service.ChatService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

  private final ChatRepository chatRepository;
  private final ChatMapper chatMapper;

  public void saveChat(UUID roomUuid, ChatRequest request) {
    log.info("[ws] sendMessage to {} message {}", roomUuid, request);
    chatRepository.save(chatMapper.toEntity(roomUuid, request));
  }
}
