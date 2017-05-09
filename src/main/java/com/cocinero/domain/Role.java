package com.cocinero.domain;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Role {

    private Long id;
    private String name;
    private String description;
    private Set<String> permissions = new HashSet<>();
}
