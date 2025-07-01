package com.amigopay.user.common.util;

import com.amigopay.user.common.enums.ValidationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageResolver {

    private final MessageSource messageSource;

    public String resolve(ValidationMessage key) {
        return messageSource.getMessage(key.key(), null, Locale.getDefault());
    }

    public String resolve(ValidationMessage key, Object... args) {
        return messageSource.getMessage(key.key(), args, Locale.getDefault());
    }
}
