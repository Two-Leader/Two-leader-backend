package com.twoleader.backend.domain.chat.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.twoleader.backend.domain.chat.document.Chat;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
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
      Page<Chat> chats = chatRepository.findAllByRoomUuid(roomUuid1, PageRequest.of(0, 10));
      assertEquals(10, chats.getSize());
      assertTrue(chats.hasNext());
      assertEquals(2,chats.getTotalPages());
    }

    @DisplayName("페이지 초과시")
    @Test
    public void findAllByRoomUuidWithPaginationTestWhenOverPage(){
      Page<Chat> chats = chatRepository.findAllByRoomUuid(roomUuid1, PageRequest.of(2, 10));
      assertEquals(0,chats.get().count());
    }
  }
}
