package com.twoleader.backend.domain.chat.controll;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twoleader.backend.domain.chat.controller.WebSocketController;
import com.twoleader.backend.domain.chat.dto.response.GetChatResponse;
import com.twoleader.backend.domain.chat.mapper.ChatMapper;
import com.twoleader.backend.domain.chat.service.ChatService;
import com.twoleader.backend.domain.roomUser.service.RoomUserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@WebMvcTest(WebSocketController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = {"test"})
public class ChatControllerTest {
  @Autowired private MockMvc mockMvc;
  @MockBean private ChatService chatService;
  @SpyBean ChatMapper chatMapper;
  @MockBean SimpMessagingTemplate simpMessagingTemplate;
  @MockBean private RoomUserService roomUserService;
  @Autowired private ObjectMapper objectMapper;

  @Nested
  @DisplayName("Chat 이력 가져오기 Test")
  public class getChatTest {
    @Test
    @DisplayName("다음 페이지가 존재할 때")
    public void whenHasNextPage() throws Exception {
      // given
      List<GetChatResponse> chatList = new ArrayList<>();
      for (int i = 0; i < 20; i++) {
        chatList.add(
            GetChatResponse.builder()
                .userId(1L)
                .message("testMessage" + i)
                .time(LocalDateTime.now())
                .build());
      }
      given(chatService.getChat(any(), any()))
          .willReturn(new PageImpl<>(chatList, PageRequest.of(0, 20), 40));
      MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
      request.add("roomUuid", UUID.randomUUID().toString());
      request.add("page", "0");

      // when,then
      mockMvc
          .perform(
              get("/api/v1/chat")
                  .contentType(MediaType.APPLICATION_JSON)
                  .params(request)
                  .accept(MediaType.APPLICATION_JSON)
                  .characterEncoding("utf-8"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$._links.self").exists())
          .andExpect(jsonPath("$._links.next").exists())
          .andDo(print());
    }

    @Test
    @DisplayName("다음 페이지가 존재하지 않을 때")
    public void whenHasNotNextPage() throws Exception {
      // given
      List<GetChatResponse> chatList = new ArrayList<>();
      for (int i = 0; i < 20; i++) {
        chatList.add(
            GetChatResponse.builder()
                .userId(1L)
                .message("testMessage" + i)
                .time(LocalDateTime.now())
                .build());
      }
      given(chatService.getChat(any(), any())).willReturn(new PageImpl<>(chatList));
      MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
      request.add("roomUuid", UUID.randomUUID().toString());
      request.add("page", "0");

      // when,then
      mockMvc
          .perform(
              get("/api/v1/chat")
                  .contentType(MediaType.APPLICATION_JSON)
                  .params(request)
                  .accept(MediaType.APPLICATION_JSON)
                  .characterEncoding("utf-8"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$._links.self").exists())
          .andExpect(jsonPath("$._links.next").doesNotExist())
          .andDo(print());
    }
  }
}
