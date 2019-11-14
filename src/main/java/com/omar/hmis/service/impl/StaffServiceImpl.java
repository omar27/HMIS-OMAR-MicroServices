package com.omar.hmis.service.impl;

import com.omar.hmis.service.StaffService;
import com.omar.hmis.domain.Staff;
import com.omar.hmis.repository.StaffRepository;
import com.omar.hmis.repository.search.StaffSearchRepository;
import com.omar.hmis.service.dto.StaffDTO;
import com.omar.hmis.service.mapper.StaffMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Staff}.
 */
@Service
@Transactional
public class StaffServiceImpl implements StaffService {

    private final Logger log = LoggerFactory.getLogger(StaffServiceImpl.class);

    private final StaffRepository staffRepository;

    private final StaffMapper staffMapper;

    private final StaffSearchRepository staffSearchRepository;

    public StaffServiceImpl(StaffRepository staffRepository, StaffMapper staffMapper, StaffSearchRepository staffSearchRepository) {
        this.staffRepository = staffRepository;
        this.staffMapper = staffMapper;
        this.staffSearchRepository = staffSearchRepository;
    }

    /**
     * Save a staff.
     *
     * @param staffDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StaffDTO save(StaffDTO staffDTO) {
        log.debug("Request to save Staff : {}", staffDTO);
        Staff staff = staffMapper.toEntity(staffDTO);
        staff = staffRepository.save(staff);
        StaffDTO result = staffMapper.toDto(staff);
        staffSearchRepository.save(staff);
        return result;
    }

    /**
     * Get all the staff.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StaffDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Staff");
        return staffRepository.findAll(pageable)
            .map(staffMapper::toDto);
    }


    /**
     * Get one staff by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StaffDTO> findOne(Long id) {
        log.debug("Request to get Staff : {}", id);
        return staffRepository.findById(id)
            .map(staffMapper::toDto);
    }

    /**
     * Delete the staff by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Staff : {}", id);
        staffRepository.deleteById(id);
        staffSearchRepository.deleteById(id);
    }

    /**
     * Search for the staff corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StaffDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Staff for query {}", query);
        return staffSearchRepository.search(queryStringQuery(query), pageable)
            .map(staffMapper::toDto);
    }
}
