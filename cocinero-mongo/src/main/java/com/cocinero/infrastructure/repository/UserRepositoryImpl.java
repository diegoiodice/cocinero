package com.cocinero.infrastructure.repository;

import com.cocinero.domain.User;
import com.cocinero.infrastructure.repository.schemas.MongoUser;
import com.cocinero.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    private MongoUserRepository mongoUserRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public User create(User user) {
        MongoUser mongoUser = objectMapper.convertValue(user, MongoUser.class);
        mongoUser = mongoUserRepository.save(mongoUser);
        return objectMapper.convertValue(mongoUser, User.class);
    }

    @Override
    public User save(User user) {
        return this.create(user);
    }

    @Override
    public Collection<User> find() {
        return mongoUserRepository.findAll().stream().map(mu-> objectMapper.convertValue(mu,User.class)).collect(Collectors.toList());
    }

    @Override
    public User findById(String id) {

        return objectMapper.convertValue(mongoUserRepository.findById(id),User.class);
    }

    @Override
    public User findByEmail(String username) {
        return objectMapper.convertValue(mongoUserRepository.findByEmail(username),User.class);
    }
}
