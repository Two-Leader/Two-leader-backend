package com.twoleader.backend.domain.studyRoom.service;

import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.roomUser.repository.RoomUserRepository;
import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.exception.NotFoundStudyRoom;
import com.twoleader.backend.domain.studyRoom.mapper.StudyRoomMapper;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.service.UserService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudyRoomService {
  private final StudyRoomRepository studyRoomRepository;
  private final StudyRoomMapper studyRoomMapper;;
  private final UserService userService;

  public GetStudyRoomResponse createStudyRoom(CreateStudyRoomRequest request) {
    User user = userService.findByUserUuid(request.getUserUuid());
    StudyRoom studyRoom = studyRoomRepository.save(studyRoomMapper.toEntity(request, user));
    return studyRoomMapper.toDto(studyRoom);
  }

  public List<GetStudyRoomResponse> findAllStudyRoom() {
    List<StudyRoom> studyRooms = studyRoomRepository.findAll();
    return studyRoomMapper.toDto(studyRooms);
  }

  public GetStudyRoomResponse findStudyRoomByUuid(UUID roomUuid) {
    StudyRoom studyRoom =
        studyRoomRepository.findWithRoomUsersByRoomUuid(roomUuid).orElseThrow(NotFoundStudyRoom::new);
    return studyRoomMapper.toDto(studyRoom,studyRoom.getRoomUsers());
  }
}
