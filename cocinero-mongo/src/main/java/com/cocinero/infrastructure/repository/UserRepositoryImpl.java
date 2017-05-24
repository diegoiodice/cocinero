package com.cocinero.infrastructure.repository;

import com.cocinero.domain.User;
import com.cocinero.infrastructure.repository.mongo.MongoUserRepository;
import com.cocinero.infrastructure.repository.schemas.MongoUser;
import com.cocinero.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends AbstractRepositoryImpl<User,MongoUser> implements UserRepository {

    protected final ObjectMapper objectMapper;

    protected final MongoUserRepository mongoUserRepository;

    @Autowired
    public UserRepositoryImpl(ObjectMapper objectMapper, MongoUserRepository mongoUserRepository) {
        super(objectMapper,mongoUserRepository, User.class);
        this.objectMapper = objectMapper;
        this.mongoUserRepository = mongoUserRepository;
    }

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
}
