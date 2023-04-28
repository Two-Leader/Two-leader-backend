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
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;
  private final StudyRoomRepository studyRoomRepository;
  private final UserMapper userMapper;

  public User createUser(CreateUserRequest request, UUID room_uuid) {
    log.info("[UserService][createUser] request : {}", request);
    StudyRoom studyRoom =
        studyRoomRepository.findStudyRoomByRoom_uuid(room_uuid).orElseThrow(NotFoundStudyRoom::new);

    return userRepository.save(userMapper.toEntity(request, studyRoom));
  }

  public GetUserResponse getUser(GetUserRequest request) {
    User user =
        userRepository.findUserByUserUuid(request.getUser_uuid()).orElseThrow(NotFoundUser::new);
    return userMapper.toDto(user);
  }

  public List<GetUserResponse> getUserByRoom_uuid(GetUserRequest request) {
    List<User> users = userRepository.findAllByRoom_uuid(request.getRoom_uuid());
    return userMapper.toDto(users);
  }
}
