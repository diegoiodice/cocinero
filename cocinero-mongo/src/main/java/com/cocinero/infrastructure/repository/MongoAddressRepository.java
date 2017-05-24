package com.cocinero.infrastructure.repository;

import com.cocinero.infrastructure.repository.schemas.MongoAddress;
import com.cocinero.infrastructure.repository.schemas.MongoEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoAddressRepository extends MongoRepository<MongoAddress,String> {

}
