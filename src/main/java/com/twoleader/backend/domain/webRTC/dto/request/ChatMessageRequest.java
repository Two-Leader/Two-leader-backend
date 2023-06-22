package com.twoleader.backend.domain.webRTC.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class ChatMessageRequest {
    private String userName;
    private String message;
}
