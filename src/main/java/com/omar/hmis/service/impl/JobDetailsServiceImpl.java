package com.omar.hmis.service.impl;

import com.omar.hmis.service.JobDetailsService;
import com.omar.hmis.domain.JobDetails;
import com.omar.hmis.repository.JobDetailsRepository;
import com.omar.hmis.repository.search.JobDetailsSearchRepository;
import com.omar.hmis.service.dto.JobDetailsDTO;
import com.omar.hmis.service.mapper.JobDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link JobDetails}.
 */
@Service
@Transactional
public class JobDetailsServiceImpl implements JobDetailsService {

    private final Logger log = LoggerFactory.getLogger(JobDetailsServiceImpl.class);

    private final JobDetailsRepository jobDetailsRepository;

    private final JobDetailsMapper jobDetailsMapper;

    private final JobDetailsSearchRepository jobDetailsSearchRepository;

    public JobDetailsServiceImpl(JobDetailsRepository jobDetailsRepository, JobDetailsMapper jobDetailsMapper, JobDetailsSearchRepository jobDetailsSearchRepository) {
        this.jobDetailsRepository = jobDetailsRepository;
        this.jobDetailsMapper = jobDetailsMapper;
        this.jobDetailsSearchRepository = jobDetailsSearchRepository;
    }

    /**
     * Save a jobDetails.
     *
     * @param jobDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public JobDetailsDTO save(JobDetailsDTO jobDetailsDTO) {
        log.debug("Request to save JobDetails : {}", jobDetailsDTO);
        JobDetails jobDetails = jobDetailsMapper.toEntity(jobDetailsDTO);
        jobDetails = jobDetailsRepository.save(jobDetails);
        JobDetailsDTO result = jobDetailsMapper.toDto(jobDetails);
        jobDetailsSearchRepository.save(jobDetails);
        return result;
    }

    /**
     * Get all the jobDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JobDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JobDetails");
        return jobDetailsRepository.findAll(pageable)
            .map(jobDetailsMapper::toDto);
    }


    /**
     * Get one jobDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<JobDetailsDTO> findOne(Long id) {
        log.debug("Request to get JobDetails : {}", id);
        return jobDetailsRepository.findById(id)
            .map(jobDetailsMapper::toDto);
    }

    /**
     * Delete the jobDetails by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobDetails : {}", id);
        jobDetailsRepository.deleteById(id);
        jobDetailsSearchRepository.deleteById(id);
    }

    /**
     * Search for the jobDetails corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JobDetailsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of JobDetails for query {}", query);
        return jobDetailsSearchRepository.search(queryStringQuery(query), pageable)
            .map(jobDetailsMapper::toDto);
    }
}
