package com.amigopay.user.common.enums;

public enum ValidationMessage {

    // Nome
    FIRST_NAME_REQUIRED("validation.name.required"),
    FIRST_NAME_INVALID("validation.name.invalid"),
    FIRST_NAME_VALID("validation.name.valid"),

    LAST_NAME_REQUIRED("validation.lastname.required"),
    LAST_NAME_INVALID("validation.lastname.invalid"),

    // CPF
    CPF_REQUIRED("validation.cpf.required"),
    CPF_INVALID("validation.cpf.invalid"),
    CPF_ALREADY_EXISTS("validation.cpf.already_exists"),

    // E-mail
    EMAIL_REQUIRED("validation.email.required"),
    EMAIL_INVALID("validation.email.invalid"),
    EMAIL_ALREADY_EXISTS("validation.email.already_exists"),
    EMAIL_INVALID_FORMAT("validation.email.invalid_format"),
    EMAIL_MAX_LENGTH("validation.email.max_length"),

    // Data de nascimento
    BIRTHDATE_REQUIRED("validation.birthdate.required"),
    BIRTHDATE_INVALID("validation.birthdate.invalid"),

    // Senha
    PASSWORD_REQUIRED("validation.password.required"),
    PASSWORD_VALID("validation.password.valid"),

    // User
    USER_NOT_FOUND("validation.user.not_found"),

    // Internal error
    INTERNAL_ERROR("validation.internal.error");

    private final String key;

    ValidationMessage(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
