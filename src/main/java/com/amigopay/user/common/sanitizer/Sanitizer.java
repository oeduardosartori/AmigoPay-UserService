package com.amigopay.user.common.sanitizer;

public interface Sanitizer<T> {
    T sanitizer(T input);
}
