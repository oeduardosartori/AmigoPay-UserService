package com.amigopay.user.messaging.producer;

import com.amigopay.events.UserCreatedEvent;

public interface UserEventPublisher {
    void publishUserCreated(UserCreatedEvent event);
}
