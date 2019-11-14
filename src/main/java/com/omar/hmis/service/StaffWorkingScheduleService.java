package com.omar.hmis.service;

import com.omar.hmis.service.dto.StaffWorkingScheduleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.omar.hmis.domain.StaffWorkingSchedule}.
 */
public interface StaffWorkingScheduleService {

    /**
     * Save a staffWorkingSchedule.
     *
     * @param staffWorkingScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    StaffWorkingScheduleDTO save(StaffWorkingScheduleDTO staffWorkingScheduleDTO);

    /**
     * Get all the staffWorkingSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StaffWorkingScheduleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" staffWorkingSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StaffWorkingScheduleDTO> findOne(Long id);

    /**
     * Delete the "id" staffWorkingSchedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the staffWorkingSchedule corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StaffWorkingScheduleDTO> search(String query, Pageable pageable);
}
