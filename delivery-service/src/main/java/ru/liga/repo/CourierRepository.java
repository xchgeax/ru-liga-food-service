package ru.liga.repo;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.liga.entity.Courier;
import ru.liga.entity.CourierStatus;

import javax.transaction.Transactional;
import java.util.List;

public interface CourierRepository extends JpaRepository<Courier, Long> {

    @Transactional
    @Query("select c from Courier c where c.status = :status")
    List<Courier> findCourierByStatus(@Param("status") CourierStatus status);
}
