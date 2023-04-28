package com.twoleader.backend.domain.studyRoom.service;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.exception.NotFoundStudyRoom;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import com.twoleader.backend.domain.user.repository.UserRepository;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudyRoomService {
  private final StudyRoomRepository studyRoomRepository;
  private final UserRepository userRepository;

  public StudyRoom createStudyRoom(CreateStudyRoomRequest request) {
    StudyRoom studyRoom = request.toEntity();
    return studyRoomRepository.save(studyRoom);
  }

  public List<GetStudyRoomResponse> findAllStudyRoom() {
    List<StudyRoom> rooms = studyRoomRepository.findAll();
    log.info("[studyRoomService][findAllStudyRoom] rooms : {}",rooms);
    return rooms.stream().map(StudyRoom::toDto).collect(Collectors.toList());
  }

  public GetStudyRoomResponse findStudyRoomByRoom_uuid(UUID room_uuid) {
    log.info("[studyRoomService][findAllStudyRoom] uuid : {}",room_uuid);
    StudyRoom studyRoom =
        studyRoomRepository.findStudyRoomByRoom_uuid(room_uuid).orElseThrow(NotFoundStudyRoom::new);
    Boolean hasUser = userRepository.existsInRoomByRoom_uuid(room_uuid)
    return studyRoom.toDto();
  }
}
