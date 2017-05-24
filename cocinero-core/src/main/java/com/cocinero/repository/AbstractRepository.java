package com.cocinero.repository;

import java.util.Collection;

public interface AbstractRepository<T> {

    T create(T entity);

    T save(T entity);

    Collection<T> find();

    T findById(String id);
}
