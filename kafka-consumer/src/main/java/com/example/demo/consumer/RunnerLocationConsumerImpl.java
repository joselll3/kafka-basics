package com.example.demo.consumer;

import com.example.demo.entity.RunnerLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RunnerLocationConsumerImpl implements RunnerLocationConsumer{

    private static final Logger log = LoggerFactory.getLogger(RunnerLocationConsumerImpl.class);
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    @KafkaListener(topics = "t_location")
    public void listen(String message) throws JsonProcessingException {
        RunnerLocation runnerLocation = objectMapper.readValue(message, RunnerLocation.class);
        log.info("listen() : {}", runnerLocation);
    }

    @Override
    @KafkaListener(topics = "t_location", groupId = "spring-consumer-speed")
    public void listenSpeed(String message) throws JsonProcessingException {
        RunnerLocation runnerLocation = objectMapper.readValue(message, RunnerLocation.class);
        log.info("listenSpeed() : {}", runnerLocation);
    }
}
