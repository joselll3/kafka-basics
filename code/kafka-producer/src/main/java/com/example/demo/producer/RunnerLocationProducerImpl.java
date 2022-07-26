package com.example.demo.producer;

import com.example.demo.entity.RunnerLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.slf4j.SLF4JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RunnerLocationProducerImpl implements RunnerLocationProducer {

    private static final Logger LOG = LoggerFactory.getLogger(RunnerLocationProducerImpl.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void send(RunnerLocation runnerLocation) {
        try {
            String json = objectMapper.writeValueAsString(runnerLocation);
            LOG.info("Sending message {}", json);
            kafkaTemplate.send("t_location", runnerLocation.getName(), json);
        } catch (JsonProcessingException e) {
            LOG.error("Error send", e);
        }
    }
}
