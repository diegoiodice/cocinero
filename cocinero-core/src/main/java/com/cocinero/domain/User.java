package com.cocinero.domain;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class User {

    private final String id;
    private final String email;
    private final String password;
    private final List<Role> roles = new ArrayList<>();
}