package com.omar.hmis.service;

import com.omar.hmis.service.dto.AppointmentScheduleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.omar.hmis.domain.AppointmentSchedule}.
 */
public interface AppointmentScheduleService {

    /**
     * Save a appointmentSchedule.
     *
     * @param appointmentScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    AppointmentScheduleDTO save(AppointmentScheduleDTO appointmentScheduleDTO);

    /**
     * Get all the appointmentSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppointmentScheduleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" appointmentSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppointmentScheduleDTO> findOne(Long id);

    /**
     * Delete the "id" appointmentSchedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the appointmentSchedule corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppointmentScheduleDTO> search(String query, Pageable pageable);
}
