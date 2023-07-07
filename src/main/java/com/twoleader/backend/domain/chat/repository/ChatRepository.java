package com.twoleader.backend.domain.chat.repository;

import com.twoleader.backend.domain.chat.document.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findAllByRoomUuid(UUID rooUuid, Pageable pageable);
    List<Chat> findAllByRoomUuid(UUID rooUuid);
}

