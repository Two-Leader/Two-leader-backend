package com.twoleader.backend.domain.user.service;

import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.dto.request.LoginRequest;
import com.twoleader.backend.domain.user.dto.response.LoginResponse;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.exception.ExistedUserException;
import com.twoleader.backend.domain.user.exception.NotFoundUserException;
import com.twoleader.backend.domain.user.mapper.UserMapper;
import com.twoleader.backend.domain.user.repository.UserRepository;
import java.util.UUID;

import com.twoleader.backend.global.config.security.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public void deleteUser(UUID userUuid) {
    User user = userRepository.findByUserUuid(userUuid).orElseThrow(NotFoundUserException::new);
    user.changeDeleted(); // user는 영속성 컨텍스트에 이미 로딩되있어 따로 저장하지 않아도 save됨.
  }

  public User findByUserUuid(UUID userUuid) {
    return userRepository.findByUserUuid(userUuid).orElseThrow(NotFoundUserException::new);
  }

  public void signup(CreateUserRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) throw new ExistedUserException();
    userRepository.save(userMapper.toEntity(request));
  }

  public LoginResponse login(LoginRequest request) {
    User user = userRepository.findByEmailAndPassword(request.getEmail(),request.getPassword()).orElseThrow(NotFoundUserException::new);
    return userMapper.toDto(user);
  }
}
