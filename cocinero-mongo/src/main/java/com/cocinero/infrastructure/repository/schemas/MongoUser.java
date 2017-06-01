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

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
public class MongoUser {

    @Id
    @JsonSerialize(using=ToStringSerializer.class)
    private ObjectId id;
    @Indexed(unique = true)
    @NotBlank(message = "com.cocinero.validator.constraints.MongoUser.email.mandatory.message")
    @Email(message = "com.cocinero.validator.constraints.MongoUser.email.message")
    private String email;
    @NotBlank(message = "com.cocinero.validator.constraints.MongoUser.password.mandatory.message")
    private String password;

    @DBRef
    private List<MongoAddress> addresses = new ArrayList<>();
}
