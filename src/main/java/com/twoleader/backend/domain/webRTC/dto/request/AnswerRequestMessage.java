package com.twoleader.backend.domain.webRTC.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class AnswerRequestMessage {
    private UUID from;
    private String sdp;
}
