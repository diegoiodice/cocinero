package com.cocinero.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(of = {"id"})
public class UserProfile {

    private final String id;
    private final String names;
    private final String lastNames;
    private final Date dateOfBirth;
    private final String telephone;
    private final List<UserAddress> addresses = new ArrayList<>();
}
