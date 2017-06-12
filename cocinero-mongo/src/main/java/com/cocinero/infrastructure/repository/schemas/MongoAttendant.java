package com.cocinero.infrastructure.repository.schemas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class MongoAttendant {

    @Id
    @JsonSerialize(using=ToStringSerializer.class)
    private ObjectId id;
}
