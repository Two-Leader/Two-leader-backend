package com.twoleader.backend.global.config.kafka;

import com.twoleader.backend.domain.chat.dto.ChatMessage;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class ProducerConfig {

  @Value(value = "${spring.kafka.producer.bootstrap-servers}")
  private String bootstrapServer;

  // Kafka ProducerFactory를 생성하는 Bean 메서드
  @Bean
  public ProducerFactory<String, ChatMessage> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigurations());
  }

  // Kafka Producer 구성을 위한 설정값들을 포함한 맵을 반환하는 메서드
  @Bean
  public Map<String, Object> producerConfigurations() {
    HashMap<String, Object> configurations = new HashMap<>();
    configurations.put(
        org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
    configurations.put(
        org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        StringSerializer.class);
    configurations.put(
        org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        JsonSerializer.class);
    return configurations;
  }

  // KafkaTemplate을 생성하는 Bean 메서드
  @Bean
  public KafkaTemplate<String, ChatMessage> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
}
