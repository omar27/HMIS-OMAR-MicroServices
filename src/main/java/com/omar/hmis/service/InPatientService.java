package com.omar.hmis.service;

import com.omar.hmis.service.dto.InPatientDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.omar.hmis.domain.InPatient}.
 */
public interface InPatientService {

    /**
     * Save a inPatient.
     *
     * @param inPatientDTO the entity to save.
     * @return the persisted entity.
     */
    InPatientDTO save(InPatientDTO inPatientDTO);

    /**
     * Get all the inPatients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InPatientDTO> findAll(Pageable pageable);


    /**
     * Get the "id" inPatient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InPatientDTO> findOne(Long id);

    /**
     * Delete the "id" inPatient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the inPatient corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InPatientDTO> search(String query, Pageable pageable);
}
