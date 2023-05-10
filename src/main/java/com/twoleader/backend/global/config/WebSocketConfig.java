package com.twoleader.backend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/topic");
    registry.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    //the url is for Websocket handshake
    registry.addEndpoint("/signal")
            .setAllowedOriginPatterns("*")
            .withSockJS();
  }

//  @Override
//  public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//    registration.setMessageSizeLimit(160 * 64 * 1024);    // Max incoming message size, default : 64 * 1024
//    registration.setSendTimeLimit(20 * 10000);            // default : 10 * 10000
//    registration.setSendBufferSizeLimit(10 * 512 * 1024); // Max outgoing buffer size, default : 512 * 1024
//  }
}
