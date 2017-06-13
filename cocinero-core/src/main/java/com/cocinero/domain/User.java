package com.cocinero.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(of = {"id"})
public class User {

    private final String id;
    private final String email;
    private final String password;
}