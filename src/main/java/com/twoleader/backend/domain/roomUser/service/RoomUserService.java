package com.twoleader.backend.domain.roomUser.service;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.exception.NotFoundStudyRoom;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import com.twoleader.backend.domain.roomUser.dto.request.CreateRoomUserRequest;
import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.roomUser.exception.NotFoundUser;
import com.twoleader.backend.domain.roomUser.mapper.RoomUserMapper;
import com.twoleader.backend.domain.roomUser.repository.RoomUserRepository;
import java.util.List;
import java.util.UUID;

import com.twoleader.backend.domain.webRTC.service.SendMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RoomUserService {
  private final RoomUserRepository roomUserRepository;
  private final StudyRoomRepository studyRoomRepository;
  private final SendMessagingService sendMessagingService;
  private final RoomUserMapper roomUserMapper;

  @Transactional
  public GetRoomUserResponse createUser(CreateRoomUserRequest request) {
    StudyRoom studyRoom =
        studyRoomRepository
            .findStudyRoomByUuid(request.getRoomUuid())
            .orElseThrow(NotFoundStudyRoom::new);
    RoomUser user = roomUserRepository.save(roomUserMapper.toEntity(request, studyRoom));
    return roomUserMapper.toDto(user);
  }

  public GetRoomUserResponse getUser(UUID userUuid) {
    RoomUser user = roomUserRepository.findUserByUserUuid(userUuid).orElseThrow(NotFoundUser::new);
    return roomUserMapper.toDto(user);
  }

  public List<RoomUser> findAllUserInStudyRoomByStudyRoomUuid(UUID studyRoomUuid) {
    return roomUserRepository.findAllInStudyRoomByStudyRoomUuid(studyRoomUuid);
  }

  public void deleteUserByUuid(UUID userUuid) {
    roomUserRepository.deleteByUserUuid(userUuid);
  }
}
