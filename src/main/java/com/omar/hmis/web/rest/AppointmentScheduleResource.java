package com.omar.hmis.web.rest;

import com.omar.hmis.service.AppointmentScheduleService;
import com.omar.hmis.web.rest.errors.BadRequestAlertException;
import com.omar.hmis.service.dto.AppointmentScheduleDTO;

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
 * REST controller for managing {@link com.omar.hmis.domain.AppointmentSchedule}.
 */
@RestController
@RequestMapping("/api")
public class AppointmentScheduleResource {

    private final Logger log = LoggerFactory.getLogger(AppointmentScheduleResource.class);

    private static final String ENTITY_NAME = "patientServiceAppointmentSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppointmentScheduleService appointmentScheduleService;

    public AppointmentScheduleResource(AppointmentScheduleService appointmentScheduleService) {
        this.appointmentScheduleService = appointmentScheduleService;
    }

    /**
     * {@code POST  /appointment-schedules} : Create a new appointmentSchedule.
     *
     * @param appointmentScheduleDTO the appointmentScheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appointmentScheduleDTO, or with status {@code 400 (Bad Request)} if the appointmentSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appointment-schedules")
    public ResponseEntity<AppointmentScheduleDTO> createAppointmentSchedule(@Valid @RequestBody AppointmentScheduleDTO appointmentScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save AppointmentSchedule : {}", appointmentScheduleDTO);
        if (appointmentScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new appointmentSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppointmentScheduleDTO result = appointmentScheduleService.save(appointmentScheduleDTO);
        return ResponseEntity.created(new URI("/api/appointment-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appointment-schedules} : Updates an existing appointmentSchedule.
     *
     * @param appointmentScheduleDTO the appointmentScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appointmentScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the appointmentScheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appointmentScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appointment-schedules")
    public ResponseEntity<AppointmentScheduleDTO> updateAppointmentSchedule(@Valid @RequestBody AppointmentScheduleDTO appointmentScheduleDTO) throws URISyntaxException {
        log.debug("REST request to update AppointmentSchedule : {}", appointmentScheduleDTO);
        if (appointmentScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AppointmentScheduleDTO result = appointmentScheduleService.save(appointmentScheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appointmentScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /appointment-schedules} : get all the appointmentSchedules.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appointmentSchedules in body.
     */
    @GetMapping("/appointment-schedules")
    public ResponseEntity<List<AppointmentScheduleDTO>> getAllAppointmentSchedules(Pageable pageable) {
        log.debug("REST request to get a page of AppointmentSchedules");
        Page<AppointmentScheduleDTO> page = appointmentScheduleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appointment-schedules/:id} : get the "id" appointmentSchedule.
     *
     * @param id the id of the appointmentScheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appointmentScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appointment-schedules/{id}")
    public ResponseEntity<AppointmentScheduleDTO> getAppointmentSchedule(@PathVariable Long id) {
        log.debug("REST request to get AppointmentSchedule : {}", id);
        Optional<AppointmentScheduleDTO> appointmentScheduleDTO = appointmentScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appointmentScheduleDTO);
    }

    /**
     * {@code DELETE  /appointment-schedules/:id} : delete the "id" appointmentSchedule.
     *
     * @param id the id of the appointmentScheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appointment-schedules/{id}")
    public ResponseEntity<Void> deleteAppointmentSchedule(@PathVariable Long id) {
        log.debug("REST request to delete AppointmentSchedule : {}", id);
        appointmentScheduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/appointment-schedules?query=:query} : search for the appointmentSchedule corresponding
     * to the query.
     *
     * @param query the query of the appointmentSchedule search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/appointment-schedules")
    public ResponseEntity<List<AppointmentScheduleDTO>> searchAppointmentSchedules(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AppointmentSchedules for query {}", query);
        Page<AppointmentScheduleDTO> page = appointmentScheduleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
