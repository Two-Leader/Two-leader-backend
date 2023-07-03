package com.twoleader.backend.domain.roomUser.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.twoleader.backend.domain.roomUser.dto.request.CreateRoomUserRequest;
import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.roomUser.exception.FullOfPersonnelException;
import com.twoleader.backend.domain.roomUser.exception.NotFoundRoomUserException;
import com.twoleader.backend.domain.roomUser.mapper.RoomUserMapper;
import com.twoleader.backend.domain.roomUser.repository.RoomUserRepository;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeAll;
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
public class RoomUserServiceTest {
  @Mock private RoomUserRepository roomUserRepository;
  @Mock private UserService userService;
  @Mock private StudyRoomRepository studyRoomRepository;
  @Spy private RoomUserMapper roomUserMapper;
  @InjectMocks private RoomUserService roomUserService;

  private static RoomUser roomUser;
  private static StudyRoom studyRoom;
  private static User user;

  @BeforeAll
  public static void setUp() {
    user = User.builder().email("tester@gmail.com").password("testPassword").nickName("tester").build();

    studyRoom =
        StudyRoom.builder()
            .studyRoomId(1L)
            .roomName("testStudyRoom")
            .constructor(user)
            .totalNop(5)
            .nowTotalNop(4)
            .roomUsers(new ArrayList<>())
            .build();

    roomUser =
        RoomUser.builder().roomUserId(1L).roomUserName("testUser").user(user).studyRoom(studyRoom).build();
  }

  @Nested
  @DisplayName("Id로 User찾기")
  class getUserByUserUuid {
    @Test
    @DisplayName("User 존재")
    public void getUserByUserIdTestwhenUserExist() {
      // given
      given(roomUserRepository.findById(any())).willReturn(Optional.ofNullable(roomUser));

      // when
      GetRoomUserResponse response = roomUserService.getUser(1L);

      // then
      assertEquals(roomUser.getRoomUserId(), response.getUserId());
      assertEquals(roomUser.getRoomUserName(), response.getUserName());
    }

    @Test
    @DisplayName("존재하지 않는 User")
    public void getUserByUserUuidTestWhenUserNotExist() {
      // given
      given(roomUserRepository.findById(any())).willReturn(Optional.empty());

      // when,then
      assertThrows(
          NotFoundRoomUserException.class,
          () -> {
            roomUserService.getUser(1L);
          });
    }
  }

  @Nested
  @DisplayName("roomUser 생성 Test")
  class createRoomUserTest{
    @Test
    @DisplayName("인원이 다 찾을때")
    public void whenFullOfPersonnel(){
      //given
      User user1 = User.builder().email("tester1@gmail.com").password("testPassword").nickName("tester1").build();
      RoomUser roomUser1 = RoomUser.builder().studyRoom(studyRoom).roomUserName("tester1").user(user1).build();
      CreateRoomUserRequest request = CreateRoomUserRequest.builder().userUuid(user1.getUserUuid()).userName("tester").build();

      given(userService.findByUserUuid(any())).willReturn(user1);
      given(studyRoomRepository.findByRoomUuid(any())).willReturn(Optional.of(studyRoom));
      given(roomUserRepository.save(any())).willReturn(roomUser1);

      roomUserService.createUser(studyRoom.getRoomUuid(),request); //이제 인원 다참.

      //when, then
      assertThrows(FullOfPersonnelException.class,()->{
        roomUserService.createUser(studyRoom.getRoomUuid(),request);
      });
    }
  }
}
