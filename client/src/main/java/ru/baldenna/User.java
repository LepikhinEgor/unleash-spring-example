package ru.baldenna;

public record User(
        String usermame,
        UserType type,
        Integer age
) {}
