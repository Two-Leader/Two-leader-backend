package com.twoleader.backend.global.config.kafka;

import com.twoleader.backend.domain.chat.dto.response.ChatMessage;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
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
public class KafkaProducerConfig {

  @Value(value = "${spring.kafka.producer.bootstrap-servers}")
  private String bootstrapServer;

  @Bean
  public KafkaTemplate<String, ChatMessage> kafkaProducerTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  public ProducerFactory<String, ChatMessage> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigurations());
  }

  public Map<String, Object> producerConfigurations() {
    HashMap<String, Object> configurations = new HashMap<>();
    configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
    configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return configurations;
  }
}
