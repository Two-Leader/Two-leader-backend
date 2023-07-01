package com.twoleader.backend.domain.studyRoom.dto.response;

import com.twoleader.backend.domain.roomUser.dto.response.GetRoomUserResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

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