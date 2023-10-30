package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.dto.CourierStatusConfirmationDto;
import ru.liga.dto.CourierUpdateStatusDto;
import ru.liga.entity.Courier;
import ru.liga.entity.CourierStatus;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.repo.CourierRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;

    public Page<Courier> getCourierList(Integer pageIndex, Integer pageCount) {
        Pageable page = PageRequest.of(pageIndex, pageCount);

        return courierRepository.findAll(page);
    }

    public CourierStatusConfirmationDto updateCourierStatus(Long courierId, CourierStatus courierStatus) throws ResourceNotFoundException {
        Courier courier = courierRepository.findById(courierId).orElseThrow(() -> new ResourceNotFoundException("Courier does not exist"));

        courier.setStatus(courierStatus);
        courierRepository.save(courier);

        CourierStatusConfirmationDto confirmationDto = new CourierStatusConfirmationDto();

        confirmationDto.setId(courierId);
        confirmationDto.setStatus(courierStatus);

        return confirmationDto;
    }

    public Courier getCourierById(Long id) throws ResourceNotFoundException {
        return courierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Courier does not exist"));
    }

    public List<Courier> getCourierListByStatus(CourierStatus courierStatus) {
        return courierRepository.findCourierByStatus(courierStatus);
    }
}
