package ru.liga.service;

import org.springframework.data.domain.Page;
import ru.liga.dto.CourierStatusConfirmationDto;
import ru.liga.entity.Courier;
import ru.liga.entity.CourierStatus;
import ru.liga.exception.ResourceNotFoundException;

import java.util.List;

public interface CourierService {

    Page<Courier> getCourierList(Integer pageIndex, Integer pageCount);

    CourierStatusConfirmationDto updateCourierStatus(Long courierId, CourierStatus courierStatus) throws ResourceNotFoundException;

    Courier getCourierById(Long id) throws ResourceNotFoundException;

    List<Courier> getCourierListByStatus(CourierStatus courierStatus);
}
