package com.twoleader.backend.domain.studyRoom.service;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.studyRoom.exception.NotFoundStudyRoom;
import com.twoleader.backend.domain.studyRoom.mapper.StudyRoomMapper;
import com.twoleader.backend.domain.studyRoom.repository.StudyRoomRepository;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudyRoomService {
  private final StudyRoomRepository studyRoomRepository;
  private final StudyRoomMapper studyRoomMapper;

  public GetStudyRoomResponse createStudyRoom(CreateStudyRoomRequest request) {
    StudyRoom studyRoom = studyRoomRepository.save(studyRoomMapper.toEntity(request));
    return studyRoomMapper.toDto(studyRoom);
  }

  public List<GetStudyRoomResponse> findAllStudyRoom() {
    List<StudyRoom> studyRooms = studyRoomRepository.findAll();
    return studyRoomMapper.toDto(studyRooms);
  }

  public GetStudyRoomResponse findStudyRoomByUuid(UUID RoomUuid) {
    StudyRoom studyRoom =
        studyRoomRepository.findStudyRoomByUuid(RoomUuid).orElseThrow(NotFoundStudyRoom::new);
    return studyRoomMapper.toDto(studyRoom);
  }
}
