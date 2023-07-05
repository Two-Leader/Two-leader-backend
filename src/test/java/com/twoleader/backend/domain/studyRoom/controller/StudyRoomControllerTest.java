package com.twoleader.backend.domain.studyRoom.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import com.twoleader.backend.domain.studyRoom.dto.request.CheckStudyRoomPasswordRequest;
import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetAllStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.mapper.StudyRoomMapper;
import com.twoleader.backend.domain.studyRoom.service.StudyRoomService;
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
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
  @SpyBean private StudyRoomMapper studyRoomMapper;

  private static final List<GetRoomUserResponse> users = new ArrayList<>();

  @Test
  @DisplayName("StudyRoom 생성 Test")
  public void CreateStudyRoomTest() throws Exception {
    // given
    CreateStudyRoomRequest request =
        CreateStudyRoomRequest.builder()
            .userUuid(UUID.randomUUID())
            .roomName("testRoomName")
            .totalNop(5)
            .build();

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
  public void getAllStudyRoomTest() throws Exception {
    // given
    List<GetAllStudyRoomResponse> mockResponse = new ArrayList<>();
    mockResponse.add(
        GetAllStudyRoomResponse.builder()
            .roomUuid(UUID.randomUUID())
            .roomName("tesetStudyRoom1")
            .build());
    mockResponse.add(
        GetAllStudyRoomResponse.builder()
            .roomUuid(UUID.randomUUID())
            .roomName("tesetStudyRoom2")
            .build());

    Page<GetAllStudyRoomResponse> response = new PageImpl<>(mockResponse);
    given(studyRoomService.findAllStudyRoom(any())).willReturn(response);

    // when, then
    mockMvc
        .perform(
            get("/api/v1/studies").accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8"))
        .andExpect(status().isOk())
        //        .andExpect(jsonPath("$.data").isArray())
        //
        // .andExpect(jsonPath("$.data[0].roomUuid").value(response.get(0).getRoomUuid().toString()))
        //        .andExpect(jsonPath("$.data[0].roomName").value(response.get(0).getRoomName()))
        //        .andExpect(jsonPath("$._links.self").exists())
        .andDo(print());
  }

  @Test
  @DisplayName(" StudyRoom 개별 조회 Test")
  public void getStudyRoomByUuidTest() throws Exception {
    // given
    users.add(GetRoomUserResponse.builder().userId(1L).userName("tester").build());
    users.add(GetRoomUserResponse.builder().userId(2L).userName("tester2").build());
    GetStudyRoomResponse response =
        GetStudyRoomResponse.builder()
            .roomName("testStudyRoom1")
            .constructorName("tester")
            .users(users)
            .build();
    int index = 1;
    given(studyRoomService.findStudyRoomByUuid(any())).willReturn(response);

    // when, then
    mockMvc
        .perform(
            get("/api/v1/studies/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.roomName").value(response.getRoomName()))
        .andExpect(
            jsonPath("$.data.users[" + index + "].userId")
                .value(response.getUsers().get(index).getUserId().toString()))
        .andExpect(jsonPath("$._links.self").exists())
        .andDo(print());
  }

  @Test
  @DisplayName(" StudyRoom password 체크 Test")
  public void checkStudyRoomPasswordTest() throws Exception {
    // given
    CheckStudyRoomPasswordRequest request =
        CheckStudyRoomPasswordRequest.builder().password("testPassword").build();
    given(studyRoomService.checkStudyRoomPassword(any(), any())).willReturn(true);

    // when, then
    mockMvc
        .perform(
            get("/api/v1/studies/" + UUID.randomUUID() + "/checkpw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("utf-8"))
        .andExpect(status().isOk())
        .andDo(print());
  }
}
