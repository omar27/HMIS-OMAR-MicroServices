package com.omar.hmis.service;

import com.omar.hmis.service.dto.JobDetailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.omar.hmis.domain.JobDetails}.
 */
public interface JobDetailsService {

    /**
     * Save a jobDetails.
     *
     * @param jobDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    JobDetailsDTO save(JobDetailsDTO jobDetailsDTO);

    /**
     * Get all the jobDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JobDetailsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" jobDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" jobDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the jobDetails corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JobDetailsDTO> search(String query, Pageable pageable);
}
