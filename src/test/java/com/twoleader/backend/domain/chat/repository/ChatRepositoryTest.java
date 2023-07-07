package com.twoleader.backend.domain.chat.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.twoleader.backend.domain.chat.document.Chat;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataMongoTest
public class ChatRepositoryTest {
  @Autowired private ChatRepository chatRepository;
  private final UUID roomUuid1 = UUID.randomUUID();
  private final UUID roomUuid2 = UUID.randomUUID();

  @BeforeEach
  public void setUp() {
    for (int i = 0; i < 20; i++) {
      chatRepository.save(
          Chat.builder().roomUuid(roomUuid1).userId(1L).message("testMessage" + i).build());
      chatRepository.save(
          Chat.builder().roomUuid(roomUuid2).userId(2L).message("testMessage" + i).build());
    }
  }

  @Nested
  @DisplayName("Chat 찾기 테스트")
  class findChatsTest {
    @DisplayName("Chat 모두 찾기")
    @Test
    public void findAllTest() {
      List<Chat> chats = chatRepository.findAll();
      assertEquals(40, chats.size());
    }

    @DisplayName("같은 RoomUuid를 가진 Chat 모두 찾기")
    @Test
    public void findAllByRoomUuidTest() {
      List<Chat> chats = chatRepository.findAllByRoomUuid(roomUuid1);
      assertEquals(20, chats.size());
    }

    @DisplayName("같은 RoomUuid를 가진 Chat 모두 찾기, 페이지")
    @Test
    public void findAllByRoomUuidWithPaginationTest() {
      List<Chat> chats = chatRepository.findAllByRoomUuid(roomUuid1, PageRequest.of(0, 10));
      assertEquals("testMessage0", chats.get(0).getMessage());
      assertEquals(10, chats.size());
    }
  }
}
