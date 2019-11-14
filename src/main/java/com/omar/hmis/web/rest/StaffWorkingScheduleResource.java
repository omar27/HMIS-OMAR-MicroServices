package com.omar.hmis.web.rest;

import com.omar.hmis.service.StaffWorkingScheduleService;
import com.omar.hmis.web.rest.errors.BadRequestAlertException;
import com.omar.hmis.service.dto.StaffWorkingScheduleDTO;

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
 * REST controller for managing {@link com.omar.hmis.domain.StaffWorkingSchedule}.
 */
@RestController
@RequestMapping("/api")
public class StaffWorkingScheduleResource {

    private final Logger log = LoggerFactory.getLogger(StaffWorkingScheduleResource.class);

    private static final String ENTITY_NAME = "patientServiceStaffWorkingSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaffWorkingScheduleService staffWorkingScheduleService;

    public StaffWorkingScheduleResource(StaffWorkingScheduleService staffWorkingScheduleService) {
        this.staffWorkingScheduleService = staffWorkingScheduleService;
    }

    /**
     * {@code POST  /staff-working-schedules} : Create a new staffWorkingSchedule.
     *
     * @param staffWorkingScheduleDTO the staffWorkingScheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new staffWorkingScheduleDTO, or with status {@code 400 (Bad Request)} if the staffWorkingSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/staff-working-schedules")
    public ResponseEntity<StaffWorkingScheduleDTO> createStaffWorkingSchedule(@Valid @RequestBody StaffWorkingScheduleDTO staffWorkingScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save StaffWorkingSchedule : {}", staffWorkingScheduleDTO);
        if (staffWorkingScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new staffWorkingSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaffWorkingScheduleDTO result = staffWorkingScheduleService.save(staffWorkingScheduleDTO);
        return ResponseEntity.created(new URI("/api/staff-working-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /staff-working-schedules} : Updates an existing staffWorkingSchedule.
     *
     * @param staffWorkingScheduleDTO the staffWorkingScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staffWorkingScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the staffWorkingScheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the staffWorkingScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/staff-working-schedules")
    public ResponseEntity<StaffWorkingScheduleDTO> updateStaffWorkingSchedule(@Valid @RequestBody StaffWorkingScheduleDTO staffWorkingScheduleDTO) throws URISyntaxException {
        log.debug("REST request to update StaffWorkingSchedule : {}", staffWorkingScheduleDTO);
        if (staffWorkingScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaffWorkingScheduleDTO result = staffWorkingScheduleService.save(staffWorkingScheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, staffWorkingScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /staff-working-schedules} : get all the staffWorkingSchedules.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staffWorkingSchedules in body.
     */
    @GetMapping("/staff-working-schedules")
    public ResponseEntity<List<StaffWorkingScheduleDTO>> getAllStaffWorkingSchedules(Pageable pageable) {
        log.debug("REST request to get a page of StaffWorkingSchedules");
        Page<StaffWorkingScheduleDTO> page = staffWorkingScheduleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /staff-working-schedules/:id} : get the "id" staffWorkingSchedule.
     *
     * @param id the id of the staffWorkingScheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staffWorkingScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/staff-working-schedules/{id}")
    public ResponseEntity<StaffWorkingScheduleDTO> getStaffWorkingSchedule(@PathVariable Long id) {
        log.debug("REST request to get StaffWorkingSchedule : {}", id);
        Optional<StaffWorkingScheduleDTO> staffWorkingScheduleDTO = staffWorkingScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(staffWorkingScheduleDTO);
    }

    /**
     * {@code DELETE  /staff-working-schedules/:id} : delete the "id" staffWorkingSchedule.
     *
     * @param id the id of the staffWorkingScheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/staff-working-schedules/{id}")
    public ResponseEntity<Void> deleteStaffWorkingSchedule(@PathVariable Long id) {
        log.debug("REST request to delete StaffWorkingSchedule : {}", id);
        staffWorkingScheduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/staff-working-schedules?query=:query} : search for the staffWorkingSchedule corresponding
     * to the query.
     *
     * @param query the query of the staffWorkingSchedule search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/staff-working-schedules")
    public ResponseEntity<List<StaffWorkingScheduleDTO>> searchStaffWorkingSchedules(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StaffWorkingSchedules for query {}", query);
        Page<StaffWorkingScheduleDTO> page = staffWorkingScheduleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
