package com.globsest.testsoap.repository;

import com.globsest.testsoap.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByName(String name);
}
