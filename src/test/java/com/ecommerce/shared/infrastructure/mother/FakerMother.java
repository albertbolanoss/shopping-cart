package com.ecommerce.shared.infrastructure.mother;

import net.datafaker.Faker;

/**
 * Generate random data using Faker library
 */
public class FakerMother {
    private static Faker _faker;
    private FakerMother() { }

    private static class FakerHelper {
        private static final Faker _faker = new Faker();
    }
    public static Faker getFaker()
    {
        return FakerHelper._faker;
    }

}
