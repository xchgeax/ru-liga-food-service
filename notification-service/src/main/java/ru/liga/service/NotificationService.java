package ru.liga.service;


public interface NotificationService {

    void sendCustomerNotification(Long customerId, String message);

    void sendCourierNotification(Long courierId, String message);
}
