package ru.liga.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.dto.CourierUpdateStatusDto;
import ru.liga.entity.Courier;
import ru.liga.entity.CourierStatus;
import ru.liga.exception.ResourceNotFoundException;
import ru.liga.service.CourierService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Tag(name = "Couriers management API")
@RestController
@RequestMapping("/courier")
@RequiredArgsConstructor
public class CouriersController {

    private final CourierService courierService;

    @Operation(summary = "Get courier list by status")
    @GetMapping("/status")
    public ResponseEntity<List<Courier>> getCourierListByStatus(@RequestParam CourierStatus status) {
        return ResponseEntity.ok(courierService.getCourierListByStatus(status));
    }

    @Operation(summary = "Get all couriers")
    @GetMapping
    public ResponseEntity<Page<Courier>> getCourierList(@PositiveOrZero @RequestParam Integer pageIndex,
                                        @Positive @RequestParam Integer pageCount) {
        return ResponseEntity.ok(courierService.getCourierList(pageIndex, pageCount));
    }

    @Operation(summary = "Get courier by id")
    @GetMapping("/id/{courierId}")
    public ResponseEntity<Courier> getCourierById(@PathVariable Long courierId) throws ResourceNotFoundException {
        return ResponseEntity.ok(courierService.getCourierById(courierId));
    }

    @Operation(summary = "Update courier status")
    @PostMapping("/id/{courierId}/status")
    public ResponseEntity<Object> updateCourierStatus(@PathVariable("courierId") Long courierId,
                                                    @RequestBody CourierUpdateStatusDto updateDto) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(courierService.updateCourierStatus(courierId, updateDto.getStatus()));
    }

}
