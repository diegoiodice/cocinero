package com.cocinero.repository;

import com.cocinero.domain.User;

public interface UserRepository extends AbstractRepository<User>{

    User findByEmail(String email);
}
