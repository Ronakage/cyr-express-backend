package com.ronymawad.shop.controllers;

import com.ronymawad.shop.request.OrderCreationRequest;
import com.ronymawad.shop.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v0/shop/orders")
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasAuthority('ROLE_SHOP_OWNER')")
    @PostMapping()
    public ResponseEntity<Object> createNewOrder(@RequestBody OrderCreationRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.createNewOrder(request));
    }

    @PreAuthorize("hasAuthority('ROLE_SHOP_OWNER') || hasAuthority('ROLE_DRIVER')")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateOrderStatus(@PathVariable String id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrderStatus(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("message", e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('ROLE_STAFF')")
    @GetMapping()
    public ResponseEntity<Object> getAllOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
    }

    @PreAuthorize("hasAuthority('ROLE_SHOP_OWNER')")
    @GetMapping("/shopName/{shopName}")
    public ResponseEntity<Object> getOrdersByShopName(@PathVariable String shopName){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByShopName(shopName));
    }

    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    @GetMapping("/driverName/{driverName}")
    public ResponseEntity<Object> getOrdersByDriverName(@PathVariable String driverName){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByDriverName(driverName));
    }

    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    @GetMapping("/clientAttribute/{clientAttribute}")
    public ResponseEntity<Object> getOrdersByClient(@PathVariable String clientAttribute){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByClient(clientAttribute));
    }

    @PreAuthorize("hasAuthority('ROLE_STAFF')")
    @GetMapping("/attribute/{attribute}")
    public ResponseEntity<Object> getOrdersByAttribute(@PathVariable String attribute){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByAttribute(attribute));
    }


}
