package com.twoleader.backend.domain.user.mapper;

import static com.twoleader.backend.domain.user.entity.Authority.ROLE_USER;

import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.dto.response.LoginResponse;
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

  public User toEntity(CreateUserRequest request) {
    return User.builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .nickName(request.getNickName())
            .role(ROLE_USER)
            .build();
  }

  public LoginResponse toDto(User user){
    return LoginResponse.builder().userUuid(user.getUserUuid()).nickName(user.getNickName()).build();
  }
}
