package com.ronymawad.cyrexpress.shop.repositories;

import com.ronymawad.cyrexpress.shop.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserModel, String> {
    Optional<UserModel> findById(String id);
    Optional<UserModel> findByEmail(String email);
    boolean existsByEmail(String email);
}
