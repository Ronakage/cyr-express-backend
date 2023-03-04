package com.ronymawad.shop.repositories;

import com.ronymawad.shop.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<UserModel, String> {
    Optional<UserModel> findById(String id);
    Optional<UserModel> findByEmail(String email);
    boolean existsByEmail(String email);
}
