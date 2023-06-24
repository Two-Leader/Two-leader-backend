package com.twoleader.backend.domain.user.service;

import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.dto.request.LoginRequest;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.exception.ExistedUserException;
import com.twoleader.backend.domain.user.exception.NotFoundUserException;
import com.twoleader.backend.domain.user.mapper.UserMapper;
import com.twoleader.backend.domain.user.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Transactional
  public void createUser(CreateUserRequest request) {
    Optional<User> user = userRepository.findByEmail(request.getEmail());
    if (user.isPresent()) throw new ExistedUserException();
    userRepository.save(userMapper.toEntity(request));
  }

  public UUID login(LoginRequest request) {
    User user =
        userRepository
            .findByEmailAndPassword(request.getEmail(), request.getPassword())
            .orElseThrow(NotFoundUserException::new);
    return user.getUserUuid();
  }

  @Transactional
  public User deleteUser(UUID userUuid) {
    User user = userRepository.findByUserUuid(userUuid).orElseThrow(NotFoundUserException::new);
    user.changeDeleted();
    return userRepository.save(user);
  }

  public User findByUserUuid(UUID userUuid) {
    User user = userRepository.findByUserUuid(userUuid).orElseThrow(NotFoundUserException::new);
    return user;
  }
}
