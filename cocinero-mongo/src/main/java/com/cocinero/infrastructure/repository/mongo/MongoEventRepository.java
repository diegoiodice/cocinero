package com.cocinero.infrastructure.repository.mongo;

import com.cocinero.infrastructure.repository.mongo.schemas.MongoEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoEventRepository extends MongoRepository<MongoEvent,String> {

}
