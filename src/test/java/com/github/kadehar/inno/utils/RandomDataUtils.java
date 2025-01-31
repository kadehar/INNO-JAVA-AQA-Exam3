package com.github.kadehar.inno.utils;

import com.github.javafaker.Faker;

public class RandomDataUtils {

    private static final Faker faker = new Faker();

    public static String randomCompanyName() {
        return faker.company().name();
    }

    public static String randomCompanyDescription() {
        return faker.company().catchPhrase();
    }

    public static String randomNickname() {
        return faker.name().username();
    }

    public static String randomFirstName() {
        return faker.name().firstName();
    }

    public static String randomLastName() {
        return faker.name().lastName();
    }

    public static String randomMiddleName() {
        return randomFirstName();
    }

    public static String randomEmail() {
        return faker.internet().emailAddress();
    }

    public static String randomUrl() {
        return faker.internet().url();
    }

    public static String randomPhone() {
        return "7999" + faker.phoneNumber().subscriberNumber(7);
    }
}