package com.ecommerce.customer.domain.valueobjects;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CustomerReqFilterDomain {
    /**
     * First name max length
     */
    private static final int FIRSTNAME_MAX_LENGTH = 125;
    /**
     * Last name max length
     */
    private static final int LASTNAME_MAX_LENGTH = 125;
    /**
     * Email max length
     */
    private static final int EMAIL_MAX_LENGTH = 255;


    private final @Size(max = FIRSTNAME_MAX_LENGTH) String firstName;
    /**
     * The customer last name
     */
    private final @Size(max = LASTNAME_MAX_LENGTH) String lastName;
    /**
     * The customer email
     */
    private final @Size(max = EMAIL_MAX_LENGTH) String email;

    /**
     * The page number or offset
     */
    private int pageNumber;

    /**
     * The page size or limit
     */
    private int pageSize;

    /**
     * The sort of the query
     */
    private List<String> sort;

}
