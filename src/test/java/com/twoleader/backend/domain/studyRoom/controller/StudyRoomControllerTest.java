package com.twoleader.backend.domain.studyRoom.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.service.StudyRoomService;
import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import java.util.ArrayList;
import java.util.List;
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

@WebMvcTest(StudyRoomController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = {"test"})
public class StudyRoomControllerTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private StudyRoomService studyRoomService;

  private static final List<GetRoomUserResponse> users = new ArrayList<>();

  private static final GetStudyRoomResponse response =
      GetStudyRoomResponse.builder()
          .roomUuid(UUID.randomUUID())
          .roomName("testStudyRoom1")
          .checkUser(false)
          .users(users)
          .build();

  @Test
  @DisplayName("StudyRoom 생성 Test")
  public void CreateStudyRoomTest() throws Exception {
    // given
    CreateStudyRoomRequest request =
        CreateStudyRoomRequest.builder().roomName(response.getRoomName()).build();

    given(studyRoomService.createStudyRoom(any())).willReturn(response);

    // when,then
    mockMvc
        .perform(
            post("/api/v1/studies")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("utf-8"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$._links.self").exists())
        .andDo(print());
  }

  @Test
  @DisplayName("StudyRoom 모두 조회 Test")
  public void getAllStudyRoom() throws Exception {
    // given
    List<GetStudyRoomResponse> response = new ArrayList<>();
    response.add(
        GetStudyRoomResponse.builder()
            .roomUuid(UUID.randomUUID())
            .roomName("tesetStudyRoom1")
            .build());
    response.add(
        GetStudyRoomResponse.builder()
            .roomUuid(UUID.randomUUID())
            .roomName("tesetStudyRoom2")
            .build());

    given(studyRoomService.findAllStudyRoom()).willReturn(response);

    // when, then
    mockMvc
        .perform(
            get("/api/v1/studies").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(jsonPath("$.data[0].roomUuid").value(response.get(0).getRoomUuid().toString()))
        .andExpect(jsonPath("$.data[0].roomName").value(response.get(0).getRoomName()))
        .andExpect(jsonPath("$._links.self").exists())
        .andDo(print());
  }

  @Test
  @DisplayName(" StudyRoom 개별 조회")
  public void getStudyRoomByUuid() throws Exception {
    // given
    int index = 1;
    given(studyRoomService.findStudyRoomByUuid(any())).willReturn(response);
    users.add(GetRoomUserResponse.builder().userUuid(UUID.randomUUID()).userName("tester").build());
    users.add(GetRoomUserResponse.builder().userUuid(UUID.randomUUID()).userName("tester2").build());

    // when, then
    mockMvc
        .perform(
            get("/api/v1/studies/" + response.getRoomUuid().toString())
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.roomUuid").value(response.getRoomUuid().toString()))
        .andExpect(jsonPath("$.data.roomName").value(response.getRoomName()))
        .andExpect(jsonPath("$.data.checkUser").value(response.getCheckUser()))
        .andExpect(
            jsonPath("$.data.users[" + index + "].userUuid")
                .value(response.getUsers().get(index).getUserUuid().toString()))
        .andExpect(jsonPath("$._links.self").exists())
        .andDo(print());
  }
}
