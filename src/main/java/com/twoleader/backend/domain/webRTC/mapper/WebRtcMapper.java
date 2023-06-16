package com.twoleader.backend.domain.webRTC.mapper;

import com.twoleader.backend.domain.webRTC.dto.response.AnswerResponseMessage;
import com.twoleader.backend.domain.webRTC.dto.response.IceCandidateResponseMessage;
import org.kurento.client.IceCandidate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WebRtcMapper {
    public IceCandidateResponseMessage toDto(UUID userUuid, IceCandidate iceCandidate){
        return IceCandidateResponseMessage.builder()
                .userUuid(userUuid)
                .sdp(iceCandidate.getCandidate())
                .sdpMid(iceCandidate.getSdpMid())
                .sdpMLineIndex(iceCandidate.getSdpMLineIndex())
                .build();
    }

    public AnswerResponseMessage toDto(UUID userUuid,String sdp){
        return AnswerResponseMessage.builder()
                .userUuid(userUuid)
                .sdp(sdp)
                .build();
    }
}
