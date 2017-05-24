package com.cocinero.infrastructure.repository.mongo;

import com.cocinero.infrastructure.repository.schemas.MongoAddress;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoAddressRepository extends MongoRepository<MongoAddress,String>{

}
