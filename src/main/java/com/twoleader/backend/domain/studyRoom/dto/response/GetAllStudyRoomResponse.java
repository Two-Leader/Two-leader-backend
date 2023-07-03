package com.twoleader.backend.domain.studyRoom.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetAllStudyRoomResponse {
  private UUID roomUuid;
  private String roomName;
  private String roomInformation;
  private String constructorName;
  private int totalNop;
  private int nowTotalNop;
  private boolean isLocked;
}
