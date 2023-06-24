package com.twoleader.backend.domain.studyRoom.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudyRoomRequest {

  @NotBlank private String roomName;
  @NotNull private UUID userUuid;
}
