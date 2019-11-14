package com.omar.hmis.repository;
import com.omar.hmis.domain.StaffWorkingSchedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StaffWorkingSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaffWorkingScheduleRepository extends JpaRepository<StaffWorkingSchedule, Long> {

}
