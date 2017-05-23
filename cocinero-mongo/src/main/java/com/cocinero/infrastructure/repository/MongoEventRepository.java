package com.cocinero.infrastructure.repository;

import com.cocinero.infrastructure.repository.schemas.MongoEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoEventRepository extends MongoRepository<MongoEvent,String> {

}
