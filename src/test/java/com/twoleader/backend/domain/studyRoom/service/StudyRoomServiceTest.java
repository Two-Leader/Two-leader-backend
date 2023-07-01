package com.twoleader.backend.domain.studyRoom.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.roomUser.repository.RoomUserRepository;
import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetAllStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.exception.NotFoundStudyRoom;
import com.twoleader.backend.domain.studyRoom.mapper.StudyRoomMapper;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(profiles = {"test"})
public class StudyRoomServiceTest {
  @Mock private StudyRoomRepository studyRoomRepository;

  @Mock private UserService userService;

  @InjectMocks private StudyRoomService studyRoomService;

  @Spy private StudyRoomMapper studyRoomMapper;

  private List<StudyRoom> studyRooms = new ArrayList<>();
  private List<RoomUser> roomUsers = new ArrayList<>();
  private List<User> users = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    users.add(
        User.builder()
            .userId(1L)
            .email("testEmail")
            .password("testPassword")
            .nickName("tester")
            .build());

    studyRooms.add(
        StudyRoom.builder()
            .studyRoomId(1L)
            .roomUuid(UUID.randomUUID())
            .roomName("testStudyRoom1")
            .password("testPassword")
            .information("testInformation")
            .totalNop(5)
            .constructor(users.get(0))
            .roomUsers(roomUsers)
            .build());

    studyRooms.add(
        StudyRoom.builder()
            .studyRoomId(2L)
            .roomUuid(UUID.randomUUID())
            .roomName("testStudyRoom2")
            .totalNop(5)
            .constructor(users.get(0))
            .build());

    roomUsers.add(
        RoomUser.builder()
            .roomUserId(1L)
            .user(users.get(0))
            .roomUserName("tester")
            .studyRoom(studyRooms.get(0))
            .build());
  }

  @Test
  @DisplayName("StudyRoom 모두 조회")
  public void findAllStudyRoomTest() {
    // given
    given(studyRoomRepository.findAll()).willReturn(studyRooms);
    int index = 0;

    // when
    List<GetAllStudyRoomResponse> findStudyRooms = studyRoomService.findAllStudyRoom();

    // then
    assertEquals(studyRooms.size(), findStudyRooms.size());
    assertEquals(studyRooms.get(index).getRoomUuid(), findStudyRooms.get(index).getRoomUuid());
    assertEquals(studyRooms.get(index).getRoomName(), findStudyRooms.get(index).getRoomName());
    assertTrue(findStudyRooms.get(0).isLocked());
    assertFalse(findStudyRooms.get(1).isLocked());
  }

  @Nested
  @DisplayName("studyRoom UUID로 Study방 찾기")
  class findStudyRoomByUuid {
    @Test
    @DisplayName("존재하는 StudyRoom")
    public void whenExist() {
      // given
      int index = 0;
      StudyRoom studyRoom = studyRooms.get(index);

      given(studyRoomRepository.findWithRoomUsersByRoomUuid(any()))
          .willReturn(Optional.ofNullable(studyRoom));

      // when
      assert studyRoom != null;
      GetStudyRoomResponse response = studyRoomService.findStudyRoomByUuid(studyRoom.getRoomUuid());

      // then
      assertEquals(studyRoom.getRoomName(), response.getRoomName());
      assertEquals(1, response.getUsers().size());
    }

    @Test
    @DisplayName("존재하지 않는 StudyRoom")
    public void whenNotExist() {
      // given
      given(studyRoomRepository.findWithRoomUsersByRoomUuid(any())).willReturn(Optional.empty());

      // when, then
      assertThrows(
          NotFoundStudyRoom.class,
          () -> {
            studyRoomService.findStudyRoomByUuid(UUID.randomUUID());
          });
    }
  }

  @Nested
  @DisplayName("studyRoom Password 체크")
  class checkPassword{
    @Test
    @DisplayName("password가 맞을 때")
    public void whenPasswordCorrect() {
      // given
      given(studyRoomRepository.findByRoomUuid(any())).willReturn(Optional.of(studyRooms.get(0)));

      // when
      boolean response = studyRoomService.checkStudyRoomPassword(studyRooms.get(0).getRoomUuid(),studyRooms.get(0).getPassword());

      // then
      assertTrue(response);
    }

    @Test
    @DisplayName("password가 틀릴 때")
    public void whenPasswordInCorrect() {
      // given
      given(studyRoomRepository.findByRoomUuid(any())).willReturn(Optional.of(studyRooms.get(0)));

      // when
      boolean response = studyRoomService.checkStudyRoomPassword(studyRooms.get(0).getRoomUuid(),"inCorrectPassword");

      // then
      assertFalse(response);
    }

    @Test
    @DisplayName("password가 없을 때")
    public void whenPasswordNotExist() {
      // given
      given(studyRoomRepository.findByRoomUuid(any())).willReturn(Optional.of(studyRooms.get(1)));

      // when, then
      assertThrows((NullPointerException.class),()->{
        studyRoomService.checkStudyRoomPassword(studyRooms.get(1).getRoomUuid(),"");
      });
    }
  }
}
