package com.twoleader.backend.domain.user.service;

import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.dto.request.GetUserRequest;
import com.twoleader.backend.domain.user.dto.response.GetUserResponse;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.exception.NotFoundUser;
import com.twoleader.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;

  public User createUser(CreateUserRequest userDto) {
    return userRepository.save(userDto.toEntity());
  }

  public GetUserResponse getUser(GetUserRequest request) {
    User user =
        userRepository.findUserByUserName(request.getUser_name()).orElseThrow(NotFoundUser::new);
    return user.toDto();
  }
}
