package com.perficient.shoppingcart.infrastructure.mother;

import net.datafaker.Faker;
import net.datafaker.providers.base.Commerce;

/**
 * Generate random data using Faker library
 */
public class FakerMother {
    public static final Faker faker = new Faker();

    /**
     * Create a random hexadecimal with the defined length
     * @param maxLength the max length of the hex to generate
     * @return a string hexadecimal
     */
    public static String randomHex(int maxLength) {
        return faker.random().hex(maxLength);
    }

    /**
     * Create a random first name
     * @return a random string first name
     */
    public static String randomFirstname() {
        return faker.name().firstName();
    }

    /**
     * Create a random last name
     * @return a random string last name
     */
    public static String randomLastname() {
        return faker.name().lastName();
    }

    /**
     * Create a random email address
     * @return a random string email address
     */
    public static String randomEmail() {
        return faker.internet().emailAddress();
    }

    /**
     * Create a random username
     * @return a random string username
     */
    public static String randomUsername() {
        return faker.internet().username();
    }

    /**
     * Create a random phone number
     * @return a random string phone number
     */
    public static String randomInternationalPhoneNumber() {
        return faker.phoneNumber().phoneNumberInternational();
    }

    /**
     * Create a random phone number
     * @return a random string phone number
     */
    public static String randomPassword() {
        return faker.internet().password();
    }

    /**
     * Generate a text with a specify length
     * @param textLength the text length to generate
     * @return a string text
     */
    public static String randomText(int textLength) {
        return faker.lorem().characters(textLength);
    }

}
