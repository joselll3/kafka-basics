package com.example.demo.producer;

import com.example.demo.entity.RunnerLocation;

public interface RunnerLocationProducer {

    void send(RunnerLocation runnerLocation);
}
