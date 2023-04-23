package com.twoleader.backend.webRTC.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@ToString
@Builder
@Getter
public class WebSocketMessage {
    private String from;
    private String type;
    private String data;
    private Object candidate;
    private Object sdp;
}
