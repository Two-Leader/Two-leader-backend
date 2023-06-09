package com.twoleader.backend.domain.roomUser.service;

import com.twoleader.backend.domain.roomUser.dto.request.CreateRoomUserRequest;
import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.roomUser.exception.FullOfPersonnelException;
import com.twoleader.backend.domain.roomUser.exception.NotFoundRoomUserException;
import com.twoleader.backend.domain.roomUser.mapper.RoomUserMapper;
import com.twoleader.backend.domain.roomUser.repository.RoomUserRepository;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.exception.NotFoundStudyRoom;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = {RuntimeException.class})
@RequiredArgsConstructor
@Service
public class RoomUserService {
  private final RoomUserRepository roomUserRepository;
  private final StudyRoomRepository studyRoomRepository;
  private final RoomUserMapper roomUserMapper;
  private final UserService userService;

  public GetRoomUserResponse createUser(UUID roomUuid, CreateRoomUserRequest request) {
    User user = userService.findByUserUuid(request.getUserUuid());
    StudyRoom studyRoom =
        studyRoomRepository.findByRoomUuid(roomUuid).orElseThrow(NotFoundStudyRoom::new);
    if (studyRoom.getNowTotalNop() >= studyRoom.getTotalNop()) throw new FullOfPersonnelException();
    RoomUser roomUser = roomUserRepository.save(roomUserMapper.toEntity(request, studyRoom, user));
    studyRoom.addRoomUser();
    return roomUserMapper.toDto(roomUser);
  }

  public void createUser(String userName, StudyRoom studyRoom, User user) {
    roomUserRepository.save(roomUserMapper.toEntity(userName, studyRoom, user));
    studyRoom.addRoomUser();
  }

  public GetRoomUserResponse getUser(long userId) {
    RoomUser user = roomUserRepository.findById(userId).orElseThrow(NotFoundRoomUserException::new);
    return roomUserMapper.toDto(user);
  }

  public void deleteUserById(long userId) {
    RoomUser user = roomUserRepository.findById(userId).orElseThrow(NotFoundRoomUserException::new);
    roomUserRepository.delete(user);
    user.getStudyRoom().deleteRoomUser();
  }
}
