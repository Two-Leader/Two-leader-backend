package com.twoleader.backend.domain.roomUser.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.roomUser.exception.NotFoundUser;
import com.twoleader.backend.domain.roomUser.repository.RoomUserRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(profiles = {"test"})
public class RoomUserServiceTest {
  @Mock private RoomUserRepository roomUserRepository;

  @InjectMocks private RoomUserService roomUserService;

  private static RoomUser user;
  private static StudyRoom studyRoom;

  @BeforeAll
  public static void setUp() {
    studyRoom =
        StudyRoom.builder()
            .roomId(1L)
            .roomUuid(UUID.randomUUID())
            .roomName("testStudyRoom")
            .build();

    user =
        RoomUser.builder()
            .userId(1L)
            .userUuid(UUID.randomUUID())
            .userName("testUser")
            .studyRoom(studyRoom)
            .build();
  }

  @Nested
  @DisplayName("Uuid로 User찾기")
  class getUserByUserUuid {
    @Test
    @DisplayName("User 존재")
    public void getUserByUserUuidTestwhenUserExist() {
      // given
      given(roomUserRepository.findUserByUserUuid(any())).willReturn(Optional.ofNullable(user));
      UUID userUuid = user.getUserUuid();

      // when
      GetRoomUserResponse response = roomUserService.getUser(userUuid);

      // then
      assertEquals(user.getUserUuid(), response.getUserUuid());
      assertEquals(user.getUserName(), response.getUserName());
    }

    @Test
    @DisplayName("존재하지 않는 User")
    public void getUserByUserUuidTestWhenUserNotExist() {
      // given
      given(roomUserRepository.findUserByUserUuid(any())).willReturn(Optional.ofNullable(null));
      UUID userUuid = UUID.randomUUID();

      // when,then
      assertThrows(
          NotFoundUser.class,
          () -> {
            GetRoomUserResponse response = roomUserService.getUser(userUuid);
          });
    }
  }
}
