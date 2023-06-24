package com.twoleader.backend.domain.user.mapper;

import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  public User toEntity(CreateUserRequest request) {
    return User.builder()
        .email(request.getEmail())
        .password(request.getPassword())
        .nickName(request.getNickName())
        .build();
  }
}
