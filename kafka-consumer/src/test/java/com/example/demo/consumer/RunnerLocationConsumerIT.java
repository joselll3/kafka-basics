package com.example.demo.consumer;

import com.example.demo.entity.RunnerLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.waitAtMost;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"t_location"},
        brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class RunnerLocationConsumerIT {
    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    @SpyBean
    private RunnerLocationConsumer runnerLocationConsumer;

    @Captor
    ArgumentCaptor<String> listenArgumentCaptor;

    @Captor
    ArgumentCaptor<String> listenSpeedArgumentCaptor;

    @Test
    void when_receiveMessage_expectBothListenerListen() throws JsonProcessingException, InterruptedException {
        String valueString = objectMapper.writeValueAsString(new RunnerLocation("Jose", 8L, 2l));
        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(senderProps);
        kafkaProducer.send(new ProducerRecord<>("t_location", valueString));

        waitAtMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            verify(runnerLocationConsumer).listen(listenSpeedArgumentCaptor.capture());
            verify(runnerLocationConsumer).listenSpeed(listenSpeedArgumentCaptor.capture());
        });
    }

}
