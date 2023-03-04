package com.ronymawad.shop.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("order")
public class OrderModel {
    @Id
    private String id;
    private String shopId;
    private String shopName;
    private String driverId;
    private String driverName;

    private String clientSessionID;
    private String clientFirstName;
    private String clientLastName;
    private String clientPhoneNumber;
    private String clientEmail;
    private String clientAddress;

    //It's going to be a photo, string is for testing
    private String orderReceipt;

    private LocalDateTime timeCreated;
    private LocalDateTime timeReadyForDelivery;
    private LocalDateTime timePickedForDelivery;
    private LocalDateTime timeDelivered;
    private LocalDateTime timeCanceled;

    private Boolean isCreated;
    private Boolean isBeingPrepared;
    private Boolean isReadyForDelivery;
    private Boolean isPickedByDriver;
    private Boolean isDroppedByDriver;
    private Boolean isCanceled;

}
