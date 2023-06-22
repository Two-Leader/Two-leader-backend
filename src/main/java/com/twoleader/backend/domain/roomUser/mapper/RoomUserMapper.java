package com.twoleader.backend.domain.roomUser.mapper;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.roomUser.dto.request.CreateRoomUserRequest;
import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import com.twoleader.backend.domain.roomUser.entity.RoomUser;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class RoomUserMapper {

  public RoomUser toEntity(CreateRoomUserRequest request, StudyRoom studyRoom) {
    return RoomUser.builder().userName(request.getUserName()).studyRoom(studyRoom).build();
  }

  public GetRoomUserResponse toDto(RoomUser user) {
    return GetRoomUserResponse.builder()
        .userUuid(user.getUserUuid())
        .userName(user.getUserName())
        .build();
  }

  public List<GetRoomUserResponse> toDto(List<RoomUser> users) {
    return users.stream().map(this::toDto).collect(Collectors.toList());
  }
}
