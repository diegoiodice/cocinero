package com.cocinero.infrastructure.repository.schemas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "events")
public class MongoEvent {

    @Id
    @JsonSerialize(using=ToStringSerializer.class)
    private ObjectId id;

    @NotNull(message = "Type is mandatory")
    private final String type;
    @NotNull(message = "Event date is mandatory")
    private final Date eventDate;
    @NotNull(message = "Max attendants is mandatory")
    private final Integer maxAttendants;
    @NotNull(message = "Amount is mandatory")
    private final BigDecimal amount;
    @NotNull(message = "Event name is mandatory")
    private final String name;
    @NotNull(message = "Event description is mandatory")
    private final String description;

    @DBRef
    private final MongoUser host;

    @DBRef
    private final MongoAddress address;

    private final Set<MongoAttendant> attendants = new HashSet<>();

    private Date created;

}
