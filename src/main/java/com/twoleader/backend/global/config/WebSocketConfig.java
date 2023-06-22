package com.twoleader.backend.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//  private final StompHandler stompHandler;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/topic");
    registry.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // the url is for Websocket handshake
    registry.addEndpoint("/signal").setAllowedOriginPatterns("*").withSockJS();
  }
//  @Override
//  public void configureClientInboundChannel(ChannelRegistration registration) {
//    // connect / disconnect 인터셉터
//    registration.interceptors(stompHandler);
//  }
}
