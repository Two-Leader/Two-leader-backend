package com.twoleader.backend.domain.studyRoom.mapper;

import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import java.util.List;
import java.util.stream.Collectors;
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

  public GetStudyRoomResponse toDto(StudyRoom studyRoom, List<RoomUser> users) {
    return GetStudyRoomResponse.builder()
        .roomUuid(studyRoom.getRoomUuid())
        .roomName(studyRoom.getRoomName())
        .users(users.stream().map(this::toDto).collect(Collectors.toList()))
        .checkUser(users.size() > 1)
        .build();
  }

  private GetRoomUserResponse toDto(RoomUser user) {
    return GetRoomUserResponse.builder()
        .userUuid(user.getUserUuid())
        .userName(user.getUserName())
        .build();
  }
}
