package com.cocinero.infrastructure.repository.schemas;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "userProfiles")
public class MongoUserProfile {

    @Id
    @JsonSerialize(using=ToStringSerializer.class)
    private ObjectId id;

    private final String names;
    private final String lastNames;
    private final Date dateOfBirth;
    private final String telephone;

    private List<MongoUserAddress> addresses = new ArrayList<>();
}
