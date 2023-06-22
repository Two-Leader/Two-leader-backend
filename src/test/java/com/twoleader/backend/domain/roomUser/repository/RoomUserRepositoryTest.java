package com.twoleader.backend.domain.roomUser.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.roomUser.exception.NotFoundUser;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoomUserRepositoryTest {

  @Autowired private RoomUserRepository roomUserRepository;
  @Autowired private StudyRoomRepository studyRoomRepository;

  private StudyRoom studyRoom;
  private RoomUser user;

  @BeforeEach
  public void setUp() {
    studyRoom = studyRoomRepository.save(StudyRoom.builder().roomName("testStudyRoom").build());

    user = roomUserRepository.save(RoomUser.builder().studyRoom(studyRoom).userName("testUser").build());
  }

  @Nested
  @DisplayName("User uuid로 User찾기")
  class findUserByUserUuid {
    @Test
    @DisplayName("존재하는 User")
    public void findUserByUserUuidTestWhenUserExist() {
      // given
      RoomUser expectUser = user;

      // when
      RoomUser findUser =
          roomUserRepository
              .findUserByUserUuid(expectUser.getUserUuid())
              .orElseThrow(NotFoundUser::new);

      // then
      assertEquals(expectUser.getUserId(), findUser.getUserId());
      assertEquals(expectUser.getUserUuid(), findUser.getUserUuid());
      assertEquals(expectUser.getUserName(), findUser.getUserName());
    }

    @Test
    @DisplayName("존재하지 않는 User")
    public void findUserbyUserUuidTestWhenUserNotExist() {
      // given
      UUID userUuid = UUID.randomUUID();

      // when,then
      Optional<RoomUser> findUser = roomUserRepository.findUserByUserUuid(userUuid);
      assertFalse(findUser.isPresent());
    }
  }

  @Nested
  @DisplayName("Study Room안 User check")
  class checkUserInStudyRoom {

    @Test
    @DisplayName("Study Room에 유저 한 명만 존재 check")
    public void checkUsersInStudyRoomWhenExistOneUser() {
      // given
      UUID roomUuid = studyRoom.getRoomUuid();
      boolean checkUsers = roomUserRepository.checkUsersByRoomUuid(roomUuid);

      assertFalse(checkUsers);
    }

    @Test
    @DisplayName("Study Room에 유저 두 명 존재")
    public void checkUsersInStudyRoomWhenExistTwoUser() {
      // given
      roomUserRepository.save(RoomUser.builder().studyRoom(studyRoom).userName("user2").build());
      UUID roomUuid = studyRoom.getRoomUuid();
      boolean checkUsers = roomUserRepository.checkUsersByRoomUuid(roomUuid);

      assertTrue(checkUsers);
    }
  }

  @Test
  @DisplayName("StudyRoom 안 User 찾기")
  public void findAllUserByStudyRoomUuid() {
    // given
    UUID roomUuid = studyRoom.getRoomUuid();
    List<RoomUser> users = roomUserRepository.findAllInStudyRoomByStudyRoomUuid(roomUuid);

    assertEquals(user.getUserUuid(), users.get(0).getUserUuid());
  }

  @Test
  @DisplayName("User 삭제")
  public void deleteUserByUserUuid() {
    // given
    List<RoomUser> users = roomUserRepository.findAll();
    assertEquals(users.size(), 1);
    UUID userUuid = user.getUserUuid();

    // when
    roomUserRepository.deleteByUserUuid(userUuid);

    // then
    users = roomUserRepository.findAll();
    assertEquals(users.size(), 0);
  }
}
