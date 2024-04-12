package com.ecommerce.user.infrastructure.mother;

import com.ecommerce.user.domain.valueobjects.NewUserDomain;
import com.ecommerce.shared.infrastructure.mother.FakerMother;

/**
 * Genera random data for Customer Model
 */
public class UserDomainMother {
    /*
     * First name max length
     */
    public static final int FIRSTNAME_MAX_LENGTH = 125;
    /**
     * Last name max length
     */
    public static final int LASTNAME_MAX_LENGTH = 125;
    /**
     * Email max length
     */
    public static final int EMAIL_MAX_LENGTH = 255;
    /**
     * Password max length
     */
    public static final int PASSWORD_MAX_LENGTH = 255;
    /**
     * Phone max length
     */
    public static final int PHONE_MAX_LENGTH = 40;

    /**
     * Generate a random Customer model without id and active
     * @return a customer model instance
     */
    public static NewUserDomain random() {
        return new NewUserDomain(
                FakerMother.getFaker().name().firstName(),
                FakerMother.getFaker().name().lastName(),
                FakerMother.getFaker().internet().emailAddress(),
                FakerMother.getFaker().internet().password(),
                FakerMother.getFaker().phoneNumber().phoneNumberInternational(),
                Boolean.TRUE
        );
    }

    /**
     * Generate a random Customer model without id and active
     * @return a customer model instance
     */
    public static NewUserDomain randomNewUser() {
        return new NewUserDomain(
            FakerMother.getFaker().name().firstName(),
            FakerMother.getFaker().name().lastName(),
            FakerMother.getFaker().internet().emailAddress(),
            FakerMother.getFaker().internet().password(),
            FakerMother.getFaker().phoneNumber().phoneNumberInternational(),
            Boolean.TRUE
        );
    }

    /**
     * Generate a customer with all properties nullables
     * @return a customer model instance
     */
    public static NewUserDomain nullableNewCustomer() {
        return new NewUserDomain(
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    /**
     * Generate a customer with invalids max length
     * @return a customer model instance
     */
    public static NewUserDomain invalidMaxLengthNewCustomer() {
        return new NewUserDomain(
                FakerMother.getFaker().lorem().characters(FIRSTNAME_MAX_LENGTH + 1),
                FakerMother.getFaker().lorem().characters(LASTNAME_MAX_LENGTH + 1),
                FakerMother.getFaker().lorem().characters(EMAIL_MAX_LENGTH + 1),
                FakerMother.getFaker().lorem().characters(PASSWORD_MAX_LENGTH + 1),
                FakerMother.getFaker().lorem().characters(PHONE_MAX_LENGTH + 1),
                Boolean.TRUE
        );
    }

    /**
     * Generate a random Customer model with invalid email
     * @return a customer model instance
     */
    public static NewUserDomain invalidEmailNewCustomer() {
        return new NewUserDomain(
                FakerMother.getFaker().name().firstName(),
                FakerMother.getFaker().name().lastName(),
                FakerMother.getFaker().internet().username(),
                FakerMother.getFaker().internet().password(),
                FakerMother.getFaker().phoneNumber().phoneNumberInternational(),
                Boolean.TRUE
        );
    }
}
