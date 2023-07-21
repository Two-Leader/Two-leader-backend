package com.twoleader.backend.domain.chat.controll;


import com.twoleader.backend.domain.chat.dto.ChatMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@DirtiesContext
@ActiveProfiles(profiles = {"test"})
public class KafkaControllerTest {
    @MockBean
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    private BlockingQueue<ConsumerRecord<Integer, String>> records= new LinkedBlockingDeque<>();

//    @BeforeEach
    public void setUp() {
//        // Kafka consumer setup
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker);
        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        ContainerProperties containerProperties = new ContainerProperties("chatTopic");
        KafkaMessageListenerContainer<Integer, String> container = new KafkaMessageListenerContainer<>(cf, containerProperties);
        container.setupMessageListener((MessageListener<Integer, String>) records::add);
        container.start();
        ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
    }

    @Test
    public void testReceivingKafkaEvents() throws InterruptedException {
        ChatMessage chatMessage = ChatMessage.builder().roomUuid(UUID.randomUUID()).userId(1L).message("Test message").build();
        // Send the test ChatMessage
        kafkaTemplate.send("chatTopic", chatMessage);

        // Check that the message was received
        ConsumerRecord<Integer, String> received = records.poll(5, TimeUnit.SECONDS);
        assertThat(received).isNotNull();
        System.out.println(received);
        assertThat(received.value().getBytes()).isEqualTo("Test message");
    }
}
