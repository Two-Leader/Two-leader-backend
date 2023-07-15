package com.twoleader.backend.global.config;

import com.twoleader.backend.global.config.kafka.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
  private final StompHandler stompHandler;

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
  //    @Override
  //    public void configureClientInboundChannel(ChannelRegistration registration) {
  //      // connect / disconnect 인터셉터
  //      registration.interceptors(stompHandler);
  //    }

  // STOMP에서 64KB 이상의 데이터 전송을 못하는 문제 해결
  //  @Override
  //  public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
  //    registry.setMessageSizeLimit(160 * 64 * 1024);
  //    registry.setSendTimeLimit(100 * 10000);
  //    registry.setSendBufferSizeLimit(3 * 512 * 1024);
  //  }
}
