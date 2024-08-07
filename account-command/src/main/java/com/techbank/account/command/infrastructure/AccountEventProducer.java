package com.techbank.account.command.infrastructure;

import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.producer.EventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventProducer implements EventProducer {
    private final KafkaTemplate<String,Object> kafkaTemplate;
    @Override
    public void produce(String topic, BaseEvent payload) {
        this.kafkaTemplate.send(topic, payload);
    }
}
