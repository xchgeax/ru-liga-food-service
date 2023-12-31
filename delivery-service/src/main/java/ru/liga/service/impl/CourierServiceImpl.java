package ru.liga.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.dto.CourierStatusConfirmationDto;
import ru.liga.entity.Courier;
import ru.liga.entity.CourierStatus;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.mapper.CourierStatusConfirmationMapper;
import ru.liga.repo.CourierRepository;
import ru.liga.service.CourierService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final CourierStatusConfirmationMapper courierStatusConfirmationMapper;

    public Page<Courier> getCourierList(Integer pageIndex, Integer pageCount) {
        Pageable page = PageRequest.of(pageIndex, pageCount);

        return courierRepository.findAll(page);
    }

    public CourierStatusConfirmationDto updateCourierStatus(Long courierId, CourierStatus courierStatus) throws ResourceNotFoundException {
        Courier courier = courierRepository.findById(courierId).orElseThrow(() -> new ResourceNotFoundException("Courier does not exist"));

        courier.setStatus(courierStatus);
        courierRepository.save(courier);

        return courierStatusConfirmationMapper.toCourierStatusConfirmationDto(courierId, courierStatus);
    }

    public Courier getCourierById(Long id) throws ResourceNotFoundException {
        return courierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Courier does not exist"));
    }

    public List<Courier> getCourierListByStatus(CourierStatus courierStatus) {
        return courierRepository.findCourierByStatus(courierStatus);
    }
}
