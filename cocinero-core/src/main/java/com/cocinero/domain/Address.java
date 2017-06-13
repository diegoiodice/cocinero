package com.cocinero.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class Address {

    private final String id;
    private final String postCode;
    private final String addressLine1;
    private final String addressLine2;
    private final String city;
    private final String country;
}
