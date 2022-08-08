package com.example.demo.scheduler;

import com.example.demo.entity.RunnerLocation;
import com.example.demo.producer.RunnerLocationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@ConditionalOnProperty(name="kafka-producer.scheduler.enabled", matchIfMissing = true)
public class RunnerLocationScheduler {

    private final List<String> RUNNER_NAMES = List.of("Natalia", "Jose", "Noelia", "Sergio");
    private final Random random = new Random();
    @Autowired
    private RunnerLocationProducer runnerLocationProducer;

    @Scheduled(fixedRate = 3000)
    public void generateRunnersLocation() {
        RUNNER_NAMES.forEach( runner -> {
        RunnerLocation runnerLocation = new RunnerLocation(runner, random.nextDouble(90),
                random.nextDouble(120));
        runnerLocationProducer.send(runnerLocation);
        }
        );
    }
}