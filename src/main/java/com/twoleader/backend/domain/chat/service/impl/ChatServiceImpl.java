package com.twoleader.backend.domain.chat.service.impl;

import com.twoleader.backend.domain.chat.dto.ChatMessage;
import com.twoleader.backend.domain.chat.mapper.ChatMapper;
import com.twoleader.backend.domain.chat.repository.ChatRepository;
import com.twoleader.backend.domain.chat.service.ChatService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

  private final KafkaTemplate<String, ChatMessage> kafkaTemplate;
  private final ChatRepository chatRepository;
  private final ChatMapper chatMapper;

  public void saveChat( ChatMessage request) {
    log.info("[ws] sendMessage to {} message {}", request.getRoomUuid(), request);
    chatRepository.save(chatMapper.toEntity(request.getRoomUuid(), request));
  }

  public void sendChatMessage(String topic, ChatMessage chatMessage) {
    ListenableFuture<SendResult<String, ChatMessage>> future = kafkaTemplate.send(topic,chatMessage);
    future.addCallback(new ListenableFutureCallback<SendResult<String, ChatMessage>>() {
      @Override
      public void onFailure(Throwable ex) {
        log.error("메세지 전송 실패={}", ex.getMessage());
      }
      @Override
      public void onSuccess(SendResult<String, ChatMessage> result) {
        log.info("메세지 전송 성공 topic={}, offset={}, partition={}",topic, result.getRecordMetadata().offset(), result.getRecordMetadata().partition());
      }
    });
  }
}
