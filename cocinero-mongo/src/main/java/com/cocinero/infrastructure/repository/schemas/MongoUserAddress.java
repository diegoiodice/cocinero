package com.cocinero.infrastructure.repository.schemas;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class MongoUserAddress {

    @DBRef
    private final MongoAddress address;
    private final String telephone;
    private final String alias;
}
