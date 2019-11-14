package com.omar.hmis.web.rest;

import com.omar.hmis.service.WorkingScheduleService;
import com.omar.hmis.web.rest.errors.BadRequestAlertException;
import com.omar.hmis.service.dto.WorkingScheduleDTO;

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
 * REST controller for managing {@link com.omar.hmis.domain.WorkingSchedule}.
 */
@RestController
@RequestMapping("/api")
public class WorkingScheduleResource {

    private final Logger log = LoggerFactory.getLogger(WorkingScheduleResource.class);

    private static final String ENTITY_NAME = "patientServiceWorkingSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkingScheduleService workingScheduleService;

    public WorkingScheduleResource(WorkingScheduleService workingScheduleService) {
        this.workingScheduleService = workingScheduleService;
    }

    /**
     * {@code POST  /working-schedules} : Create a new workingSchedule.
     *
     * @param workingScheduleDTO the workingScheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workingScheduleDTO, or with status {@code 400 (Bad Request)} if the workingSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/working-schedules")
    public ResponseEntity<WorkingScheduleDTO> createWorkingSchedule(@Valid @RequestBody WorkingScheduleDTO workingScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save WorkingSchedule : {}", workingScheduleDTO);
        if (workingScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new workingSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkingScheduleDTO result = workingScheduleService.save(workingScheduleDTO);
        return ResponseEntity.created(new URI("/api/working-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /working-schedules} : Updates an existing workingSchedule.
     *
     * @param workingScheduleDTO the workingScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workingScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the workingScheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workingScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/working-schedules")
    public ResponseEntity<WorkingScheduleDTO> updateWorkingSchedule(@Valid @RequestBody WorkingScheduleDTO workingScheduleDTO) throws URISyntaxException {
        log.debug("REST request to update WorkingSchedule : {}", workingScheduleDTO);
        if (workingScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkingScheduleDTO result = workingScheduleService.save(workingScheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workingScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /working-schedules} : get all the workingSchedules.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workingSchedules in body.
     */
    @GetMapping("/working-schedules")
    public ResponseEntity<List<WorkingScheduleDTO>> getAllWorkingSchedules(Pageable pageable) {
        log.debug("REST request to get a page of WorkingSchedules");
        Page<WorkingScheduleDTO> page = workingScheduleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /working-schedules/:id} : get the "id" workingSchedule.
     *
     * @param id the id of the workingScheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workingScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/working-schedules/{id}")
    public ResponseEntity<WorkingScheduleDTO> getWorkingSchedule(@PathVariable Long id) {
        log.debug("REST request to get WorkingSchedule : {}", id);
        Optional<WorkingScheduleDTO> workingScheduleDTO = workingScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workingScheduleDTO);
    }

    /**
     * {@code DELETE  /working-schedules/:id} : delete the "id" workingSchedule.
     *
     * @param id the id of the workingScheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/working-schedules/{id}")
    public ResponseEntity<Void> deleteWorkingSchedule(@PathVariable Long id) {
        log.debug("REST request to delete WorkingSchedule : {}", id);
        workingScheduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/working-schedules?query=:query} : search for the workingSchedule corresponding
     * to the query.
     *
     * @param query the query of the workingSchedule search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/working-schedules")
    public ResponseEntity<List<WorkingScheduleDTO>> searchWorkingSchedules(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of WorkingSchedules for query {}", query);
        Page<WorkingScheduleDTO> page = workingScheduleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
