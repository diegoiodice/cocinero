package com.cocinero.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Event {

    private final String id;
    private final String type;
    private final Date created;
    private final Date eventDate;
    private final List<Attendant> attendants = new ArrayList<>();
    private final Integer maxAttendants;
    private final Host host;
    private Address address;
    private final BigDecimal amount;
    private final String name;
    private final String description;
    private final List<Comment> comments = new ArrayList<>();
}
