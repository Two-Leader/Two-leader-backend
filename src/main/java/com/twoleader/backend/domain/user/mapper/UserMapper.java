package com.twoleader.backend.domain.user.mapper;

import static com.twoleader.backend.domain.user.entity.Authority.ROLE_USER;

import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public User toEntity(CreateUserRequest request, BCryptPasswordEncoder passwordEncoder) {
    return User.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .nickName(request.getNickName())
        .role(ROLE_USER)
        .build();
  }
}
