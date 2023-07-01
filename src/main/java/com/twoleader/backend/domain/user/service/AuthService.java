package com.twoleader.backend.domain.user.service;

import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.dto.request.LoginRequest;
import com.twoleader.backend.domain.user.exception.ExistedUserException;
import com.twoleader.backend.domain.user.mapper.UserMapper;
import com.twoleader.backend.domain.user.repository.UserRepository;
import com.twoleader.backend.global.config.security.JwtProvider;
import com.twoleader.backend.global.config.security.Token;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
  private final AuthenticationManagerBuilder managerBuilder;
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final UserMapper userMapper;

  public void signup(CreateUserRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) throw new ExistedUserException();
    userRepository.save(userMapper.toEntity(request, passwordEncoder));
  }

  public Token login(LoginRequest request) {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
    Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

    return jwtProvider.createToken(authentication);
  }
}
