package com.amigopay.user.messaging.producer.impl;

import com.amigopay.events.UserCreatedEvent;
import com.amigopay.user.messaging.producer.UserEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserKafkaPublisher implements UserEventPublisher {

    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    @Override
    public void publishUserCreated(UserCreatedEvent event) {
        kafkaTemplate.send("user.created", event.id().toString(), event);
    }
}
