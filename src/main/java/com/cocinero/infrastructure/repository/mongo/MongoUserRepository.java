package com.cocinero.infrastructure.repository.mongo;

import com.cocinero.infrastructure.repository.mongo.schemas.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MongoUserRepository extends MongoRepository<MongoUser,String>{

    MongoUser findByEmail(String email);

    MongoUser findById(String id);
}
