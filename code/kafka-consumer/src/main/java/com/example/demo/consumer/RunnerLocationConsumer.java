package com.example.demo.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface RunnerLocationConsumer {

    void listen(String message) throws JsonProcessingException;

    void listenSpeed(String message) throws JsonProcessingException;
}
