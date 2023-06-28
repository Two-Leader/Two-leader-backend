package com.twoleader.backend.domain.user.service;

import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.exception.NotFoundUserException;
import com.twoleader.backend.domain.user.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;

  @Transactional
  public void deleteUser(UUID userUuid) {
    User user = userRepository.findByUserUuid(userUuid).orElseThrow(NotFoundUserException::new);
    user.changeDeleted();
  }

  public User findByUserUuid(UUID userUuid) {
    User user = userRepository.findByUserUuid(userUuid).orElseThrow(NotFoundUserException::new);
    return user;
  }
}
