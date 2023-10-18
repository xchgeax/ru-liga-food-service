package ru.liga.entity;

public enum OrderStatus {
    CUSTOMER_CREATED,
    CUSTOMER_PAID,
    CUSTOMER_CANCELLED,
    KITCHEN_ACCEPTED,
    KITCHEN_DENIED,
    KITCHEN_REFUNDED,
    KITCHEN_PREPARING,
    DELIVERY_PENDING,
    DELIVERY_PICKING,
    DELIVERY_DENIED,
    DELIVERY_DELIVERING,
    DELIVERY_REFUNDED,
    DELIVERY_COMPLETE
}