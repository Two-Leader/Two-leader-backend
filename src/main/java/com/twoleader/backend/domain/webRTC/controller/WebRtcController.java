package com.twoleader.backend.domain.webRTC.controller;

import com.twoleader.backend.domain.webRTC.service.WebRtcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebRtcController {
//  private final SimpMessagingTemplate simpMessagingTemplate;
  private final WebRtcService webRtcService;
  @MessageMapping("/join/{roomUuid}")
  public void joinMessage(@Payload UUID userUuid, @DestinationVariable("roomUuid") UUID roomUuid){
    log.debug("[ws] joinMessage : user {}, roomUuid {}", userUuid.toString(), roomUuid.toString());
    webRtcService.join(roomUuid,userUuid);
  }

//  @MessageMapping("/offer/{roomUuid}")
//  public void offerMessage(@Payload OfferRequestMessage message, @DestinationVariable("roomUuid") String roomUuid) {
//    log.debug("[ws] offerMessage : user {}, roomUuid {}", message.getFrom(), roomUuid);
//    simpMessagingTemplate.convertAndSend("/topic/" + roomUuid, message);
//  }
//
//  @MessageMapping("/answer/{roomUuid}")
//  public void answerMessage(@Payload AnswerRequestMessage message, @DestinationVariable("roomUuid") String roomUuid){
//    log.debug("[ws] answerMessage : user {}, roomUuid {}", message.getFrom(), roomUuid);
//    simpMessagingTemplate.convertAndSend("/topic/"+roomUuid,message);
//  }
//
//  @MessageMapping("/ice/{roomUuid}")
//  public void iceCandidateMessage(@Payload IceCandidateRequestMessage message, @DestinationVariable("roomUuid") String roomUuid){
//    log.debug("[ws] answerMessage : user {}, roomUuid {}", message.getFrom(), roomUuid);
//    simpMessagingTemplate.convertAndSend("/topic/"+roomUuid,message);
//  }
}
