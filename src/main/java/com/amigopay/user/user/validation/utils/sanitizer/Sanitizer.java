package com.amigopay.user.user.validation.utils.sanitizer;

public interface Sanitizer<T> {
    T sanitizer(T input);
}
