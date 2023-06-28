package com.twoleader.backend.domain.roomUser.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.roomUser.exception.NotFoundRoomUserException;
import com.twoleader.backend.domain.roomUser.mapper.RoomUserMapper;
import com.twoleader.backend.domain.roomUser.repository.RoomUserRepository;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import java.util.Optional;
import java.util.UUID;
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
  @Spy private RoomUserMapper roomUserMapper;
  @InjectMocks private RoomUserService roomUserService;

  private static RoomUser roomUser;
  private static StudyRoom studyRoom;

  @BeforeAll
  public static void setUp() {
    studyRoom =
        StudyRoom.builder()
            .studyRoomId(1L)
            .roomUuid(UUID.randomUUID())
            .roomName("testStudyRoom")
            .build();

    roomUser =
        RoomUser.builder().roomUserId(1L).roomUserName("testUser").studyRoom(studyRoom).build();
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
}
