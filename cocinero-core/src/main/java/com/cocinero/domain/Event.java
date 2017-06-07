package com.cocinero.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class Event {

    private final String id;
    private final String name;
    private final String description;
    private final String type;
    private final Integer maxAttendants;
    private final BigDecimal amount;

    private final Date eventDate;
    private final Address address;

    private final List<Attendant> attendants = new ArrayList<>();
    private final List<Comment> comments = new ArrayList<>();

    private final Date created;
    private final Host host;

}
