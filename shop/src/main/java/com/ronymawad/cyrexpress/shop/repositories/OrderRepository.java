package com.ronymawad.cyrexpress.shop.repositories;

import com.ronymawad.cyrexpress.shop.models.OrderModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<OrderModel, String> {
    Optional<OrderModel> findById(String id);
    Optional<List<OrderModel>> findByShopName(String shopName);
    Optional<List<OrderModel>> findByDriverName(String driverName);
    Optional<List<OrderModel>> findByClientFirstName(String clientFirstName);
    Optional<List<OrderModel>> findByClientLastName(String clientLastName);
    Optional<List<OrderModel>> findByClientPhoneNumber(String clientPhoneNumber);
    Optional<List<OrderModel>> findByClientEmail(String clientEmail);
}
