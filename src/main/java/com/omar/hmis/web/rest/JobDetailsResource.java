package com.omar.hmis.web.rest;

import com.omar.hmis.service.JobDetailsService;
import com.omar.hmis.web.rest.errors.BadRequestAlertException;
import com.omar.hmis.service.dto.JobDetailsDTO;

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
 * REST controller for managing {@link com.omar.hmis.domain.JobDetails}.
 */
@RestController
@RequestMapping("/api")
public class JobDetailsResource {

    private final Logger log = LoggerFactory.getLogger(JobDetailsResource.class);

    private static final String ENTITY_NAME = "patientServiceJobDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobDetailsService jobDetailsService;

    public JobDetailsResource(JobDetailsService jobDetailsService) {
        this.jobDetailsService = jobDetailsService;
    }

    /**
     * {@code POST  /job-details} : Create a new jobDetails.
     *
     * @param jobDetailsDTO the jobDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobDetailsDTO, or with status {@code 400 (Bad Request)} if the jobDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-details")
    public ResponseEntity<JobDetailsDTO> createJobDetails(@Valid @RequestBody JobDetailsDTO jobDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save JobDetails : {}", jobDetailsDTO);
        if (jobDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobDetailsDTO result = jobDetailsService.save(jobDetailsDTO);
        return ResponseEntity.created(new URI("/api/job-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-details} : Updates an existing jobDetails.
     *
     * @param jobDetailsDTO the jobDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the jobDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-details")
    public ResponseEntity<JobDetailsDTO> updateJobDetails(@Valid @RequestBody JobDetailsDTO jobDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update JobDetails : {}", jobDetailsDTO);
        if (jobDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobDetailsDTO result = jobDetailsService.save(jobDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jobDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /job-details} : get all the jobDetails.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobDetails in body.
     */
    @GetMapping("/job-details")
    public ResponseEntity<List<JobDetailsDTO>> getAllJobDetails(Pageable pageable) {
        log.debug("REST request to get a page of JobDetails");
        Page<JobDetailsDTO> page = jobDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-details/:id} : get the "id" jobDetails.
     *
     * @param id the id of the jobDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-details/{id}")
    public ResponseEntity<JobDetailsDTO> getJobDetails(@PathVariable Long id) {
        log.debug("REST request to get JobDetails : {}", id);
        Optional<JobDetailsDTO> jobDetailsDTO = jobDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobDetailsDTO);
    }

    /**
     * {@code DELETE  /job-details/:id} : delete the "id" jobDetails.
     *
     * @param id the id of the jobDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-details/{id}")
    public ResponseEntity<Void> deleteJobDetails(@PathVariable Long id) {
        log.debug("REST request to delete JobDetails : {}", id);
        jobDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/job-details?query=:query} : search for the jobDetails corresponding
     * to the query.
     *
     * @param query the query of the jobDetails search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/job-details")
    public ResponseEntity<List<JobDetailsDTO>> searchJobDetails(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of JobDetails for query {}", query);
        Page<JobDetailsDTO> page = jobDetailsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
