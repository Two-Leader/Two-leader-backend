package com.twoleader.backend.domain.user.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.exception.NotFoundUser;
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
public class UserRepositoryTest {

  @Autowired private UserRepository userRepository;
  @Autowired private StudyRoomRepository studyRoomRepository;

  private StudyRoom studyRoom;
  private User user;

  @BeforeEach
  public void setUp() {
    studyRoom = studyRoomRepository.save(StudyRoom.builder().roomName("testStudyRoom").build());

    user = userRepository.save(User.builder().studyRoom(studyRoom).userName("testUser").build());
  }

  @Nested
  @DisplayName("User uuid로 User찾기")
  class findUserByUserUuid {
    @Test
    @DisplayName("존재하는 User")
    public void findUserByUserUuidTestWhenUserExist() {
      // given
      User expectUser = user;

      // when
      User findUser =
          userRepository
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
      UUID user_uuid = UUID.randomUUID();

      // when,then
      Optional<User> findUser = userRepository.findUserByUserUuid(user_uuid);
      assertFalse(findUser.isPresent());
    }
  }
}
