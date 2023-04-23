package com.twoleader.backend.domain.studyRoom.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class GetStudyRoomResponse {
    private UUID room_uuid;
    private String room_name;
}
