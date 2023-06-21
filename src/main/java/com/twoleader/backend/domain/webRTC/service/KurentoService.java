package com.twoleader.backend.domain.webRTC.service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kurento.client.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KurentoService {
  private final KurentoClient kurentoClient;

  private final ConcurrentMap<UUID, MediaPipeline> roomMediaPipelines = new ConcurrentHashMap<>();
  private final ConcurrentMap<UUID, WebRtcEndpoint> outgoingEndpoints = new ConcurrentHashMap<>();
  private final ConcurrentMap<UUID, ConcurrentMap<UUID, WebRtcEndpoint>> incomingEndpoints =
      new ConcurrentHashMap<>();

  public void createOutgoingEndpoint(
      UUID roomUuid, UUID userUuid, EventListener<IceCandidateFoundEvent> listener) {
    log.info("Create [OUTGOING_ENDPOINT] for identifier [{}]", userUuid);
    MediaPipeline mediaPipeline = getMediaPipeline(roomUuid);

    WebRtcEndpoint outgoingEndpoint = new WebRtcEndpoint.Builder(mediaPipeline).build();
    outgoingEndpoint.addIceCandidateFoundListener(listener);

    outgoingEndpoints.put(userUuid, outgoingEndpoint);
  }

  public WebRtcEndpoint createIncomingEndpoint(
      UUID roomUuid,
      UUID userUuid,
      UUID senderUuid,
      EventListener<IceCandidateFoundEvent> listener) {
    if (!incomingEndpoints.containsKey(userUuid)) {
      incomingEndpoints.put(userUuid, new ConcurrentHashMap<>());
    }
    log.info("Create [INCOMING_ENDPOINT] for identifier [{}]", userUuid + "-" + senderUuid);
    MediaPipeline mediaPipeline = getMediaPipeline(roomUuid);
    WebRtcEndpoint incomingEndpoint = new WebRtcEndpoint.Builder(mediaPipeline).build();
    incomingEndpoint.addIceCandidateFoundListener(listener);

    incomingEndpoints.get(userUuid).put(senderUuid, incomingEndpoint);

    return incomingEndpoint;
  }

  public void removeIncomingEndpoint(UUID userUuid, UUID senderUuid) {
    if (incomingEndpoints.containsKey(userUuid)
        && incomingEndpoints.get(userUuid).containsKey(senderUuid)) {
      log.info("Release [INCOMING_ENDPOINT] for identifier [{}]", userUuid + "-" + senderUuid);
      incomingEndpoints.get(userUuid).remove(senderUuid).release();
    }
  }

  public void removeIncomingEndpoint(UUID userUuid) {
    if (incomingEndpoints.containsKey(userUuid)) {
      incomingEndpoints
          .remove(userUuid)
          .forEach(
              (user, incomingEndpoint) -> {
                log.info("Release [INCOMING_ENDPOINT] for identifier [{}]", user);
                incomingEndpoint.release();
              });
    }
  }

  public WebRtcEndpoint getOutgoingEndpoint(UUID userUuid) {
    return outgoingEndpoints.get(userUuid);
  }

  private MediaPipeline getMediaPipeline(final UUID roomUuid) {
    // if There is no mediaPipeline of room create mediaPipeline
    if (!roomMediaPipelines.containsKey(roomUuid)) {
      log.info("Create [ROOM_PIPELINE] for identifier [{}]", roomUuid);
      roomMediaPipelines.put(roomUuid, kurentoClient.createMediaPipeline());
    }

    return roomMediaPipelines.get(roomUuid);
  }
}
