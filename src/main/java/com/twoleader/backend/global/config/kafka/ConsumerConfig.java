package com.twoleader.backend.global.config.kafka;

import com.twoleader.backend.domain.chat.dto.ChatMessage;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class ConsumerConfig {

  @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
  private String bootstrapServer;

  // KafkaListener 컨테이너 팩토리를 생성하는 Bean 메서드
  @Bean
  ConcurrentKafkaListenerContainerFactory<String, ChatMessage> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, ChatMessage> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }

  // Kafka ConsumerFactory를 생성하는 Bean 메서드
  @Bean
  public ConsumerFactory<String, ChatMessage> consumerFactory() {
    JsonDeserializer<ChatMessage> deserializer = new JsonDeserializer<>();
    // 패키지 신뢰 오류로 인해 모든 패키지를 신뢰하도록 작성
    deserializer.addTrustedPackages("*");

    // Kafka Consumer 구성을 위한 설정값들을 설정
    Map<String, Object> configurations = new HashMap<>();

    configurations.put(
        org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
    configurations.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, "adopt");
    configurations.put(
        org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class);
    configurations.put(
        org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        deserializer);
    configurations.put(
        org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

    return new DefaultKafkaConsumerFactory<>(
        configurations, new StringDeserializer(), deserializer);
  }
}
