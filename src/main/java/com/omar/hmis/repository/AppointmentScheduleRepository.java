package com.omar.hmis.repository;
import com.omar.hmis.domain.AppointmentSchedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AppointmentSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentScheduleRepository extends JpaRepository<AppointmentSchedule, Long> {

}
