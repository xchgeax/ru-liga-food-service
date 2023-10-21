package ru.liga.repo;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.liga.entity.Courier;
import ru.liga.entity.CourierStatus;

import javax.transaction.Transactional;

public interface CourierRepository extends CrudRepository<Courier, Long> {

    @Transactional
    @Query("select c from Courier c where c.id = :id")
    Courier findCourierById(@Param("id") Long id);

    @Transactional
    @Query("select c from Courier c where c.status = :status")
    Courier findCourierByStatus(@Param("status") CourierStatus status);
}
