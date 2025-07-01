package com.amigopay.user.messaging.producer;

import com.amigopay.user.messaging.event.UserCreatedEvent;

public interface UserEventPublisher {
    void publishUserCreated(UserCreatedEvent event);
}
