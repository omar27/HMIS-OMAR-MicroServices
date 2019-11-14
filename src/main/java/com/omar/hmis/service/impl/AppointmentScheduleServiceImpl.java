package com.omar.hmis.service.impl;

import com.omar.hmis.service.AppointmentScheduleService;
import com.omar.hmis.domain.AppointmentSchedule;
import com.omar.hmis.repository.AppointmentScheduleRepository;
import com.omar.hmis.repository.search.AppointmentScheduleSearchRepository;
import com.omar.hmis.service.dto.AppointmentScheduleDTO;
import com.omar.hmis.service.mapper.AppointmentScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AppointmentSchedule}.
 */
@Service
@Transactional
public class AppointmentScheduleServiceImpl implements AppointmentScheduleService {

    private final Logger log = LoggerFactory.getLogger(AppointmentScheduleServiceImpl.class);

    private final AppointmentScheduleRepository appointmentScheduleRepository;

    private final AppointmentScheduleMapper appointmentScheduleMapper;

    private final AppointmentScheduleSearchRepository appointmentScheduleSearchRepository;

    public AppointmentScheduleServiceImpl(AppointmentScheduleRepository appointmentScheduleRepository, AppointmentScheduleMapper appointmentScheduleMapper, AppointmentScheduleSearchRepository appointmentScheduleSearchRepository) {
        this.appointmentScheduleRepository = appointmentScheduleRepository;
        this.appointmentScheduleMapper = appointmentScheduleMapper;
        this.appointmentScheduleSearchRepository = appointmentScheduleSearchRepository;
    }

    /**
     * Save a appointmentSchedule.
     *
     * @param appointmentScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AppointmentScheduleDTO save(AppointmentScheduleDTO appointmentScheduleDTO) {
        log.debug("Request to save AppointmentSchedule : {}", appointmentScheduleDTO);
        AppointmentSchedule appointmentSchedule = appointmentScheduleMapper.toEntity(appointmentScheduleDTO);
        appointmentSchedule = appointmentScheduleRepository.save(appointmentSchedule);
        AppointmentScheduleDTO result = appointmentScheduleMapper.toDto(appointmentSchedule);
        appointmentScheduleSearchRepository.save(appointmentSchedule);
        return result;
    }

    /**
     * Get all the appointmentSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppointmentSchedules");
        return appointmentScheduleRepository.findAll(pageable)
            .map(appointmentScheduleMapper::toDto);
    }


    /**
     * Get one appointmentSchedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AppointmentScheduleDTO> findOne(Long id) {
        log.debug("Request to get AppointmentSchedule : {}", id);
        return appointmentScheduleRepository.findById(id)
            .map(appointmentScheduleMapper::toDto);
    }

    /**
     * Delete the appointmentSchedule by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppointmentSchedule : {}", id);
        appointmentScheduleRepository.deleteById(id);
        appointmentScheduleSearchRepository.deleteById(id);
    }

    /**
     * Search for the appointmentSchedule corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AppointmentScheduleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AppointmentSchedules for query {}", query);
        return appointmentScheduleSearchRepository.search(queryStringQuery(query), pageable)
            .map(appointmentScheduleMapper::toDto);
    }
}
