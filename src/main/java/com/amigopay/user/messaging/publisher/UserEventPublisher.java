package com.amigopay.user.messaging.publisher;

import com.amigopay.user.common.enums.ValidationMessage;
import com.amigopay.user.exception.BusinessException;
import com.amigopay.user.messaging.events.UserCreatedEventFactory;
import com.amigopay.user.messaging.producer.UserEventProducer;
import com.amigopay.user.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventPublisher {

    private final UserEventProducer eventProducer;

    public void publishUserCreated(User user) {
        try {
            var event = UserCreatedEventFactory.from(user);
            eventProducer.sendUserCreatedEvent(event);
        } catch (BusinessException ex) {
            log.error("[Kafka] Failed to publish UserCreatedEvent for userId={}: {}", user.getId(), ex.getMessage(), ex);
            throw new BusinessException(ValidationMessage.FAILED_PUBLISH_EVENT.key());
        }
    }
}
