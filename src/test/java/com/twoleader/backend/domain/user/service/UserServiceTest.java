package com.twoleader.backend.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.dto.request.LoginRequest;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.exception.ExistedUserException;
import com.twoleader.backend.domain.user.exception.NotFoundUserException;
import com.twoleader.backend.domain.user.mapper.UserMapper;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(profiles = {"test"})
public class UserServiceTest {
  @Mock private UserRepository userRepository;

  @InjectMocks private UserService userService;

  @Spy private UserMapper userMapper;

  private List<User> users = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    users.add(
        User.builder()
            .userId(1L)
            .userUuid(UUID.randomUUID())
            .email("testEmail1")
            .password("testPassword")
            .nickName("tester1")
            .build());
    users.add(
        User.builder()
            .userId(2L)
            .userUuid(UUID.randomUUID())
            .email("testEmail2")
            .password("testPassword")
            .nickName("tester2")
            .build());
  }

  @DisplayName("User 생성 Test")
  @Nested
  class createUserTest {
    @DisplayName("User가 존재하지 않을 때")
    @Test
    public void whenNotExistedUser() {
      // given
      CreateUserRequest request =
          CreateUserRequest.builder()
              .email("testEmail")
              .password("testPassword")
              .nickName("tester")
              .build();
      given(userRepository.findByEmail(any())).willReturn(Optional.empty());

      // when, then
      userService.createUser(request);
    }

    @DisplayName("User가 존재할 때")
    @Test
    public void whenExistedUser() {
      // given
      User user = users.get(0);
      CreateUserRequest request =
          CreateUserRequest.builder()
              .email(user.getEmail())
              .password(user.getPassword())
              .nickName(user.getNickName())
              .build();
      given(userRepository.findByEmail(any())).willReturn(Optional.of(user));

      // when, then
      assertThrows(
          ExistedUserException.class,
          () -> {
            userService.createUser(request);
          });
    }
  }

  @DisplayName("User 로그인 Test")
  @Nested
  class loginUserTest {
    @DisplayName("User가 존재하지 않을 때")
    @Test
    public void whenNotExistedUser() {
      // given
      LoginRequest request =
          LoginRequest.builder()
              .email("notExistedUserEamil")
              .password("notExsitedUserPassword")
              .build();
      given(userRepository.findByEmailAndPassword(any(), any())).willReturn(Optional.empty());

      // when, then
      assertThrows(
          NotFoundUserException.class,
          () -> {
            userService.login(request);
          });
    }

    @DisplayName("User가 존재할 때")
    @Test
    public void whenExistedUser() {
      // given
      User user = users.get(0);
      LoginRequest request =
          LoginRequest.builder().email(user.getEmail()).password(user.getPassword()).build();
      given(userRepository.findByEmailAndPassword(any(), any())).willReturn(Optional.of(user));

      UUID foundUuid = userService.login(request);
      assertEquals(user.getUserUuid(), foundUuid);
    }
  }
}
