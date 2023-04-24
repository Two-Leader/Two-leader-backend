package com.twoleader.backend.domain.studyRoom.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles(profiles = {"test"})
public class StudyRoomServiceTest {
  @MockBean private StudyRoomRepository studyRoomRepository;

  @Autowired private StudyRoomService studyRoomService;

  private List<StudyRoom> studyRooms = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    studyRooms.add(
        StudyRoom.builder()
            .room_id(1L)
            .room_uuid(UUID.randomUUID())
            .room_name("testStudyRoom1")
            .build());
    studyRooms.add(
        StudyRoom.builder()
            .room_id(2L)
            .room_uuid(UUID.randomUUID())
            .room_name("testStudyRoom2")
            .build());
  }

  @Test
  @DisplayName("StudyRoom 저장")
  public void createStudyRoomTest() {
    // given
    StudyRoom studyRoom = studyRooms.get(0);
    CreateStudyRoomRequest createStudyRoomDto =
        CreateStudyRoomRequest.builder().room_name(studyRoom.getRoom_name()).build();
    given(studyRoomRepository.save(any())).willReturn(studyRoom);

    // when
    StudyRoom savedStudyRoom = studyRoomService.createStudyRoom(createStudyRoomDto);

    // then
    assertEquals(studyRoom.getRoom_name(), savedStudyRoom.getRoom_name());
    assertEquals(studyRoom.getRoom_uuid(), savedStudyRoom.getRoom_uuid());
  }

  @Test
  @DisplayName("StudyRoom 모두 조회")
  public void findAllStudyRoomTest() {
    // given
    given(studyRoomRepository.findAll()).willReturn(studyRooms);
    int index = 0;

    // when
    List<GetStudyRoomResponse> findStudyRooms = studyRoomService.findAllStudyRoom();

    // then
    assertEquals(studyRooms.size(), findStudyRooms.size());
    assertEquals(studyRooms.get(index).getRoom_uuid(), findStudyRooms.get(index).getRoom_uuid());
    assertEquals(studyRooms.get(index).getRoom_name(), findStudyRooms.get(index).getRoom_name());
  }
}
