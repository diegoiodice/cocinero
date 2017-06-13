package com.cocinero.infrastructure.repository.schemas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "addresses")
public class MongoAddress {

    @Id
    @JsonSerialize(using=ToStringSerializer.class)
    private ObjectId id;
    private final String postCode;
    private final String addressLine1;
    private final String addressLine2;
    private final String city;
    private final String country;
}
