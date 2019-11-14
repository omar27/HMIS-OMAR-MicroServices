package com.omar.hmis.web.rest;

import com.omar.hmis.PatientServiceApp;
import com.omar.hmis.config.TestSecurityConfiguration;
import com.omar.hmis.domain.AppointmentSchedule;
import com.omar.hmis.domain.Patient;
import com.omar.hmis.domain.Staff;
import com.omar.hmis.repository.AppointmentScheduleRepository;
import com.omar.hmis.repository.search.AppointmentScheduleSearchRepository;
import com.omar.hmis.service.AppointmentScheduleService;
import com.omar.hmis.service.dto.AppointmentScheduleDTO;
import com.omar.hmis.service.mapper.AppointmentScheduleMapper;
import com.omar.hmis.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.omar.hmis.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.omar.hmis.domain.enumeration.AppointmentScheduleStatus;
/**
 * Integration tests for the {@link AppointmentScheduleResource} REST controller.
 */
@SpringBootTest(classes = {PatientServiceApp.class, TestSecurityConfiguration.class})
public class AppointmentScheduleResourceIT {

    private static final AppointmentScheduleStatus DEFAULT_STATUS = AppointmentScheduleStatus.PENDING;
    private static final AppointmentScheduleStatus UPDATED_STATUS = AppointmentScheduleStatus.CANCELLED;

    private static final LocalDate DEFAULT_SCHEDULED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCHEDULED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AppointmentScheduleRepository appointmentScheduleRepository;

    @Autowired
    private AppointmentScheduleMapper appointmentScheduleMapper;

    @Autowired
    private AppointmentScheduleService appointmentScheduleService;

    /**
     * This repository is mocked in the com.omar.hmis.repository.search test package.
     *
     * @see com.omar.hmis.repository.search.AppointmentScheduleSearchRepositoryMockConfiguration
     */
    @Autowired
    private AppointmentScheduleSearchRepository mockAppointmentScheduleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAppointmentScheduleMockMvc;

    private AppointmentSchedule appointmentSchedule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppointmentScheduleResource appointmentScheduleResource = new AppointmentScheduleResource(appointmentScheduleService);
        this.restAppointmentScheduleMockMvc = MockMvcBuilders.standaloneSetup(appointmentScheduleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppointmentSchedule createEntity(EntityManager em) {
        AppointmentSchedule appointmentSchedule = new AppointmentSchedule()
            .status(DEFAULT_STATUS)
            .scheduledDate(DEFAULT_SCHEDULED_DATE);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        appointmentSchedule.setPatient(patient);
        // Add required entity
        Staff staff;
        if (TestUtil.findAll(em, Staff.class).isEmpty()) {
            staff = StaffResourceIT.createEntity(em);
            em.persist(staff);
            em.flush();
        } else {
            staff = TestUtil.findAll(em, Staff.class).get(0);
        }
        appointmentSchedule.setStaff(staff);
        // Add required entity
        appointmentSchedule.setScheduledBy(staff);
        return appointmentSchedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppointmentSchedule createUpdatedEntity(EntityManager em) {
        AppointmentSchedule appointmentSchedule = new AppointmentSchedule()
            .status(UPDATED_STATUS)
            .scheduledDate(UPDATED_SCHEDULED_DATE);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createUpdatedEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        appointmentSchedule.setPatient(patient);
        // Add required entity
        Staff staff;
        if (TestUtil.findAll(em, Staff.class).isEmpty()) {
            staff = StaffResourceIT.createUpdatedEntity(em);
            em.persist(staff);
            em.flush();
        } else {
            staff = TestUtil.findAll(em, Staff.class).get(0);
        }
        appointmentSchedule.setStaff(staff);
        // Add required entity
        appointmentSchedule.setScheduledBy(staff);
        return appointmentSchedule;
    }

    @BeforeEach
    public void initTest() {
        appointmentSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppointmentSchedule() throws Exception {
        int databaseSizeBeforeCreate = appointmentScheduleRepository.findAll().size();

        // Create the AppointmentSchedule
        AppointmentScheduleDTO appointmentScheduleDTO = appointmentScheduleMapper.toDto(appointmentSchedule);
        restAppointmentScheduleMockMvc.perform(post("/api/appointment-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentScheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the AppointmentSchedule in the database
        List<AppointmentSchedule> appointmentScheduleList = appointmentScheduleRepository.findAll();
        assertThat(appointmentScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        AppointmentSchedule testAppointmentSchedule = appointmentScheduleList.get(appointmentScheduleList.size() - 1);
        assertThat(testAppointmentSchedule.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAppointmentSchedule.getScheduledDate()).isEqualTo(DEFAULT_SCHEDULED_DATE);

        // Validate the AppointmentSchedule in Elasticsearch
        verify(mockAppointmentScheduleSearchRepository, times(1)).save(testAppointmentSchedule);
    }

    @Test
    @Transactional
    public void createAppointmentScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appointmentScheduleRepository.findAll().size();

        // Create the AppointmentSchedule with an existing ID
        appointmentSchedule.setId(1L);
        AppointmentScheduleDTO appointmentScheduleDTO = appointmentScheduleMapper.toDto(appointmentSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppointmentScheduleMockMvc.perform(post("/api/appointment-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppointmentSchedule in the database
        List<AppointmentSchedule> appointmentScheduleList = appointmentScheduleRepository.findAll();
        assertThat(appointmentScheduleList).hasSize(databaseSizeBeforeCreate);

        // Validate the AppointmentSchedule in Elasticsearch
        verify(mockAppointmentScheduleSearchRepository, times(0)).save(appointmentSchedule);
    }


    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = appointmentScheduleRepository.findAll().size();
        // set the field null
        appointmentSchedule.setStatus(null);

        // Create the AppointmentSchedule, which fails.
        AppointmentScheduleDTO appointmentScheduleDTO = appointmentScheduleMapper.toDto(appointmentSchedule);

        restAppointmentScheduleMockMvc.perform(post("/api/appointment-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<AppointmentSchedule> appointmentScheduleList = appointmentScheduleRepository.findAll();
        assertThat(appointmentScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScheduledDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = appointmentScheduleRepository.findAll().size();
        // set the field null
        appointmentSchedule.setScheduledDate(null);

        // Create the AppointmentSchedule, which fails.
        AppointmentScheduleDTO appointmentScheduleDTO = appointmentScheduleMapper.toDto(appointmentSchedule);

        restAppointmentScheduleMockMvc.perform(post("/api/appointment-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<AppointmentSchedule> appointmentScheduleList = appointmentScheduleRepository.findAll();
        assertThat(appointmentScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppointmentSchedules() throws Exception {
        // Initialize the database
        appointmentScheduleRepository.saveAndFlush(appointmentSchedule);

        // Get all the appointmentScheduleList
        restAppointmentScheduleMockMvc.perform(get("/api/appointment-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appointmentSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].scheduledDate").value(hasItem(DEFAULT_SCHEDULED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getAppointmentSchedule() throws Exception {
        // Initialize the database
        appointmentScheduleRepository.saveAndFlush(appointmentSchedule);

        // Get the appointmentSchedule
        restAppointmentScheduleMockMvc.perform(get("/api/appointment-schedules/{id}", appointmentSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appointmentSchedule.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.scheduledDate").value(DEFAULT_SCHEDULED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppointmentSchedule() throws Exception {
        // Get the appointmentSchedule
        restAppointmentScheduleMockMvc.perform(get("/api/appointment-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppointmentSchedule() throws Exception {
        // Initialize the database
        appointmentScheduleRepository.saveAndFlush(appointmentSchedule);

        int databaseSizeBeforeUpdate = appointmentScheduleRepository.findAll().size();

        // Update the appointmentSchedule
        AppointmentSchedule updatedAppointmentSchedule = appointmentScheduleRepository.findById(appointmentSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedAppointmentSchedule are not directly saved in db
        em.detach(updatedAppointmentSchedule);
        updatedAppointmentSchedule
            .status(UPDATED_STATUS)
            .scheduledDate(UPDATED_SCHEDULED_DATE);
        AppointmentScheduleDTO appointmentScheduleDTO = appointmentScheduleMapper.toDto(updatedAppointmentSchedule);

        restAppointmentScheduleMockMvc.perform(put("/api/appointment-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentScheduleDTO)))
            .andExpect(status().isOk());

        // Validate the AppointmentSchedule in the database
        List<AppointmentSchedule> appointmentScheduleList = appointmentScheduleRepository.findAll();
        assertThat(appointmentScheduleList).hasSize(databaseSizeBeforeUpdate);
        AppointmentSchedule testAppointmentSchedule = appointmentScheduleList.get(appointmentScheduleList.size() - 1);
        assertThat(testAppointmentSchedule.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppointmentSchedule.getScheduledDate()).isEqualTo(UPDATED_SCHEDULED_DATE);

        // Validate the AppointmentSchedule in Elasticsearch
        verify(mockAppointmentScheduleSearchRepository, times(1)).save(testAppointmentSchedule);
    }

    @Test
    @Transactional
    public void updateNonExistingAppointmentSchedule() throws Exception {
        int databaseSizeBeforeUpdate = appointmentScheduleRepository.findAll().size();

        // Create the AppointmentSchedule
        AppointmentScheduleDTO appointmentScheduleDTO = appointmentScheduleMapper.toDto(appointmentSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppointmentScheduleMockMvc.perform(put("/api/appointment-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppointmentSchedule in the database
        List<AppointmentSchedule> appointmentScheduleList = appointmentScheduleRepository.findAll();
        assertThat(appointmentScheduleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AppointmentSchedule in Elasticsearch
        verify(mockAppointmentScheduleSearchRepository, times(0)).save(appointmentSchedule);
    }

    @Test
    @Transactional
    public void deleteAppointmentSchedule() throws Exception {
        // Initialize the database
        appointmentScheduleRepository.saveAndFlush(appointmentSchedule);

        int databaseSizeBeforeDelete = appointmentScheduleRepository.findAll().size();

        // Delete the appointmentSchedule
        restAppointmentScheduleMockMvc.perform(delete("/api/appointment-schedules/{id}", appointmentSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppointmentSchedule> appointmentScheduleList = appointmentScheduleRepository.findAll();
        assertThat(appointmentScheduleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AppointmentSchedule in Elasticsearch
        verify(mockAppointmentScheduleSearchRepository, times(1)).deleteById(appointmentSchedule.getId());
    }

    @Test
    @Transactional
    public void searchAppointmentSchedule() throws Exception {
        // Initialize the database
        appointmentScheduleRepository.saveAndFlush(appointmentSchedule);
        when(mockAppointmentScheduleSearchRepository.search(queryStringQuery("id:" + appointmentSchedule.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(appointmentSchedule), PageRequest.of(0, 1), 1));
        // Search the appointmentSchedule
        restAppointmentScheduleMockMvc.perform(get("/api/_search/appointment-schedules?query=id:" + appointmentSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appointmentSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].scheduledDate").value(hasItem(DEFAULT_SCHEDULED_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppointmentSchedule.class);
        AppointmentSchedule appointmentSchedule1 = new AppointmentSchedule();
        appointmentSchedule1.setId(1L);
        AppointmentSchedule appointmentSchedule2 = new AppointmentSchedule();
        appointmentSchedule2.setId(appointmentSchedule1.getId());
        assertThat(appointmentSchedule1).isEqualTo(appointmentSchedule2);
        appointmentSchedule2.setId(2L);
        assertThat(appointmentSchedule1).isNotEqualTo(appointmentSchedule2);
        appointmentSchedule1.setId(null);
        assertThat(appointmentSchedule1).isNotEqualTo(appointmentSchedule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppointmentScheduleDTO.class);
        AppointmentScheduleDTO appointmentScheduleDTO1 = new AppointmentScheduleDTO();
        appointmentScheduleDTO1.setId(1L);
        AppointmentScheduleDTO appointmentScheduleDTO2 = new AppointmentScheduleDTO();
        assertThat(appointmentScheduleDTO1).isNotEqualTo(appointmentScheduleDTO2);
        appointmentScheduleDTO2.setId(appointmentScheduleDTO1.getId());
        assertThat(appointmentScheduleDTO1).isEqualTo(appointmentScheduleDTO2);
        appointmentScheduleDTO2.setId(2L);
        assertThat(appointmentScheduleDTO1).isNotEqualTo(appointmentScheduleDTO2);
        appointmentScheduleDTO1.setId(null);
        assertThat(appointmentScheduleDTO1).isNotEqualTo(appointmentScheduleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(appointmentScheduleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(appointmentScheduleMapper.fromId(null)).isNull();
    }
}
