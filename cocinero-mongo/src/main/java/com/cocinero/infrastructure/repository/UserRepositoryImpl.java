package com.cocinero.infrastructure.repository;

import com.cocinero.domain.User;
import com.cocinero.infrastructure.repository.mongo.MongoUserRepository;
import com.cocinero.infrastructure.repository.schemas.MongoUser;
import com.cocinero.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends AbstractRepositoryImpl<User,MongoUser> implements UserRepository {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MongoUserRepository mongoUserRepository;

    @Override
    public User save(User user) {
        MongoUser mongoUser = objectMapper.convertValue(user, MongoUser.class);
        mongoUser = mongoUserRepository.save(mongoUser);
        return objectMapper.convertValue(mongoUser, User.class);
    }

    @Override
    public User findByEmail(String username) {
        return objectMapper.convertValue(mongoUserRepository.findByEmail(username),User.class);
    }

    @Override
    protected Class<User> getType() {
        return User.class;
    }

    @Override
    protected MongoRepository<MongoUser, String> getMongoRepository() {
        return mongoUserRepository;
    }

    @Override
    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
