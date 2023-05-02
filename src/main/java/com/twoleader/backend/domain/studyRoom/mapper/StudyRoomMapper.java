package com.twoleader.backend.domain.studyRoom.mapper;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class StudyRoomMapper {
  public GetStudyRoomResponse toDto(StudyRoom studyRoom, Boolean hasUser) {
    return GetStudyRoomResponse.builder()
        .room_uuid(studyRoom.getRoom_uuid())
        .room_name(studyRoom.getRoom_name())
        .hasUser(hasUser)
        .build();
  }

  public GetStudyRoomResponse toDto(StudyRoom studyRoom) {
    return GetStudyRoomResponse.builder()
        .room_uuid(studyRoom.getRoom_uuid())
        .room_name(studyRoom.getRoom_name())
        .build();
  }

  public List<GetStudyRoomResponse> toDto(List<StudyRoom> studyRooms) {
    return studyRooms.stream().map(this::toDto).collect(Collectors.toList());
  }

  public StudyRoom toEntity(CreateStudyRoomRequest request) {
    return StudyRoom.builder().room_name(request.getRoom_name()).build();
  }
}
