package com.ronymawad.cyrexpress.shop.services;

import com.ronymawad.cyrexpress.shop.models.OrderModel;
import com.ronymawad.cyrexpress.shop.repositories.OrderRepository;
import com.ronymawad.cyrexpress.shop.request.OrderCreationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderModel createNewOrder(OrderCreationRequest request) {
        OrderModel newOrder = OrderModel
                .builder()
                .shopId(request.shopId())
                .shopName(request.shopName())
                .clientSessionID(UUID.randomUUID().toString())
                .clientFirstName(request.clientFirstName())
                .clientLastName(request.clientLastName())
                .clientPhoneNumber(request.clientPhoneNumber())
                .clientEmail(request.clientEmail())
                .clientAddress(request.clientAddress())
                .orderReceipt(request.orderReceipt())
                .timeCreated(LocalDateTime.now())
                .isCreated(true).isBeingPrepared(false).isReadyForDelivery(false).isPickedByDriver(false).isDroppedByDriver(false).isCanceled(false)
                .build();
        return orderRepository.save(newOrder);
    }

    public OrderModel updateOrderStatus(String id) throws Exception {
        OrderModel order = orderRepository.findById(id).orElseThrow(() -> new Exception("Order not found."));
        if(!order.getIsBeingPrepared()){
            order.setIsBeingPrepared(true);
            return orderRepository.save(order);
        }
        else if(!order.getIsReadyForDelivery()){
            order.setIsReadyForDelivery(true);
            order.setTimeReadyForDelivery(LocalDateTime.now());
            return orderRepository.save(order);
        }
        else if(!order.getIsPickedByDriver()){
            order.setIsPickedByDriver(true);
            order.setTimePickedForDelivery(LocalDateTime.now());
            return orderRepository.save(order);
        }
        else if(!order.getIsDroppedByDriver()){
            order.setIsDroppedByDriver(true);
            order.setTimeDelivered(LocalDateTime.now());
            return orderRepository.save(order);
        }
        else{
            throw new Exception("Order is already delivered.");
        }
    }

    public List<OrderModel> getAllOrders(){
        return orderRepository.findAll();
    }

    public List<OrderModel> getOrdersByShopName(String shopName){
        return orderRepository.findByShopName(shopName).orElse(new ArrayList<>());
    }

    public List<OrderModel> getOrdersByDriverName(String driverName){
        return orderRepository.findByDriverName(driverName).orElse(new ArrayList<>());
    }

    public List<OrderModel> getOrdersByClient(String clientAttribute){
        List<OrderModel> orders = new ArrayList<>();
        List<OrderModel> ordersByClientPhoneNumber =  orderRepository.findByClientPhoneNumber(clientAttribute).orElse(new ArrayList<>());
        List<OrderModel> ordersByClientEmail =  orderRepository.findByClientEmail(clientAttribute).orElse(new ArrayList<>());
        orders.addAll(ordersByClientPhoneNumber);
        orders.addAll(ordersByClientEmail);
        return orders;
    }

    public List<OrderModel> getOrdersByAttribute(String attribute){
        List<OrderModel> orders = new ArrayList<>();
        List<OrderModel> ordersByShopName =  orderRepository.findByShopName(attribute).orElse(new ArrayList<>());
        List<OrderModel> ordersByDriverName =  orderRepository.findByDriverName(attribute).orElse(new ArrayList<>());
        List<OrderModel> ordersByClientFirstName =  orderRepository.findByClientFirstName(attribute).orElse(new ArrayList<>());
        List<OrderModel> ordersByClientLastName =  orderRepository.findByClientLastName(attribute).orElse(new ArrayList<>());
        List<OrderModel> ordersByClientPhoneNumber =  orderRepository.findByClientPhoneNumber(attribute).orElse(new ArrayList<>());
        List<OrderModel> ordersByClientEmail =  orderRepository.findByClientEmail(attribute).orElse(new ArrayList<>());
        orders.addAll(ordersByShopName);
        orders.addAll(ordersByDriverName);
        orders.addAll(ordersByClientFirstName);
        orders.addAll(ordersByClientLastName);
        orders.addAll(ordersByClientPhoneNumber);
        orders.addAll(ordersByClientEmail);
        return orders;
    }



}
