package com.twoleader.backend.webRTC.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twoleader.backend.domain.user.entity.User;
import com.twoleader.backend.domain.user.repository.UserRepository;
import com.twoleader.backend.webRTC.dto.WebSocketMessage;
import java.io.IOException;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
// import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
@RequiredArgsConstructor
public class SignalHandler extends TextWebSocketHandler {

  private final UserRepository userRepository;
  private final ObjectMapper objectMapper = new ObjectMapper();
  // session id to room mapping
  //  private Map<String, Map<String, WebSocketSession>> sessionIdToRoomMap = new HashMap<>();

  private static final UUID SERVER_UUID = UUID.randomUUID();
  private Map<UUID, WebSocketSession> userSessions = new HashMap<>();
  // message types, used in signalling:
  // text message
  //    private static final String MSG_TYPE_TEXT = "text";
  // SDP Offer message
  private static final String MSG_TYPE_OFFER = "offer";
  // SDP Answer message
  private static final String MSG_TYPE_ANSWER = "answer";
  // New ICE Candidate message
  private static final String MSG_TYPE_ICE = "ice";
  // join room data message
  private static final String MSG_TYPE_JOIN = "join";

  // leave room data message
  //    private static final String MSG_TYPE_LEAVE = "leave";

  @Override
  public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
    log.debug(
        "[ws] Session has been closed with status [session : {}, status : {}]", session, status);
  }

  @Override
  public void afterConnectionEstablished(final WebSocketSession session) {
    // webSocket has been opened, send a message to the client
    // when data field contains 'true' value, the client starts negotiating
    // to establish peer-to-peer connection, otherwise they wait for a counterpart
    log.debug("[ws] Session has been Open [session : {}]", session);

    sendMessage(
        session,
        WebSocketMessage.builder().from(SERVER_UUID).type(MSG_TYPE_JOIN).build());

  }

  @Override
  protected void handleTextMessage(final WebSocketSession session, final TextMessage textMessage) {
    // a message has been received
    try {
      WebSocketMessage message =
          objectMapper.readValue(textMessage.getPayload(), WebSocketMessage.class);
      log.debug("[ws] Message of {} type from {} received", message.getType(), message.getFrom());
      //      UUID from = message.getFrom(); // origin of the message
      //      UUID data = message.getData(); // payload
      //      UUID room_uuid = message.getRoom_uuid()

      //      StudyRoom studyRoom;
      switch (message.getType()) {
          //                // text message from client has been received
          //                case MSG_TYPE_TEXT:
          //                    logger.debug("[ws] Text message: {}", message.getData());
          //                    // message.data is the text sent by client
          //                    // process text message if needed
          //                    break;
          //
          //                 process signal received from client
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
          log.info("[ws] users : {}",users.toString());
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
          //            Room rm = sessionIdToRoomMap.get(session.getId());
          //            if (rm != null) {
          //                Map<String, WebSocketSession> clients =
          // roomService.getClients(rm);
          //                for(Map.Entry<String, WebSocketSession> client :
          // clients.entrySet())  {
          //                    // send messages to all clients except current user
          //                    if (!client.getKey().equals(userName)) {
          //                        // select the same type to resend signal
          //                        sendMessage(client.getValue(),
          //                                new WebSocketMessage(
          //                                        userName,
          //                                        message.getType(),
          //                                        data,
          //                                        candidate,
          //                                        sdp));
          //                    }
          //                }
          //            }
          break;

          // identify user and their opponent
        case MSG_TYPE_JOIN:
          // message.data contains connected room id
          log.debug("[ws] {} has joined Room: #{}", message.getFrom(), message.getData());
          //          GetStudyRoomResponse response =
          // studyRoomService.findStudyRoomByRoom_uuid(message.getData());
          //          StudyRoom studyRoom =
          // studyRoomRepository.findStudyRoomByRoom_uuid(message.getData()).orElseThrow(NotFoundStudyRoom::new);
          userSessions.put(message.getFrom(), session);
          //          userService.createUser()
          //          userRepository.save()
          // add client to the Room clients list
          //          sessionIdToRoomMap.get(studyRoom.getRoom_uuid()).put(user_uuid, session);

          break;

          //                case MSG_TYPE_LEAVE:
          //                    // message data contains connected room id
          //                    logger.debug("[ws] {} is going to leave Room: #{}", userName,
          // message.getData());
          //                    // room id taken by session id
          //                    room = sessionIdToRoomMap.get(session.getId());
          //                    // remove the client which leaves from the Room clients list
          //                    Optional<String> client =
          // roomService.getClients(room).entrySet().stream()
          //                            .filter(entry -> Objects.equals(entry.getValue().getId(),
          // session.getId()))
          //                            .map(Map.Entry::getKey)
          //                            .findAny();
          //                    client.ifPresent(c -> roomService.removeClientByName(room, c));
          //                    break;

          // something should be wrong with the received message, since it's type is unrecognizable
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
      log.info("[ws] sendMessage , json : {}",json);
      session.sendMessage(new TextMessage(json));
    } catch (IOException e) {
      log.error("An error occured: {}", e.getMessage());
    }
  }
}
