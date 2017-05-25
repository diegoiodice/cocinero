package com.cocinero.infrastructure.repository;

import com.cocinero.repository.AbstractRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class AbstractRepositoryImpl<T,V> implements AbstractRepository<T>{


    public final T create(T entity) {
        return this.save(entity);
    }

    public final Collection<T> find() {
        return getMongoRepository().findAll().stream().map(me-> getObjectMapper().convertValue(me,getType())).collect(Collectors.toList());
    }

    public final T findById(String id) {
        return getObjectMapper().convertValue(getMongoRepository().findOne(id),getType());
    }

    protected abstract Class<T> getType();

    protected abstract MongoRepository<V,String> getMongoRepository();

    protected abstract ObjectMapper getObjectMapper();
}
