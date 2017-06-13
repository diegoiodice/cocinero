package com.cocinero.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserAddress {

    private final Address address;
    private final String telephone;
    private final String alias;
}
