package com.omar.hmis.service.impl;

import com.omar.hmis.service.WorkingScheduleService;
import com.omar.hmis.domain.WorkingSchedule;
import com.omar.hmis.repository.WorkingScheduleRepository;
import com.omar.hmis.repository.search.WorkingScheduleSearchRepository;
import com.omar.hmis.service.dto.WorkingScheduleDTO;
import com.omar.hmis.service.mapper.WorkingScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link WorkingSchedule}.
 */
@Service
@Transactional
public class WorkingScheduleServiceImpl implements WorkingScheduleService {

    private final Logger log = LoggerFactory.getLogger(WorkingScheduleServiceImpl.class);

    private final WorkingScheduleRepository workingScheduleRepository;

    private final WorkingScheduleMapper workingScheduleMapper;

    private final WorkingScheduleSearchRepository workingScheduleSearchRepository;

    public WorkingScheduleServiceImpl(WorkingScheduleRepository workingScheduleRepository, WorkingScheduleMapper workingScheduleMapper, WorkingScheduleSearchRepository workingScheduleSearchRepository) {
        this.workingScheduleRepository = workingScheduleRepository;
        this.workingScheduleMapper = workingScheduleMapper;
        this.workingScheduleSearchRepository = workingScheduleSearchRepository;
    }

    /**
     * Save a workingSchedule.
     *
     * @param workingScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WorkingScheduleDTO save(WorkingScheduleDTO workingScheduleDTO) {
        log.debug("Request to save WorkingSchedule : {}", workingScheduleDTO);
        WorkingSchedule workingSchedule = workingScheduleMapper.toEntity(workingScheduleDTO);
        workingSchedule = workingScheduleRepository.save(workingSchedule);
        WorkingScheduleDTO result = workingScheduleMapper.toDto(workingSchedule);
        workingScheduleSearchRepository.save(workingSchedule);
        return result;
    }

    /**
     * Get all the workingSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkingScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkingSchedules");
        return workingScheduleRepository.findAll(pageable)
            .map(workingScheduleMapper::toDto);
    }


    /**
     * Get one workingSchedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WorkingScheduleDTO> findOne(Long id) {
        log.debug("Request to get WorkingSchedule : {}", id);
        return workingScheduleRepository.findById(id)
            .map(workingScheduleMapper::toDto);
    }

    /**
     * Delete the workingSchedule by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkingSchedule : {}", id);
        workingScheduleRepository.deleteById(id);
        workingScheduleSearchRepository.deleteById(id);
    }

    /**
     * Search for the workingSchedule corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkingScheduleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of WorkingSchedules for query {}", query);
        return workingScheduleSearchRepository.search(queryStringQuery(query), pageable)
            .map(workingScheduleMapper::toDto);
    }
}
