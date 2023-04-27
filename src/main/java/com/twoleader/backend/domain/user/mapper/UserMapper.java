package com.twoleader.backend.domain.user.mapper;

import com.twoleader.backend.domain.studyRoom.entity.StudyRoom;
import com.twoleader.backend.domain.user.dto.request.CreateUserRequest;
import com.twoleader.backend.domain.user.dto.response.GetUserResponse;
import com.twoleader.backend.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  public GetUserResponse toDto(User user) {
    return GetUserResponse.builder()
        .user_uuid(user.getUser_uuid())
        .user_name(user.getUser_name())
        .build();
  }

  public List<GetUserResponse> toDto(List<User> users) {
    return users.stream().map(this::toDto).collect(Collectors.toList());
  }

  public User toEntity(CreateUserRequest request, StudyRoom studyRoom) {
    return User.builder().user_name(request.getUser_name()).room(studyRoom).build();
  }
}
