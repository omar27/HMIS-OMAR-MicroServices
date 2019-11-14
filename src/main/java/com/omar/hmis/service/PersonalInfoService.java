package com.omar.hmis.service;

import com.omar.hmis.service.dto.PersonalInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.omar.hmis.domain.PersonalInfo}.
 */
public interface PersonalInfoService {

    /**
     * Save a personalInfo.
     *
     * @param personalInfoDTO the entity to save.
     * @return the persisted entity.
     */
    PersonalInfoDTO save(PersonalInfoDTO personalInfoDTO);

    /**
     * Get all the personalInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonalInfoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" personalInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonalInfoDTO> findOne(Long id);

    /**
     * Delete the "id" personalInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the personalInfo corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonalInfoDTO> search(String query, Pageable pageable);
}
