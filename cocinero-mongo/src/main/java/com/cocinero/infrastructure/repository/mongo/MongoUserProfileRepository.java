package com.cocinero.infrastructure.repository.mongo;

import com.cocinero.infrastructure.repository.schemas.MongoUserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MongoUserProfileRepository extends MongoRepository<MongoUserProfile,String>{


}
