package ru.liga.notificationservice.service;

import org.springframework.stereotype.Service;
import ru.liga.notificationservice.dto.DeliveryDto;
import ru.liga.notificationservice.dto.DeliveryStatusConfirmationDto;

import java.util.List;

@Service
public class DeliveryService {

    public List<DeliveryDto> getDeliveryListByStatus(String status) {
        return List.of(new DeliveryDto());
    }

    public DeliveryStatusConfirmationDto updateDeliveryStatus(Long id, String status) {
        DeliveryStatusConfirmationDto statusConfirmationDto = new DeliveryStatusConfirmationDto();
        statusConfirmationDto.setId(id);
        statusConfirmationDto.setStatus(status);
        return statusConfirmationDto;
    }
}
