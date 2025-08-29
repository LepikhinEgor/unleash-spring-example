package ru.baldenna;

public record User(
        String usermame,
        Tariff tariff,
        Integer age
) {}
