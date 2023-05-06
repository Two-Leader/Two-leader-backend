package com.twoleader.backend.domain.user.service;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.exception.NotFoundStudyRoom;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.dto.request.GetUserRequest;
import com.twoleader.backend.domain.user.dto.response.GetUserResponse;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.exception.NotFoundUser;
import com.twoleader.backend.domain.user.mapper.UserMapper;
import com.twoleader.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;
  private final StudyRoomRepository studyRoomRepository;
  private final UserMapper userMapper;

  @Transactional
  public GetUserResponse createUser(CreateUserRequest request) {
    StudyRoom studyRoom = studyRoomRepository.findStudyRoomByUuid(request.getRoomUuid()).orElseThrow(NotFoundStudyRoom::new);
    User user = userRepository.save(userMapper.toEntity(request,studyRoom));
    return userMapper.toDto(user);
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
