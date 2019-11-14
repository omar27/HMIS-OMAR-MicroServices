package com.omar.hmis.web.rest;

import com.omar.hmis.service.PersonalInfoService;
import com.omar.hmis.web.rest.errors.BadRequestAlertException;
import com.omar.hmis.service.dto.PersonalInfoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.omar.hmis.domain.PersonalInfo}.
 */
@RestController
@RequestMapping("/api")
public class PersonalInfoResource {

    private final Logger log = LoggerFactory.getLogger(PersonalInfoResource.class);

    private static final String ENTITY_NAME = "patientServicePersonalInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonalInfoService personalInfoService;

    public PersonalInfoResource(PersonalInfoService personalInfoService) {
        this.personalInfoService = personalInfoService;
    }

    /**
     * {@code POST  /personal-infos} : Create a new personalInfo.
     *
     * @param personalInfoDTO the personalInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personalInfoDTO, or with status {@code 400 (Bad Request)} if the personalInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personal-infos")
    public ResponseEntity<PersonalInfoDTO> createPersonalInfo(@Valid @RequestBody PersonalInfoDTO personalInfoDTO) throws URISyntaxException {
        log.debug("REST request to save PersonalInfo : {}", personalInfoDTO);
        if (personalInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new personalInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonalInfoDTO result = personalInfoService.save(personalInfoDTO);
        return ResponseEntity.created(new URI("/api/personal-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personal-infos} : Updates an existing personalInfo.
     *
     * @param personalInfoDTO the personalInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personalInfoDTO,
     * or with status {@code 400 (Bad Request)} if the personalInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personalInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personal-infos")
    public ResponseEntity<PersonalInfoDTO> updatePersonalInfo(@Valid @RequestBody PersonalInfoDTO personalInfoDTO) throws URISyntaxException {
        log.debug("REST request to update PersonalInfo : {}", personalInfoDTO);
        if (personalInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonalInfoDTO result = personalInfoService.save(personalInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personalInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /personal-infos} : get all the personalInfos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personalInfos in body.
     */
    @GetMapping("/personal-infos")
    public ResponseEntity<List<PersonalInfoDTO>> getAllPersonalInfos(Pageable pageable) {
        log.debug("REST request to get a page of PersonalInfos");
        Page<PersonalInfoDTO> page = personalInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /personal-infos/:id} : get the "id" personalInfo.
     *
     * @param id the id of the personalInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personalInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personal-infos/{id}")
    public ResponseEntity<PersonalInfoDTO> getPersonalInfo(@PathVariable Long id) {
        log.debug("REST request to get PersonalInfo : {}", id);
        Optional<PersonalInfoDTO> personalInfoDTO = personalInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personalInfoDTO);
    }

    /**
     * {@code DELETE  /personal-infos/:id} : delete the "id" personalInfo.
     *
     * @param id the id of the personalInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personal-infos/{id}")
    public ResponseEntity<Void> deletePersonalInfo(@PathVariable Long id) {
        log.debug("REST request to delete PersonalInfo : {}", id);
        personalInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/personal-infos?query=:query} : search for the personalInfo corresponding
     * to the query.
     *
     * @param query the query of the personalInfo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/personal-infos")
    public ResponseEntity<List<PersonalInfoDTO>> searchPersonalInfos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PersonalInfos for query {}", query);
        Page<PersonalInfoDTO> page = personalInfoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
