package com.twoleader.backend.domain.webRTC.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserMessageResponse {
    private UUID userUuid;
    private String userName;
}
