package com.twoleader.backend.domain.studyRoom.controller;

import static com.twoleader.backend.global.result.ResultCode.STUDYROOM_REGISTRATION_SUCCESS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.service.StudyRoomService;
import com.twoleader.backend.global.result.ResultResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
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

  @Test
  @DisplayName("StudyRoom 생성 Test")
  public void CreateStudyRoomTest() throws Exception {
    // given
    CreateStudyRoomRequest studyRoomDto =
        CreateStudyRoomRequest.builder().room_name("testStudyRoom").build();
    EntityModel<ResultResponse<Object>> entity =
        EntityModel.of(
            new ResultResponse<>(STUDYROOM_REGISTRATION_SUCCESS),
            linkTo(methodOn(StudyRoomController.class).createStudyRoom(studyRoomDto))
                .withSelfRel());

    // when,then
    mockMvc
        .perform(
            post("/api/v1/study")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studyRoomDto))
                .characterEncoding("utf-8"))
        .andExpect(status().isCreated())
        //                .andExpect(content().string(objectMapper.writeValueAsString(entity)))
        .andDo(print());
  }
}
