package com.omar.hmis.repository;
import com.omar.hmis.domain.WorkingSchedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WorkingSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkingScheduleRepository extends JpaRepository<WorkingSchedule, Long> {

}
