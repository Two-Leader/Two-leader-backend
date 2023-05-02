package com.twoleader.backend.domain.studyRoom.service;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.exception.NotFoundStudyRoom;
import com.twoleader.backend.domain.studyRoom.mapper.StudyRoomMapper;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import com.twoleader.backend.domain.user.repository.UserRepository;
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
  private final UserRepository userRepository;
  private final StudyRoomMapper studyRoomMapper;

  public StudyRoom createStudyRoom(CreateStudyRoomRequest request) {
    StudyRoom studyRoom = studyRoomMapper.toEntity(request);
    return studyRoomRepository.save(studyRoom);
  }

  public List<GetStudyRoomResponse> findAllStudyRoom() {
    List<StudyRoom> rooms = studyRoomRepository.findAll();
    return studyRoomMapper.toDto(rooms);
  }

  @Transactional
  public GetStudyRoomResponse findStudyRoomByRoom_uuid(UUID room_uuid) {
    StudyRoom studyRoom =
        studyRoomRepository.findStudyRoomByRoom_uuid(room_uuid).orElseThrow(NotFoundStudyRoom::new);
    Boolean hasUser = userRepository.checkUsersByRoom_uuid(room_uuid);
    return studyRoomMapper.toDto(studyRoom, hasUser);
  }
}
