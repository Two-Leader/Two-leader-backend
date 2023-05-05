package com.twoleader.backend.domain.user.service;

import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.dto.request.GetUserRequest;
import com.twoleader.backend.domain.user.dto.response.GetUserResponse;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.exception.NotFoundUser;
import com.twoleader.backend.domain.user.mapper.UserMapper;
import com.twoleader.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public User createUser(CreateUserRequest request) {
    return userRepository.save(userMapper.toEntity(request));
  }

  public GetUserResponse getUser(GetUserRequest request) {
    User user =
        userRepository.findUserByUserUuid(request.getUserUuid()).orElseThrow(NotFoundUser::new);
    return userMapper.toDto(user);
  }

  public List<User> findAllUserInStudyRoomByStudyRoomUuid(UUID studyRoomUuid){
    return userRepository.findAllInStudyRoomByStudyRoomUuid(studyRoomUuid);
  }

  public void deleteUserByUuid(UUID userUuid){
    userRepository.deleteByUuid(userUuid);
  }
}
