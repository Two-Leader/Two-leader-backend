package com.twoleader.backend.domain.roomUser.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.repository.UserRepository;
import java.util.ArrayList;
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
  @Autowired private UserRepository userRepository;

  private StudyRoom studyRoom;
  private RoomUser roomUser;
  private List<User> users = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    studyRoom = studyRoomRepository.save(StudyRoom.builder().roomName("testStudyRoom").build());
    users.add(
        userRepository.save(
            User.builder()
                .email("testEmail1")
                .password("testPassword")
                .nickName("tester1")
                .build()));
    users.add(
        userRepository.save(
            User.builder()
                .email("testEmail2")
                .password("testPassword")
                .nickName("tester2")
                .build()));
    studyRoom =
        studyRoomRepository.save(
            StudyRoom.builder().constructor(users.get(0)).roomName("testStudyRoom").build());
    roomUser =
        roomUserRepository.save(
            RoomUser.builder()
                .studyRoom(studyRoom)
                .user(users.get(1))
                .roomUserName("testerName")
                .roomUserName("testUser")
                .build());
  }

  @Nested
  @DisplayName("User uuid로 User찾기")
  class findUserByUserUuid {
    @Test
    @DisplayName("존재하는 User")
    public void findUserByUserUuidTestWhenUserExist() {
      // given
      RoomUser expectUser = roomUser;

      // when
      Optional<RoomUser> findUser = roomUserRepository.findById(expectUser.getRoomUserId());

      assertTrue(findUser.isPresent());
      // then
      assertEquals(expectUser.getRoomUserId(), findUser.get().getRoomUserId());
      assertEquals(expectUser.getRoomUserName(), findUser.get().getRoomUserName());
    }

    @Test
    @DisplayName("존재하지 않는 User")
    public void findUserbyUserUuidTestWhenUserNotExist() {
      // when,then
      Optional<RoomUser> findUser = roomUserRepository.findById(100L);
      assertFalse(findUser.isPresent());
    }
  }

  @Test
  @DisplayName("StudyRoom 안 User 찾기")
  public void findAllUserByStudyRoomUuid() {
    // given
    UUID roomUuid = studyRoom.getRoomUuid();
    List<RoomUser> users = roomUserRepository.findAllInStudyRoomByStudyRoomUuid(roomUuid);

    assertEquals(roomUser.getRoomUserId(), users.get(0).getRoomUserId());
  }
}
