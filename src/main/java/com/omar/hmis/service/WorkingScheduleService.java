package com.omar.hmis.service;

import com.omar.hmis.service.dto.WorkingScheduleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.omar.hmis.domain.WorkingSchedule}.
 */
public interface WorkingScheduleService {

    /**
     * Save a workingSchedule.
     *
     * @param workingScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    WorkingScheduleDTO save(WorkingScheduleDTO workingScheduleDTO);

    /**
     * Get all the workingSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkingScheduleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" workingSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkingScheduleDTO> findOne(Long id);

    /**
     * Delete the "id" workingSchedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the workingSchedule corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkingScheduleDTO> search(String query, Pageable pageable);
}
