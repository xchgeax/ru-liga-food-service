package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.service.NotificationService;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendCustomerNotification(Long customerId, String message) {
        log.info("Notification sent to customer id={} content={}", customerId, message);
    }

    @Override
    public void sendCourierNotification(Long courierId, String message) {
        log.info("Notification sent to courier id={} content={}", courierId, message);
    }
}
