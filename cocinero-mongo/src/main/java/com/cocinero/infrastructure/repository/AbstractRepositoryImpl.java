package com.cocinero.infrastructure.repository;

import com.cocinero.repository.AbstractRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class AbstractRepositoryImpl<T,V> implements AbstractRepository<T>{

    protected final ObjectMapper objectMapper;

    protected final MongoRepository<V,String> mongoRepository;

    private final Class<T> type;

    protected AbstractRepositoryImpl(ObjectMapper objectMapper, MongoRepository<V, String> mongoRepository, Class<T> type) {
        this.objectMapper = objectMapper;
        this.mongoRepository = mongoRepository;
        this.type = type;
    }

    public final T create(T entity) {
        return this.save(entity);
    }

    public final Collection<T> find() {
        return mongoRepository.findAll().stream().map(me-> objectMapper.convertValue(me,type)).collect(Collectors.toList());
    }

    public final T findById(String id) {
        return objectMapper.convertValue(mongoRepository.findOne(id),type);
    }
}
