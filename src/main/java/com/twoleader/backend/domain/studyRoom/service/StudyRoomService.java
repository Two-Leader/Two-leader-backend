package com.twoleader.backend.domain.studyRoom.service;

import com.twoleader.backend.domain.roomUser.service.RoomUserService;
import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetAllStudyRoomResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudyRoomService {
  private final StudyRoomRepository studyRoomRepository;
  private final StudyRoomMapper studyRoomMapper;

  private final UserService userService;
  private final RoomUserService roomUserService;

  public UUID createStudyRoom(CreateStudyRoomRequest request) {
    User user = userService.findByUserUuid(request.getUserUuid());
    StudyRoom studyRoom = studyRoomRepository.save(studyRoomMapper.toEntity(request, user));
    roomUserService.createUser(request.getUserName(), studyRoom, user);
    return studyRoom.getRoomUuid();
  }

  public Page<GetAllStudyRoomResponse> findAllStudyRoom(Pageable pageable) {
    Page<StudyRoom> studyRooms = studyRoomRepository.findAll(pageable);
    return studyRoomMapper.toDto(studyRooms);
  }

  public GetStudyRoomResponse findStudyRoomByUuid(UUID roomUuid) {
    StudyRoom studyRoom =
        studyRoomRepository
            .findWithRoomUsersByRoomUuid(roomUuid)
            .orElseThrow(NotFoundStudyRoom::new);
    return studyRoomMapper.toDto(studyRoom, studyRoom.getRoomUsers());
  }

  public boolean checkStudyRoomPassword(UUID roomUuid, String password) {
    StudyRoom studyRoom =
        studyRoomRepository.findByRoomUuid(roomUuid).orElseThrow(NotFoundStudyRoom::new);
    return studyRoom.getPassword().equals(password);
  }

  public void deleteStudyRoom(UUID roomUuid) {
    StudyRoom studyRoom =
        studyRoomRepository.findByRoomUuid(roomUuid).orElseThrow(NotFoundStudyRoom::new);
    studyRoomRepository.delete(studyRoom);
  }
}
