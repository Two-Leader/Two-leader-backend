package com.twoleader.backend.domain.studyRoom.mapper;

import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import com.twoleader.backend.domain.studyRoom.dto.request.CreateStudyRoomRequest;
import com.twoleader.backend.domain.studyRoom.dto.response.GetAllStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.dto.response.GetStudyRoomResponse;
import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class StudyRoomMapper {

  public StudyRoom toEntity(CreateStudyRoomRequest request, User user) {
    return StudyRoom.builder()
            .roomName(request.getRoomName())
            .information(request.getInformation())
            .password(request.getPassword())
            .totalNop(request.getTotalNop())
            .constructor(user).build();
  }

  public List<GetAllStudyRoomResponse> toDto(List<StudyRoom> studyRooms) {
    return studyRooms.stream().map(this::toDto).collect(Collectors.toList());
  }

  public GetAllStudyRoomResponse toDto(StudyRoom studyRoom) {
    return GetAllStudyRoomResponse.builder()
        .roomUuid(studyRoom.getRoomUuid())
        .roomName(studyRoom.getRoomName())
        .roomInformation(studyRoom.getInformation())
        .totalNop(studyRoom.getTotalNop())
        .nowTotalNop(studyRoom.getNowTotalNop())
        .isLocked(studyRoom.getPassword() != null)
        .constructorName(studyRoom.getConstructor().getNickName())
        .build();
  }

  public GetStudyRoomResponse toDto(StudyRoom studyRoom, List<RoomUser> users) {
    return GetStudyRoomResponse.builder()
        .roomName(studyRoom.getRoomName())
        .constructorName(studyRoom.getConstructor().getNickName())
        .users(users.stream().map(this::toDto).collect(Collectors.toList()))
        .build();
  }

  private GetRoomUserResponse toDto(RoomUser user) {
    return GetRoomUserResponse.builder()
        .userId(user.getRoomUserId())
        .userName(user.getRoomUserName())
        .userUuid(user.getUser().getUserUuid())
        .build();
  }
}
