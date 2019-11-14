package com.omar.hmis.repository;
import com.omar.hmis.domain.InPatient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InPatient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InPatientRepository extends JpaRepository<InPatient, Long> {

}
