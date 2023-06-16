package com.twoleader.backend.domain.webRTC.service;

import com.twoleader.backend.domain.user.dto.response.GetUserResponse;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.service.UserService;
import com.twoleader.backend.domain.webRTC.dto.response.IceCandidateResponseMessage;
import com.twoleader.backend.domain.webRTC.mapper.WebRtcMapper;
import com.twoleader.backend.global.result.webSocket.OutputMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kurento.client.IceCandidate;
import org.kurento.client.WebRtcEndpoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.twoleader.backend.global.result.webSocket.OutputMessageCode.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebRtcService {
    private final UserService userService;
    private final WebRtcMapper webRtcMapper;
    private final KurentoService kurentoService;
    private final StompMessagingService stompMessagingService;

    public void join(UUID roomUuid, UUID userUuid) {
        log.info("[ws] {} join in {}",userUuid,roomUuid);
        GetUserResponse user = userService.getUser(userUuid);
        List<GetUserResponse> users = userService.findAllUserInStudyRoomByStudyRoomUuid(roomUuid);

//        users.forEach(roomUser -> kurentoService.removeIncomingEndpoint(roomUser.getId(), userId));
//        kurentoService.removeIncomingEndpoint(userId);
//        kurentoService.removeOutgoingEndpoint(userId);

        kurentoService.createOutgoingEndpoint(roomUuid, userUuid,
                event -> {
                    final IceCandidate iceCandidate = event.getCandidate();
                    stompMessagingService.sendToUser(userUuid,
                            new OutputMessage<>(WEBRTC_SUCCESS_ADD_ICE_CANDIDATE,webRtcMapper.toDto(userUuid,iceCandidate))
                    );
                }
        );

        users.forEach(roomUser -> stompMessagingService.sendToUser(
                roomUser.getUserUuid(),
                new OutputMessage<>(WEBRTC_SUCCESS_ADDED_STUDYROOM_USER,user))
        );

        stompMessagingService.sendToUser(userUuid, new OutputMessage<>(WEBRTC_SUCCESS_GET_STUDYROOM_USERS,users));
    }

    public void offer(UUID roomUuid, UUID userUuid, UUID senderUuid, String sdpOffer) {
        kurentoService.removeIncomingEndpoint(userUuid, senderUuid);
        userService.getUser(userUuid);

        WebRtcEndpoint incomingEndpoint = userUuid == senderUuid
                ? kurentoService.getOutgoingEndpoint(userUuid)
                : kurentoService.createIncomingEndpoint(roomUuid,userUuid,senderUuid, event -> {
                    IceCandidate iceCandidate = event.getCandidate();
                    stompMessagingService.sendToUser(userUuid,
                    new OutputMessage<>(WEBRTC_SUCCESS_ADD_ICE_CANDIDATE,webRtcMapper.toDto(senderUuid,iceCandidate)));
                }
        );

        if (userUuid != senderUuid) {
            kurentoService.getOutgoingEndpoint(senderUuid).connect(incomingEndpoint);
        }

        String sdpAnswer = incomingEndpoint.processOffer(sdpOffer);
        stompMessagingService.sendToUser(userUuid, new OutputMessage<>(WEBRTC_SUCCESS_ANSWER,webRtcMapper.toDto(senderUuid,sdpAnswer)));

        incomingEndpoint.gatherCandidates();

    }
}
