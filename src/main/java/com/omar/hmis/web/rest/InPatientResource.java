package com.omar.hmis.web.rest;

import com.omar.hmis.service.InPatientService;
import com.omar.hmis.web.rest.errors.BadRequestAlertException;
import com.omar.hmis.service.dto.InPatientDTO;

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
 * REST controller for managing {@link com.omar.hmis.domain.InPatient}.
 */
@RestController
@RequestMapping("/api")
public class InPatientResource {

    private final Logger log = LoggerFactory.getLogger(InPatientResource.class);

    private static final String ENTITY_NAME = "patientServiceInPatient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InPatientService inPatientService;

    public InPatientResource(InPatientService inPatientService) {
        this.inPatientService = inPatientService;
    }

    /**
     * {@code POST  /in-patients} : Create a new inPatient.
     *
     * @param inPatientDTO the inPatientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inPatientDTO, or with status {@code 400 (Bad Request)} if the inPatient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/in-patients")
    public ResponseEntity<InPatientDTO> createInPatient(@Valid @RequestBody InPatientDTO inPatientDTO) throws URISyntaxException {
        log.debug("REST request to save InPatient : {}", inPatientDTO);
        if (inPatientDTO.getId() != null) {
            throw new BadRequestAlertException("A new inPatient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InPatientDTO result = inPatientService.save(inPatientDTO);
        return ResponseEntity.created(new URI("/api/in-patients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /in-patients} : Updates an existing inPatient.
     *
     * @param inPatientDTO the inPatientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inPatientDTO,
     * or with status {@code 400 (Bad Request)} if the inPatientDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inPatientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/in-patients")
    public ResponseEntity<InPatientDTO> updateInPatient(@Valid @RequestBody InPatientDTO inPatientDTO) throws URISyntaxException {
        log.debug("REST request to update InPatient : {}", inPatientDTO);
        if (inPatientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InPatientDTO result = inPatientService.save(inPatientDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inPatientDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /in-patients} : get all the inPatients.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inPatients in body.
     */
    @GetMapping("/in-patients")
    public ResponseEntity<List<InPatientDTO>> getAllInPatients(Pageable pageable) {
        log.debug("REST request to get a page of InPatients");
        Page<InPatientDTO> page = inPatientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /in-patients/:id} : get the "id" inPatient.
     *
     * @param id the id of the inPatientDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inPatientDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/in-patients/{id}")
    public ResponseEntity<InPatientDTO> getInPatient(@PathVariable Long id) {
        log.debug("REST request to get InPatient : {}", id);
        Optional<InPatientDTO> inPatientDTO = inPatientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inPatientDTO);
    }

    /**
     * {@code DELETE  /in-patients/:id} : delete the "id" inPatient.
     *
     * @param id the id of the inPatientDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/in-patients/{id}")
    public ResponseEntity<Void> deleteInPatient(@PathVariable Long id) {
        log.debug("REST request to delete InPatient : {}", id);
        inPatientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/in-patients?query=:query} : search for the inPatient corresponding
     * to the query.
     *
     * @param query the query of the inPatient search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/in-patients")
    public ResponseEntity<List<InPatientDTO>> searchInPatients(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of InPatients for query {}", query);
        Page<InPatientDTO> page = inPatientService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
