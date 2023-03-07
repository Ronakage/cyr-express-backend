package com.ronymawad.cyrexpress.shop.request;

public record OrderCreationRequest(
        String shopId,
        String shopName,
        String clientFirstName,
        String clientLastName,
        String clientPhoneNumber,
        String clientEmail,
        String clientAddress,
        String orderReceipt
        ) {
}
