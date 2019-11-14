package com.omar.hmis.service.impl;

import com.omar.hmis.service.InPatientService;
import com.omar.hmis.domain.InPatient;
import com.omar.hmis.repository.InPatientRepository;
import com.omar.hmis.repository.search.InPatientSearchRepository;
import com.omar.hmis.service.dto.InPatientDTO;
import com.omar.hmis.service.mapper.InPatientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link InPatient}.
 */
@Service
@Transactional
public class InPatientServiceImpl implements InPatientService {

    private final Logger log = LoggerFactory.getLogger(InPatientServiceImpl.class);

    private final InPatientRepository inPatientRepository;

    private final InPatientMapper inPatientMapper;

    private final InPatientSearchRepository inPatientSearchRepository;

    public InPatientServiceImpl(InPatientRepository inPatientRepository, InPatientMapper inPatientMapper, InPatientSearchRepository inPatientSearchRepository) {
        this.inPatientRepository = inPatientRepository;
        this.inPatientMapper = inPatientMapper;
        this.inPatientSearchRepository = inPatientSearchRepository;
    }

    /**
     * Save a inPatient.
     *
     * @param inPatientDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InPatientDTO save(InPatientDTO inPatientDTO) {
        log.debug("Request to save InPatient : {}", inPatientDTO);
        InPatient inPatient = inPatientMapper.toEntity(inPatientDTO);
        inPatient = inPatientRepository.save(inPatient);
        InPatientDTO result = inPatientMapper.toDto(inPatient);
        inPatientSearchRepository.save(inPatient);
        return result;
    }

    /**
     * Get all the inPatients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InPatientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InPatients");
        return inPatientRepository.findAll(pageable)
            .map(inPatientMapper::toDto);
    }


    /**
     * Get one inPatient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InPatientDTO> findOne(Long id) {
        log.debug("Request to get InPatient : {}", id);
        return inPatientRepository.findById(id)
            .map(inPatientMapper::toDto);
    }

    /**
     * Delete the inPatient by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InPatient : {}", id);
        inPatientRepository.deleteById(id);
        inPatientSearchRepository.deleteById(id);
    }

    /**
     * Search for the inPatient corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InPatientDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of InPatients for query {}", query);
        return inPatientSearchRepository.search(queryStringQuery(query), pageable)
            .map(inPatientMapper::toDto);
    }
}
