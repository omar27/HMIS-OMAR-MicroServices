package com.omar.hmis.service;

import com.omar.hmis.service.dto.StaffDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.omar.hmis.domain.Staff}.
 */
public interface StaffService {

    /**
     * Save a staff.
     *
     * @param staffDTO the entity to save.
     * @return the persisted entity.
     */
    StaffDTO save(StaffDTO staffDTO);

    /**
     * Get all the staff.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StaffDTO> findAll(Pageable pageable);


    /**
     * Get the "id" staff.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StaffDTO> findOne(Long id);

    /**
     * Delete the "id" staff.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the staff corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StaffDTO> search(String query, Pageable pageable);
}
