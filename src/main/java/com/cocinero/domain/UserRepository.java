package com.cocinero.domain;

import java.util.Collection;

public interface UserRepository {

    User create(User user);

    User save(User user);

    Collection<User> find();

    User findById(String id);

    User findByEmail(String email);
}
