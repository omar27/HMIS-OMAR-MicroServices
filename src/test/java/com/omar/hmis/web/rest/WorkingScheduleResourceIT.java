package com.omar.hmis.web.rest;

import com.omar.hmis.PatientServiceApp;
import com.omar.hmis.config.TestSecurityConfiguration;
import com.omar.hmis.domain.WorkingSchedule;
import com.omar.hmis.repository.WorkingScheduleRepository;
import com.omar.hmis.repository.search.WorkingScheduleSearchRepository;
import com.omar.hmis.service.WorkingScheduleService;
import com.omar.hmis.service.dto.WorkingScheduleDTO;
import com.omar.hmis.service.mapper.WorkingScheduleMapper;
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
import java.util.Collections;
import java.util.List;

import static com.omar.hmis.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.omar.hmis.domain.enumeration.Days;
/**
 * Integration tests for the {@link WorkingScheduleResource} REST controller.
 */
@SpringBootTest(classes = {PatientServiceApp.class, TestSecurityConfiguration.class})
public class WorkingScheduleResourceIT {

    private static final Days DEFAULT_DAY = Days.MONDAY;
    private static final Days UPDATED_DAY = Days.TUESDAY;

    private static final Boolean DEFAULT_IS_OFF = false;
    private static final Boolean UPDATED_IS_OFF = true;

    private static final String DEFAULT_START_TIME = "AAAAAAAAAA";
    private static final String UPDATED_START_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_END = "AAAAAAAAAA";
    private static final String UPDATED_END = "BBBBBBBBBB";

    @Autowired
    private WorkingScheduleRepository workingScheduleRepository;

    @Autowired
    private WorkingScheduleMapper workingScheduleMapper;

    @Autowired
    private WorkingScheduleService workingScheduleService;

    /**
     * This repository is mocked in the com.omar.hmis.repository.search test package.
     *
     * @see com.omar.hmis.repository.search.WorkingScheduleSearchRepositoryMockConfiguration
     */
    @Autowired
    private WorkingScheduleSearchRepository mockWorkingScheduleSearchRepository;

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

    private MockMvc restWorkingScheduleMockMvc;

    private WorkingSchedule workingSchedule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkingScheduleResource workingScheduleResource = new WorkingScheduleResource(workingScheduleService);
        this.restWorkingScheduleMockMvc = MockMvcBuilders.standaloneSetup(workingScheduleResource)
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
    public static WorkingSchedule createEntity(EntityManager em) {
        WorkingSchedule workingSchedule = new WorkingSchedule()
            .day(DEFAULT_DAY)
            .isOff(DEFAULT_IS_OFF)
            .startTime(DEFAULT_START_TIME)
            .end(DEFAULT_END);
        return workingSchedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkingSchedule createUpdatedEntity(EntityManager em) {
        WorkingSchedule workingSchedule = new WorkingSchedule()
            .day(UPDATED_DAY)
            .isOff(UPDATED_IS_OFF)
            .startTime(UPDATED_START_TIME)
            .end(UPDATED_END);
        return workingSchedule;
    }

    @BeforeEach
    public void initTest() {
        workingSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkingSchedule() throws Exception {
        int databaseSizeBeforeCreate = workingScheduleRepository.findAll().size();

        // Create the WorkingSchedule
        WorkingScheduleDTO workingScheduleDTO = workingScheduleMapper.toDto(workingSchedule);
        restWorkingScheduleMockMvc.perform(post("/api/working-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workingScheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkingSchedule in the database
        List<WorkingSchedule> workingScheduleList = workingScheduleRepository.findAll();
        assertThat(workingScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        WorkingSchedule testWorkingSchedule = workingScheduleList.get(workingScheduleList.size() - 1);
        assertThat(testWorkingSchedule.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testWorkingSchedule.isIsOff()).isEqualTo(DEFAULT_IS_OFF);
        assertThat(testWorkingSchedule.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testWorkingSchedule.getEnd()).isEqualTo(DEFAULT_END);

        // Validate the WorkingSchedule in Elasticsearch
        verify(mockWorkingScheduleSearchRepository, times(1)).save(testWorkingSchedule);
    }

    @Test
    @Transactional
    public void createWorkingScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workingScheduleRepository.findAll().size();

        // Create the WorkingSchedule with an existing ID
        workingSchedule.setId(1L);
        WorkingScheduleDTO workingScheduleDTO = workingScheduleMapper.toDto(workingSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkingScheduleMockMvc.perform(post("/api/working-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workingScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkingSchedule in the database
        List<WorkingSchedule> workingScheduleList = workingScheduleRepository.findAll();
        assertThat(workingScheduleList).hasSize(databaseSizeBeforeCreate);

        // Validate the WorkingSchedule in Elasticsearch
        verify(mockWorkingScheduleSearchRepository, times(0)).save(workingSchedule);
    }


    @Test
    @Transactional
    public void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = workingScheduleRepository.findAll().size();
        // set the field null
        workingSchedule.setDay(null);

        // Create the WorkingSchedule, which fails.
        WorkingScheduleDTO workingScheduleDTO = workingScheduleMapper.toDto(workingSchedule);

        restWorkingScheduleMockMvc.perform(post("/api/working-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workingScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<WorkingSchedule> workingScheduleList = workingScheduleRepository.findAll();
        assertThat(workingScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = workingScheduleRepository.findAll().size();
        // set the field null
        workingSchedule.setStartTime(null);

        // Create the WorkingSchedule, which fails.
        WorkingScheduleDTO workingScheduleDTO = workingScheduleMapper.toDto(workingSchedule);

        restWorkingScheduleMockMvc.perform(post("/api/working-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workingScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<WorkingSchedule> workingScheduleList = workingScheduleRepository.findAll();
        assertThat(workingScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = workingScheduleRepository.findAll().size();
        // set the field null
        workingSchedule.setEnd(null);

        // Create the WorkingSchedule, which fails.
        WorkingScheduleDTO workingScheduleDTO = workingScheduleMapper.toDto(workingSchedule);

        restWorkingScheduleMockMvc.perform(post("/api/working-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workingScheduleDTO)))
            .andExpect(status().isBadRequest());

        List<WorkingSchedule> workingScheduleList = workingScheduleRepository.findAll();
        assertThat(workingScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkingSchedules() throws Exception {
        // Initialize the database
        workingScheduleRepository.saveAndFlush(workingSchedule);

        // Get all the workingScheduleList
        restWorkingScheduleMockMvc.perform(get("/api/working-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workingSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].isOff").value(hasItem(DEFAULT_IS_OFF.booleanValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END)));
    }
    
    @Test
    @Transactional
    public void getWorkingSchedule() throws Exception {
        // Initialize the database
        workingScheduleRepository.saveAndFlush(workingSchedule);

        // Get the workingSchedule
        restWorkingScheduleMockMvc.perform(get("/api/working-schedules/{id}", workingSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workingSchedule.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.isOff").value(DEFAULT_IS_OFF.booleanValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME))
            .andExpect(jsonPath("$.end").value(DEFAULT_END));
    }

    @Test
    @Transactional
    public void getNonExistingWorkingSchedule() throws Exception {
        // Get the workingSchedule
        restWorkingScheduleMockMvc.perform(get("/api/working-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkingSchedule() throws Exception {
        // Initialize the database
        workingScheduleRepository.saveAndFlush(workingSchedule);

        int databaseSizeBeforeUpdate = workingScheduleRepository.findAll().size();

        // Update the workingSchedule
        WorkingSchedule updatedWorkingSchedule = workingScheduleRepository.findById(workingSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedWorkingSchedule are not directly saved in db
        em.detach(updatedWorkingSchedule);
        updatedWorkingSchedule
            .day(UPDATED_DAY)
            .isOff(UPDATED_IS_OFF)
            .startTime(UPDATED_START_TIME)
            .end(UPDATED_END);
        WorkingScheduleDTO workingScheduleDTO = workingScheduleMapper.toDto(updatedWorkingSchedule);

        restWorkingScheduleMockMvc.perform(put("/api/working-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workingScheduleDTO)))
            .andExpect(status().isOk());

        // Validate the WorkingSchedule in the database
        List<WorkingSchedule> workingScheduleList = workingScheduleRepository.findAll();
        assertThat(workingScheduleList).hasSize(databaseSizeBeforeUpdate);
        WorkingSchedule testWorkingSchedule = workingScheduleList.get(workingScheduleList.size() - 1);
        assertThat(testWorkingSchedule.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testWorkingSchedule.isIsOff()).isEqualTo(UPDATED_IS_OFF);
        assertThat(testWorkingSchedule.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testWorkingSchedule.getEnd()).isEqualTo(UPDATED_END);

        // Validate the WorkingSchedule in Elasticsearch
        verify(mockWorkingScheduleSearchRepository, times(1)).save(testWorkingSchedule);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkingSchedule() throws Exception {
        int databaseSizeBeforeUpdate = workingScheduleRepository.findAll().size();

        // Create the WorkingSchedule
        WorkingScheduleDTO workingScheduleDTO = workingScheduleMapper.toDto(workingSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkingScheduleMockMvc.perform(put("/api/working-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workingScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkingSchedule in the database
        List<WorkingSchedule> workingScheduleList = workingScheduleRepository.findAll();
        assertThat(workingScheduleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the WorkingSchedule in Elasticsearch
        verify(mockWorkingScheduleSearchRepository, times(0)).save(workingSchedule);
    }

    @Test
    @Transactional
    public void deleteWorkingSchedule() throws Exception {
        // Initialize the database
        workingScheduleRepository.saveAndFlush(workingSchedule);

        int databaseSizeBeforeDelete = workingScheduleRepository.findAll().size();

        // Delete the workingSchedule
        restWorkingScheduleMockMvc.perform(delete("/api/working-schedules/{id}", workingSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkingSchedule> workingScheduleList = workingScheduleRepository.findAll();
        assertThat(workingScheduleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the WorkingSchedule in Elasticsearch
        verify(mockWorkingScheduleSearchRepository, times(1)).deleteById(workingSchedule.getId());
    }

    @Test
    @Transactional
    public void searchWorkingSchedule() throws Exception {
        // Initialize the database
        workingScheduleRepository.saveAndFlush(workingSchedule);
        when(mockWorkingScheduleSearchRepository.search(queryStringQuery("id:" + workingSchedule.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(workingSchedule), PageRequest.of(0, 1), 1));
        // Search the workingSchedule
        restWorkingScheduleMockMvc.perform(get("/api/_search/working-schedules?query=id:" + workingSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workingSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].isOff").value(hasItem(DEFAULT_IS_OFF.booleanValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkingSchedule.class);
        WorkingSchedule workingSchedule1 = new WorkingSchedule();
        workingSchedule1.setId(1L);
        WorkingSchedule workingSchedule2 = new WorkingSchedule();
        workingSchedule2.setId(workingSchedule1.getId());
        assertThat(workingSchedule1).isEqualTo(workingSchedule2);
        workingSchedule2.setId(2L);
        assertThat(workingSchedule1).isNotEqualTo(workingSchedule2);
        workingSchedule1.setId(null);
        assertThat(workingSchedule1).isNotEqualTo(workingSchedule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkingScheduleDTO.class);
        WorkingScheduleDTO workingScheduleDTO1 = new WorkingScheduleDTO();
        workingScheduleDTO1.setId(1L);
        WorkingScheduleDTO workingScheduleDTO2 = new WorkingScheduleDTO();
        assertThat(workingScheduleDTO1).isNotEqualTo(workingScheduleDTO2);
        workingScheduleDTO2.setId(workingScheduleDTO1.getId());
        assertThat(workingScheduleDTO1).isEqualTo(workingScheduleDTO2);
        workingScheduleDTO2.setId(2L);
        assertThat(workingScheduleDTO1).isNotEqualTo(workingScheduleDTO2);
        workingScheduleDTO1.setId(null);
        assertThat(workingScheduleDTO1).isNotEqualTo(workingScheduleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(workingScheduleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(workingScheduleMapper.fromId(null)).isNull();
    }
}
