package com.cocinero.infrastructure.repository.schemas;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class MongoAttendant {

    private final String id;

    @NotNull
    private final String status;

    @DBRef
    private final MongoEvent event;
}
