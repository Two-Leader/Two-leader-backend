package com.twoleader.backend.domain.roomUser.service;

import com.twoleader.backend.domain.roomUser.dto.request.CreateRoomUserRequest;
import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.roomUser.exception.NotFoundRoomUserException;
import com.twoleader.backend.domain.roomUser.mapper.RoomUserMapper;
import com.twoleader.backend.domain.roomUser.repository.RoomUserRepository;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.exception.NotFoundStudyRoom;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RoomUserService {
  private final RoomUserRepository roomUserRepository;
  private final StudyRoomRepository studyRoomRepository;
  private final RoomUserMapper roomUserMapper;
  private final UserService userService;

  @Transactional
  public GetRoomUserResponse createUser(UUID roomUuid, CreateRoomUserRequest request) {
    User user = userService.findByUserUuid(request.getUserUuid());
    StudyRoom studyRoom =
        studyRoomRepository.findStudyRoomByUuid(roomUuid).orElseThrow(NotFoundStudyRoom::new);
    RoomUser roomUser = roomUserRepository.save(roomUserMapper.toEntity(request, studyRoom, user));
    return roomUserMapper.toDto(roomUser);
  }

  public GetRoomUserResponse getUser(long userId) {
    RoomUser user = roomUserRepository.findById(userId).orElseThrow(NotFoundRoomUserException::new);
    return roomUserMapper.toDto(user);
  }

  public List<RoomUser> findAllUserInStudyRoomByStudyRoomUuid(UUID studyRoomUuid) {
    return roomUserRepository.findAllInStudyRoomByStudyRoomUuid(studyRoomUuid);
  }

  public void deleteUserByUuid(long userId) {
    RoomUser user = roomUserRepository.findById(userId).orElseThrow(NotFoundRoomUserException::new);
    user.changeDeleted();
    roomUserRepository.save(user);
  }
}
