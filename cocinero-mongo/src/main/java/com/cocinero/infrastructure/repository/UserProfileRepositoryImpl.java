package com.cocinero.infrastructure.repository;

import com.cocinero.domain.UserProfile;
import com.cocinero.infrastructure.repository.mongo.MongoUserProfileRepository;
import com.cocinero.infrastructure.repository.schemas.MongoUserProfile;
import com.cocinero.repository.UserProfileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserProfileRepositoryImpl extends AbstractRepositoryImpl<UserProfile,MongoUserProfile> implements UserProfileRepository {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MongoUserProfileRepository mongoUserProfileRepository;

    @Override
    public UserProfile save(UserProfile user) {
        MongoUserProfile mongoProfile = objectMapper.convertValue(user, MongoUserProfile.class);
        mongoProfile = mongoUserProfileRepository.save(mongoProfile);
        return objectMapper.convertValue(mongoProfile, UserProfile.class);
    }

    @Override
    protected Class<UserProfile> getType() {
        return UserProfile.class;
    }

    @Override
    protected MongoRepository<MongoUserProfile, String> getMongoRepository() {
        return mongoUserProfileRepository;
    }

    @Override
    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
