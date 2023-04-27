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
import org.springframework.stereotype.Service;

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
    return rooms.stream().map(StudyRoom::toDto).collect(Collectors.toList());
  }

  public GetStudyRoomResponse findStudyRoomByRoom_uuid(UUID room_uuid) {
    StudyRoom studyRoom =
        studyRoomRepository.findStudyRoomByRoom_uuid(room_uuid).orElseThrow(NotFoundStudyRoom::new);
    return studyRoom.toDto();
  }
}
