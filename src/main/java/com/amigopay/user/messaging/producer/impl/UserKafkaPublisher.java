package com.amigopay.user.messaging.producer.impl;

import com.amigopay.events.UserCreatedEvent;
import com.amigopay.user.messaging.producer.UserEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserKafkaPublisher implements UserEventProducer {

    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    @Override
    public void sendUserCreatedEvent(UserCreatedEvent event) {
        kafkaTemplate.send("user.created", event.id().toString(), event);
        log.info("[Kafka] Sent UserCreatedEvent for userId={}", event.id());
    }
}
