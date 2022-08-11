package com.example.demo.producer;

import com.example.demo.entity.RunnerLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"${kafka-producer.topics.location}"},
        brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class RunnerLocationProducerIT {

    private static final String NAME = "Perla";
    private static final double LATITUDE = 22l;
    private static final double LONGITUDE = 11l;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;
    @Autowired
    private RunnerLocationProducer runnerLocationProducer;

    @Value("${kafka-producer.topics.location}")
    private String topic;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Consumer<String, String> consumer;


    @BeforeEach
    void setUp() throws Exception {
        ConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(
                KafkaTestUtils.consumerProps("my-test-groupId", "true", embeddedKafka),
        StringDeserializer::new, StringDeserializer::new);
        consumer = cf.createConsumer();
        embeddedKafka.consumeFromEmbeddedTopics(consumer, topic);
    }
    @Test
    void when_publishRunnerLocation_expectConsumedRunnerLocation() throws JsonProcessingException {
        RunnerLocation runnerLocation = new RunnerLocation(NAME, LATITUDE, LONGITUDE);
        runnerLocationProducer.send(runnerLocation);

        ConsumerRecord<String, String> record = KafkaTestUtils.getSingleRecord(consumer, topic);

        RunnerLocation consumed = objectMapper.readValue(record.value(), RunnerLocation.class);
        Assertions.assertEquals(NAME, consumed.getName());
        Assertions.assertEquals(LONGITUDE, consumed.getLongitude());
        Assertions.assertEquals(LATITUDE, consumed.getLatitude());

    }
}
