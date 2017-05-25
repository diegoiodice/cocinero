package com.cocinero.api.attendant.lifecycle;

import com.cocinero.domain.Attendant;
import com.cocinero.domain.Event;
import lombok.Data;

import java.util.List;

@Data
public class Context {

    private final Event event;
    private final Attendant attendant;
    private final List<Attendant> attendants;
}
