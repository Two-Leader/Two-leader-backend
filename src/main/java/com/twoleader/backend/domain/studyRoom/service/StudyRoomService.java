package com.twoleader.backend.domain.studyRoom.service;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.exception.NotFoundStudyRoom;
import com.twoleader.backend.domain.studyRoom.mapper.StudyRoomMapper;
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
  private final StudyRoomMapper studyRoomMapper;

  public StudyRoom createStudyRoom(CreateStudyRoomRequest request) {
    return studyRoomRepository.save(studyRoomMapper.toEntity(request));
  }

  public List<GetStudyRoomResponse> findAllStudyRoom() {
    List<StudyRoom> studyRooms = studyRoomRepository.findAll();
    return studyRoomMapper.toDto(studyRooms);
  }

  public StudyRoom findStudyRoomByUuid(UUID studyRoomUuid) {
    return studyRoomRepository.findStudyRoomByUuid(studyRoomUuid).orElseThrow(NotFoundStudyRoom::new);
  }
}
