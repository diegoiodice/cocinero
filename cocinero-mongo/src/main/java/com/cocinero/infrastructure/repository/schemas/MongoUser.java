package com.cocinero.infrastructure.repository.schemas;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document(collection = "users")
public class MongoUser {

    @Id
    @JsonSerialize(using=ToStringSerializer.class)
    private ObjectId id;
    @Indexed(unique = true)
    @NotNull(message = "email is mandatory")
    @Email
    private String email;
    @NotNull(message = "password is mandatory")
    private String password;
}
