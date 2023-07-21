package com.twoleader.backend.domain.chat.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.twoleader.backend.domain.chat.document.Chat;
import com.twoleader.backend.domain.chat.dto.response.GetChatResponse;
import com.twoleader.backend.domain.chat.mapper.ChatMapper;
import com.twoleader.backend.domain.chat.repository.ChatRepository;
import com.twoleader.backend.domain.chat.service.impl.ChatServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(profiles = {"test"})
public class ChatServiceTest {

  @InjectMocks private ChatServiceImpl chatService;
  @Mock private ChatRepository chatRepository;
  @Spy private ChatMapper chatMapper;

  private final UUID roomUuid1 = UUID.randomUUID();

  @Nested
  @DisplayName("chat 가져오기 Test")
  class getChatTest {
    @Test
    @DisplayName("성공 시")
    public void whenSuccess() {
      List<Chat> chatList = new ArrayList<>();
      for (int i = 0; i < 20; i++) {
        chatList.add(
            Chat.builder()
                .id(String.valueOf(i))
                .userId((long) i)
                .roomUuid(roomUuid1)
                .message("testMessage" + i)
                .createdAt(LocalDateTime.now())
                .build());
      }
      //            Page<Chat> chats = ;
      given(chatRepository.findAllByRoomUuid(any(), any())).willReturn(new PageImpl<>(chatList));

      Page<GetChatResponse> responses =
          chatService.getChat(UUID.randomUUID(), PageRequest.of(0, 20));
      assertNotNull(responses.getContent().get(0));
      assertEquals(20, responses.getSize());
    }
  }
}
