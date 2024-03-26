package com.perficient.shoppingcart.infrastructure.mother;

import net.datafaker.Faker;

/**
 * Generate random data using Faker library
 */
public class FakerMother {
    private static final Faker faker = new Faker();

    /**
     * Create a random hexadecimal with the defined length
     * @param maxLength the max length of the hex to generate
     * @return a string hexadecimal
     */
    public static String randomHex(int maxLength) {
        return faker.random().hex(maxLength);
    }

    /**
     * Create a random first name with max length restriction
     * @param maxLength the max length of the string to generate
     * @return a random string first name
     */
    public static String randomFirstname(int maxLength) {
        return reduceIfMaxLengthExceeded(faker.name().firstName(), maxLength);
    }

    /**
     * Create a random last name with max length restriction
     * @param maxLength the max length of the string to generate
     * @return a random string last name
     */
    public static String randomLastname(int maxLength) {
        return reduceIfMaxLengthExceeded(faker.name().lastName(), maxLength);
    }

    /**
     * Create a random email address
     * @return a random string email address
     */
    public static String randomEmail() {
        return faker.internet().emailAddress();
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
     * Reduce a text if the text length exceeded the max length parameter
     * @param text the text to check
     * @param maxLength the max length to verify
     * @return a string or sub string if the text length exceeded the max length parameter
     */
    private static String reduceIfMaxLengthExceeded(String text, int maxLength) {
        if (text.length() > maxLength) {
            int beginIndex = 0;
            return text.substring(beginIndex, maxLength);
        }

        return text;
    }
}
