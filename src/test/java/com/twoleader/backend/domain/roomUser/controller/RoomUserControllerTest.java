package com.twoleader.backend.domain.roomUser.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twoleader.backend.domain.roomUser.dto.request.CreateRoomUserRequest;
import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import com.twoleader.backend.domain.roomUser.service.RoomUserService;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RoomUserController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = {"test"})
public class RoomUserControllerTest {
  @Autowired private MockMvc mockMvc;
  @MockBean private RoomUserService roomUserService;
  @Autowired private ObjectMapper objectMapper;

  private static final GetRoomUserResponse response =
      GetRoomUserResponse.builder().userId(1L).userName("tester").build();

  @Test
  @DisplayName("RoomUser 생성 Test")
  public void CreateRoomUserTest() throws Exception {
    // given
    CreateRoomUserRequest request =
        CreateRoomUserRequest.builder().userUuid(UUID.randomUUID()).userName("tester").build();
    given(roomUserService.createUser(any(), any())).willReturn(response);

    // when,then
    mockMvc
        .perform(
            post("/api/v1/room-users/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("utf-8"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$._links.self").exists())
        .andDo(print());
  }
}
