package ru.liga.mapper;

import org.mapstruct.Mapper;
import ru.liga.dto.CourierStatusConfirmationDto;
import ru.liga.entity.CourierStatus;

@Mapper(componentModel = "spring")
public interface CourierStatusConfirmationMapper {

    CourierStatusConfirmationDto toCourierStatusConfirmationDto(Long id, CourierStatus status);
}
