package com.twoleader.backend.webRTC.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.repository.UserRepository;
import com.twoleader.backend.webRTC.dto.WebSocketMessage;
import java.io.IOException;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
@RequiredArgsConstructor
public class SignalHandler extends TextWebSocketHandler {

  private final UserRepository userRepository;
  private final ObjectMapper objectMapper = new ObjectMapper();

  private static final UUID SERVER_UUID = UUID.randomUUID();
  private Map<UUID, WebSocketSession> userSessions = new HashMap<>();

  // SDP Offer message
  private static final String MSG_TYPE_OFFER = "offer";
  // SDP Answer message
  private static final String MSG_TYPE_ANSWER = "answer";
  // New ICE Candidate message
  private static final String MSG_TYPE_ICE = "ice";
  // join room data message
  private static final String MSG_TYPE_JOIN = "join";

  // leave room data message
  private static final String MSG_TYPE_LEAVE = "leave";

  @Override
  public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
    log.debug(
        "[ws] Session has been closed with status [session : {}, status : {}]", session, status);
    log.debug("[ws] before userSessions : {}", userSessions);
    UUID user_uuid = null;
    for (UUID key : userSessions.keySet()) {
      if (userSessions.get(key).equals(session)) {
        user_uuid = key;
        userSessions.remove(key);
        break;
      }
    }
    log.debug("[ws] after userSessions : {}, user_uuid : {}", userSessions, user_uuid);
    userRepository.deleteByUser_uuid(user_uuid);
  }

  @Override
  public void afterConnectionEstablished(final WebSocketSession session) {
    // webSocket has been opened, send a message to the client
    // when data field contains 'true' value, the client starts negotiating
    // to establish peer-to-peer connection, otherwise they wait for a counterpart
    log.debug("[ws] Session has been Open [session : {}]", session);

    sendMessage(session, WebSocketMessage.builder().from(SERVER_UUID).type(MSG_TYPE_JOIN).build());
  }

  @Override
  protected void handleTextMessage(final WebSocketSession session, final TextMessage textMessage) {
    // a message has been received
    try {
      WebSocketMessage message =
          objectMapper.readValue(textMessage.getPayload(), WebSocketMessage.class);
      log.debug("[ws] Message of {} type from {} received", message.getType(), message.getFrom());
      log.debug("[ws] Message : {}", message);

      switch (message.getType()) {
        case MSG_TYPE_OFFER:
        case MSG_TYPE_ANSWER:
        case MSG_TYPE_ICE:
          Object candidate = message.getCandidate();
          Object sdp = message.getSdp();
          log.debug(
              "[ws] Signal: {}",
              candidate != null
                  ? candidate.toString().substring(0, 64)
                  : sdp.toString().substring(0, 64));
          List<User> users = userRepository.findAllByRoom_uuid(message.getData());
          log.info("[ws] users : {}", users.toString());
          for (User user : users) {
            if (!message.getFrom().equals(user.getUser_uuid())) {
              sendMessage(
                  userSessions.get(user.getUser_uuid()),
                  WebSocketMessage.builder()
                      .from(message.getFrom())
                      .type(message.getType())
                      .data(message.getData())
                      .sdp(message.getSdp())
                      .candidate(message.getCandidate())
                      .build());
            }
          }
          break;

          // identify user and their opponent
        case MSG_TYPE_JOIN:
          // message.data contains connected room id
          log.debug("[ws] {} has joined Room: #{}", message.getFrom(), message.getData());
          userSessions.put(message.getFrom(), session);
          break;

        case MSG_TYPE_LEAVE:
          // message data contains connected room id
          log.debug("[ws] {} is going to leave Room: #{}", message.getFrom(), message.getData());

          break;

          // something should be wrong wit
        default:
          log.debug("[ws] Type of the received message {} is undefined!", message.getType());
          // handle this if needed
      }

    } catch (IOException e) {
      log.debug("An error occured: {}", e.getMessage());
    }
  }

  private void sendMessage(WebSocketSession session, WebSocketMessage message) {
    try {
      String json = objectMapper.writeValueAsString(message);
      log.info("[ws] sendMessage , json : {}", json);
      session.sendMessage(new TextMessage(json));
    } catch (IOException e) {
      log.error("An error occured: {}", e.getMessage());
    }
  }
}
