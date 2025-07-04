package com.amigopay.user.messaging.events;

import com.amigopay.events.UserCreatedEvent;
import com.amigopay.user.user.entity.User;

public class UserCreatedEventFactory {

    private UserCreatedEventFactory() {}

    public static UserCreatedEvent from(User user) {
        return new UserCreatedEvent(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}
