package com.twoleader.backend.domain.studyRoom.service;

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
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudyRoomService {
  private final StudyRoomRepository studyRoomRepository;
  private final StudyRoomMapper studyRoomMapper;

  private final UserService userService;

  public void createStudyRoom(CreateStudyRoomRequest request) {
    User user = userService.findByUserUuid(request.getUserUuid());
    studyRoomRepository.save(studyRoomMapper.toEntity(request, user));
  }

  public List<GetAllStudyRoomResponse> findAllStudyRoom() {
    List<StudyRoom> studyRooms = studyRoomRepository.findAll();
    return studyRoomMapper.toDto(studyRooms);
  }

  public GetStudyRoomResponse findStudyRoomByUuid(UUID roomUuid) {
    StudyRoom studyRoom =
        studyRoomRepository
            .findWithRoomUsersByRoomUuid(roomUuid)
            .orElseThrow(NotFoundStudyRoom::new);
    return studyRoomMapper.toDto(studyRoom, studyRoom.getRoomUsers());
  }

  public boolean checkStudyRoomPassword(UUID roomUuid, String password){
    StudyRoom studyRoom = studyRoomRepository.findByRoomUuid(roomUuid).orElseThrow(NotFoundStudyRoom::new);
    return studyRoom.getPassword().equals(password);
  }
}
