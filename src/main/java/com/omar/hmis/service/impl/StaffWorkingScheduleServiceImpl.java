package com.omar.hmis.service.impl;

import com.omar.hmis.service.StaffWorkingScheduleService;
import com.omar.hmis.domain.StaffWorkingSchedule;
import com.omar.hmis.repository.StaffWorkingScheduleRepository;
import com.omar.hmis.repository.search.StaffWorkingScheduleSearchRepository;
import com.omar.hmis.service.dto.StaffWorkingScheduleDTO;
import com.omar.hmis.service.mapper.StaffWorkingScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link StaffWorkingSchedule}.
 */
@Service
@Transactional
public class StaffWorkingScheduleServiceImpl implements StaffWorkingScheduleService {

    private final Logger log = LoggerFactory.getLogger(StaffWorkingScheduleServiceImpl.class);

    private final StaffWorkingScheduleRepository staffWorkingScheduleRepository;

    private final StaffWorkingScheduleMapper staffWorkingScheduleMapper;

    private final StaffWorkingScheduleSearchRepository staffWorkingScheduleSearchRepository;

    public StaffWorkingScheduleServiceImpl(StaffWorkingScheduleRepository staffWorkingScheduleRepository, StaffWorkingScheduleMapper staffWorkingScheduleMapper, StaffWorkingScheduleSearchRepository staffWorkingScheduleSearchRepository) {
        this.staffWorkingScheduleRepository = staffWorkingScheduleRepository;
        this.staffWorkingScheduleMapper = staffWorkingScheduleMapper;
        this.staffWorkingScheduleSearchRepository = staffWorkingScheduleSearchRepository;
    }

    /**
     * Save a staffWorkingSchedule.
     *
     * @param staffWorkingScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StaffWorkingScheduleDTO save(StaffWorkingScheduleDTO staffWorkingScheduleDTO) {
        log.debug("Request to save StaffWorkingSchedule : {}", staffWorkingScheduleDTO);
        StaffWorkingSchedule staffWorkingSchedule = staffWorkingScheduleMapper.toEntity(staffWorkingScheduleDTO);
        staffWorkingSchedule = staffWorkingScheduleRepository.save(staffWorkingSchedule);
        StaffWorkingScheduleDTO result = staffWorkingScheduleMapper.toDto(staffWorkingSchedule);
        staffWorkingScheduleSearchRepository.save(staffWorkingSchedule);
        return result;
    }

    /**
     * Get all the staffWorkingSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StaffWorkingScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StaffWorkingSchedules");
        return staffWorkingScheduleRepository.findAll(pageable)
            .map(staffWorkingScheduleMapper::toDto);
    }


    /**
     * Get one staffWorkingSchedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StaffWorkingScheduleDTO> findOne(Long id) {
        log.debug("Request to get StaffWorkingSchedule : {}", id);
        return staffWorkingScheduleRepository.findById(id)
            .map(staffWorkingScheduleMapper::toDto);
    }

    /**
     * Delete the staffWorkingSchedule by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StaffWorkingSchedule : {}", id);
        staffWorkingScheduleRepository.deleteById(id);
        staffWorkingScheduleSearchRepository.deleteById(id);
    }

    /**
     * Search for the staffWorkingSchedule corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StaffWorkingScheduleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StaffWorkingSchedules for query {}", query);
        return staffWorkingScheduleSearchRepository.search(queryStringQuery(query), pageable)
            .map(staffWorkingScheduleMapper::toDto);
    }
}
