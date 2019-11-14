package com.omar.hmis.service.impl;

import com.omar.hmis.service.HospitalService;
import com.omar.hmis.domain.Hospital;
import com.omar.hmis.repository.HospitalRepository;
import com.omar.hmis.repository.search.HospitalSearchRepository;
import com.omar.hmis.service.dto.HospitalDTO;
import com.omar.hmis.service.mapper.HospitalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Hospital}.
 */
@Service
@Transactional
public class HospitalServiceImpl implements HospitalService {

    private final Logger log = LoggerFactory.getLogger(HospitalServiceImpl.class);

    private final HospitalRepository hospitalRepository;

    private final HospitalMapper hospitalMapper;

    private final HospitalSearchRepository hospitalSearchRepository;

    public HospitalServiceImpl(HospitalRepository hospitalRepository, HospitalMapper hospitalMapper, HospitalSearchRepository hospitalSearchRepository) {
        this.hospitalRepository = hospitalRepository;
        this.hospitalMapper = hospitalMapper;
        this.hospitalSearchRepository = hospitalSearchRepository;
    }

    /**
     * Save a hospital.
     *
     * @param hospitalDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HospitalDTO save(HospitalDTO hospitalDTO) {
        log.debug("Request to save Hospital : {}", hospitalDTO);
        Hospital hospital = hospitalMapper.toEntity(hospitalDTO);
        hospital = hospitalRepository.save(hospital);
        HospitalDTO result = hospitalMapper.toDto(hospital);
        hospitalSearchRepository.save(hospital);
        return result;
    }

    /**
     * Get all the hospitals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HospitalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Hospitals");
        return hospitalRepository.findAll(pageable)
            .map(hospitalMapper::toDto);
    }


    /**
     * Get one hospital by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HospitalDTO> findOne(Long id) {
        log.debug("Request to get Hospital : {}", id);
        return hospitalRepository.findById(id)
            .map(hospitalMapper::toDto);
    }

    /**
     * Delete the hospital by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hospital : {}", id);
        hospitalRepository.deleteById(id);
        hospitalSearchRepository.deleteById(id);
    }

    /**
     * Search for the hospital corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HospitalDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Hospitals for query {}", query);
        return hospitalSearchRepository.search(queryStringQuery(query), pageable)
            .map(hospitalMapper::toDto);
    }
}
