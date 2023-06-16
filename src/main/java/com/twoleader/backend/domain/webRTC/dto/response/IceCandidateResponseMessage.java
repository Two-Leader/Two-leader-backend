package com.twoleader.backend.domain.webRTC.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;

@AllArgsConstructor
@Builder
public class IceCandidateResponseMessage{
    private UUID userUuid;
    private String sdp;
    private String sdpMid;
    private int sdpMLineIndex;
}
