package com.amigopay.user.messaging.producer;

import com.amigopay.events.UserCreatedEvent;

public interface UserEventProducer {
    void sendUserCreatedEvent(UserCreatedEvent event);
}
