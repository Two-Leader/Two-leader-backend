package com.twoleader.backend.domain.chat.service.impl;

import com.twoleader.backend.domain.chat.document.Chat;
import com.twoleader.backend.domain.chat.dto.ChatMessage;
import com.twoleader.backend.domain.chat.dto.response.GetChatResponse;
import com.twoleader.backend.domain.chat.mapper.ChatMapper;
import com.twoleader.backend.domain.chat.repository.ChatRepository;
import com.twoleader.backend.domain.chat.service.ChatService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

  private final KafkaTemplate<String, ChatMessage> kafkaTemplate;
  private final ChatRepository chatRepository;
  private final ChatMapper chatMapper;

  public void saveChat(ChatMessage request) {
    log.info("[ws] sendMessage to {} message {}", request.getRoomUuid(), request);
    chatRepository.save(chatMapper.toEntity(request.getRoomUuid(), request));
  }

  public void sendChatMessage(String topic, ChatMessage chatMessage) {
    ListenableFuture<SendResult<String, ChatMessage>> future =
        kafkaTemplate.send(topic, chatMessage);
  }

  public Page<GetChatResponse> getChat(UUID roomUuid, Pageable pageable) {
    Page<Chat> chats = chatRepository.findAllByRoomUuid(roomUuid, pageable);
    return chatMapper.toDto(chats);
  }
}
