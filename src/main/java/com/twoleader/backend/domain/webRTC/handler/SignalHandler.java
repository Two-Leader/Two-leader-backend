// package com.twoleader.backend.webRTC.handler;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.twoleader.backend.domain.studyRoom.service.StudyRoomService;
// import com.twoleader.backend.domain.user.service.UserService;
// import com.twoleader.backend.webRTC.dto.WebSocketMessage;
// import java.io.IOException;
// import java.util.*;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.stereotype.Component;
// import org.springframework.web.socket.CloseStatus;
// import org.springframework.web.socket.TextMessage;
//// import org.springframework.web.socket.WebSocketMessage;
// import org.springframework.web.socket.WebSocketSession;
// import org.springframework.web.socket.handler.TextWebSocketHandler;
//
// @Component
// @Slf4j
// @RequiredArgsConstructor
// public class SignalHandler extends TextWebSocketHandler {
//
//  private final StudyRoomService studyRoomService;
//  private final UserService userService;
//
//  private final ObjectMapper objectMapper = new ObjectMapper();
//
//  // session id to room mapping
//  private Map<UUID, WebSocketSession> userSessions = new HashMap<>();
//
//  // message types, used in signalling:
//  // text message
//  //    private static final String MSG_TYPE_TEXT = "text";
//  // SDP Offer message
//  //    private static final String MSG_TYPE_OFFER = "offer";
//  // SDP Answer message
//  //    private static final String MSG_TYPE_ANSWER = "answer";
//  // New ICE Candidate message
//  //    private static final String MSG_TYPE_ICE = "ice";
//  // join room data message
//  private static final String MSG_TYPE_JOIN = "join";
//  // leave room data message
//  //    private static final String MSG_TYPE_LEAVE = "leave";
//
//  // server uuid
//  private static final UUID SERVER = UUID.randomUUID();
//
//  @Override
//  public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
//    log.debug("[ws] Session has been closed with status {}", status);
//    for (UUID key : userSessions.keySet()) {
//      if (session.equals(userSessions.get(key))) {
//        userSessions.remove(key);
//        userService.deleteUserByUuid(key);
//        break;
//      }
//    }
//  }
//
//  @Override
//  public void afterConnectionEstablished(final WebSocketSession session) {
//    log.debug("[ws] {} has been opened with status", session);
//    sendMessage(session, WebSocketMessage.builder().from(SERVER).type(MSG_TYPE_JOIN).build());
//  }
//
//  @Override
//  protected void handleTextMessage(final WebSocketSession session, final TextMessage textMessage)
// {
//    // a message has been received
//    try {
//      WebSocketMessage message =
//          objectMapper.readValue(textMessage.getPayload(), WebSocketMessage.class);
//      log.debug("[ws] Message of {} type from {} received", message.getType(), message.getFrom());
//      //      String user_uuid = message.getFrom(); // origin of the message
//      //      String data = message.getData(); // payload
//
//      //      StudyRoom studyRoom;
//      switch (message.getType()) {
//          //                // text message from client has been received
//          //                case MSG_TYPE_TEXT:
//          //                    logger.debug("[ws] Text message: {}", message.getData());
//          //                    // message.data is the text sent by client
//          //                    // process text message if needed
//          //                    break;
//          //
//          //                 process signal received from client
//          //                case MSG_TYPE_OFFER:
//          //                case MSG_TYPE_ANSWER:
//          //                case MSG_TYPE_ICE:
//          //                    Object candidate = message.getCandidate();
//          //                    Object sdp = message.getSdp();
//          //                    logger.debug("[ws] Signal: {}",
//          //                            candidate != null
//          //                                    ? candidate.toString().substring(0, 64)
//          //                                    : sdp.toString().substring(0, 64));
//          //
//          //                    Room rm = sessionIdToRoomMap.get(session.getId());
//          //                    if (rm != null) {
//          //                        Map<String, WebSocketSession> clients =
//          // roomService.getClients(rm);
//          //                        for(Map.Entry<String, WebSocketSession> client :
//          // clients.entrySet())  {
//          //                            // send messages to all clients except current user
//          //                            if (!client.getKey().equals(userName)) {
//          //                                // select the same type to resend signal
//          //                                sendMessage(client.getValue(),
//          //                                        new WebSocketMessage(
//          //                                                userName,
//          //                                                message.getType(),
//          //                                                data,
//          //                                                candidate,
//          //                                                sdp));
//          //                            }
//          //                        }
//          //                    }
//          //                    break;
//
//          // identify user and their opponent
//        case MSG_TYPE_JOIN:
//          log.debug("[ws] {} has joined Room: #{}", message.getFrom(), message.getData());
//          userSessions.put(message.getFrom(), session);
//          break;
//
//          //                case MSG_TYPE_LEAVE:
//          //                    // message data contains connected room id
//          //                    logger.debug("[ws] {} is going to leave Room: #{}", userName,
//          // message.getData());
//          //                    // room id taken by session id
//          //                    room = sessionIdToRoomMap.get(session.getId());
//          //                    // remove the client which leaves from the Room clients list
//          //                    Optional<String> client =
//          // roomService.getClients(room).entrySet().stream()
//          //                            .filter(entry -> Objects.equals(entry.getValue().getId(),
//          // session.getId()))
//          //                            .map(Map.Entry::getKey)
//          //                            .findAny();
//          //                    client.ifPresent(c -> roomService.removeClientByName(room, c));
//          //                    break;
//
//          // something should be wrong with the received message, since it's type is
// unrecognizable
//        default:
//          log.debug("[ws] Type of the received message {} is undefined!", message.getType());
//          // handle this if needed
//      }
//
//    } catch (IOException e) {
//      log.debug("An error occured: {}", e.getMessage());
//    }
//  }
//
//  private void sendMessage(WebSocketSession session, WebSocketMessage message) {
//    try {
//      String json = objectMapper.writeValueAsString(message);
//      session.sendMessage(new TextMessage(json));
//    } catch (IOException e) {
//      log.debug("An error occured: {}", e.getMessage());
//    }
//  }
// }
