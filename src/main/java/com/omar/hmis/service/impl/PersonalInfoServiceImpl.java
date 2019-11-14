package com.omar.hmis.service.impl;

import com.omar.hmis.service.PersonalInfoService;
import com.omar.hmis.domain.PersonalInfo;
import com.omar.hmis.repository.PersonalInfoRepository;
import com.omar.hmis.repository.search.PersonalInfoSearchRepository;
import com.omar.hmis.service.dto.PersonalInfoDTO;
import com.omar.hmis.service.mapper.PersonalInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link PersonalInfo}.
 */
@Service
@Transactional
public class PersonalInfoServiceImpl implements PersonalInfoService {

    private final Logger log = LoggerFactory.getLogger(PersonalInfoServiceImpl.class);

    private final PersonalInfoRepository personalInfoRepository;

    private final PersonalInfoMapper personalInfoMapper;

    private final PersonalInfoSearchRepository personalInfoSearchRepository;

    public PersonalInfoServiceImpl(PersonalInfoRepository personalInfoRepository, PersonalInfoMapper personalInfoMapper, PersonalInfoSearchRepository personalInfoSearchRepository) {
        this.personalInfoRepository = personalInfoRepository;
        this.personalInfoMapper = personalInfoMapper;
        this.personalInfoSearchRepository = personalInfoSearchRepository;
    }

    /**
     * Save a personalInfo.
     *
     * @param personalInfoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PersonalInfoDTO save(PersonalInfoDTO personalInfoDTO) {
        log.debug("Request to save PersonalInfo : {}", personalInfoDTO);
        PersonalInfo personalInfo = personalInfoMapper.toEntity(personalInfoDTO);
        personalInfo = personalInfoRepository.save(personalInfo);
        PersonalInfoDTO result = personalInfoMapper.toDto(personalInfo);
        personalInfoSearchRepository.save(personalInfo);
        return result;
    }

    /**
     * Get all the personalInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PersonalInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PersonalInfos");
        return personalInfoRepository.findAll(pageable)
            .map(personalInfoMapper::toDto);
    }


    /**
     * Get one personalInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalInfoDTO> findOne(Long id) {
        log.debug("Request to get PersonalInfo : {}", id);
        return personalInfoRepository.findById(id)
            .map(personalInfoMapper::toDto);
    }

    /**
     * Delete the personalInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonalInfo : {}", id);
        personalInfoRepository.deleteById(id);
        personalInfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the personalInfo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PersonalInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PersonalInfos for query {}", query);
        return personalInfoSearchRepository.search(queryStringQuery(query), pageable)
            .map(personalInfoMapper::toDto);
    }
}
