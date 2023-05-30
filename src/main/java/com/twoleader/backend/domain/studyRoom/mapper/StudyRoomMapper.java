package com.twoleader.backend.domain.studyRoom.mapper;

import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import java.util.List;
import java.util.stream.Collectors;

import com.twoleader.backend.domain.user.dto.response.GetUserResponse;
import com.twoleader.backend.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class StudyRoomMapper {

  public StudyRoom toEntity(CreateStudyRoomRequest request) {
    return StudyRoom.builder().roomName(request.getRoomName()).build();
  }

  public GetStudyRoomResponse toDto(StudyRoom studyRoom) {
    return GetStudyRoomResponse.builder()
        .roomUuid(studyRoom.getRoomUuid())
        .roomName(studyRoom.getRoomName())
        .build();
  }


  public List<GetStudyRoomResponse> toDto(List<StudyRoom> studyRooms) {
    return studyRooms.stream().map(this::toDto).collect(Collectors.toList());
  }

  public GetStudyRoomResponse toDto(StudyRoom studyRoom, List<User> users) {
    return GetStudyRoomResponse.builder()
        .roomUuid(studyRoom.getRoomUuid())
        .roomName(studyRoom.getRoomName())
        .users(users.stream().map(this::toDto).collect(Collectors.toList()))
        .checkUser(users.size() > 1)
        .build();
  }

  private GetUserResponse toDto(User user) {
    return GetUserResponse.builder()
            .userUuid(user.getUserUuid())
            .userName(user.getUserName())
            .build();
  }
}
