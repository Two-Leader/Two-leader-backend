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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(profiles = {"test"})
public class StudyRoomServiceTest {
  @Mock private StudyRoomRepository studyRoomRepository;

  @InjectMocks private StudyRoomService studyRoomService;

  private List<StudyRoom> studyRooms = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    studyRooms.add(
        StudyRoom.builder()
            .roomId(1L)
            .roomUuid(UUID.randomUUID())
            .roomName("testStudyRoom1")
            .build());
    studyRooms.add(
        StudyRoom.builder()
            .roomId(2L)
            .roomUuid(UUID.randomUUID())
            .roomName("testStudyRoom2")
            .build());
  }

  @Test
  @DisplayName("StudyRoom 저장")
  public void createStudyRoomTest() {
    // given
    StudyRoom studyRoom = studyRooms.get(0);
    CreateStudyRoomRequest createStudyRoomDto =
        CreateStudyRoomRequest.builder().roomName(studyRoom.getRoomName()).build();
    given(studyRoomRepository.save(any())).willReturn(studyRoom);

    // when
    GetStudyRoomResponse response = studyRoomService.createStudyRoom(createStudyRoomDto);

    // then
    assertEquals(studyRoom.getRoomName(), response.getRoomName());
    assertEquals(studyRoom.getRoomUuid(), response.getRoomUuid());
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
    assertEquals(studyRooms.get(index).getRoomUuid(), findStudyRooms.get(index).getRoomUuid());
    assertEquals(studyRooms.get(index).getRoomName(), findStudyRooms.get(index).getRoomName());
  }
}
