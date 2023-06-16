package com.twoleader.backend.domain.webRTC.dto.response;


import lombok.Builder;

import java.util.UUID;

@Builder
public class AnswerResponseMessage {
    private UUID userUuid;
    private String sdp;
}
