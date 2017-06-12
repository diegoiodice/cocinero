package com.cocinero.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.*;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(of = {"id"})
public class Event {

    private final String id;
    private final String name;
    private final String description;
    private final String type;
    private final Integer maxAttendants;
    private final BigDecimal amount;

    private final Date eventDate;
    private final Address address;

    private final Set<Attendant> attendants = new HashSet<>();
    private final List<Comment> comments = new ArrayList<>();

    private final Date created;
    private final Host host;

}
