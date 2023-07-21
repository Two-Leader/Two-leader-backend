package com.twoleader.backend.domain.chat.repository;

import com.twoleader.backend.domain.chat.document.Chat;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String> {
  Page<Chat> findAllByRoomUuid(UUID rooUuid, Pageable pageable);

  List<Chat> findAllByRoomUuid(UUID rooUuid);
}
