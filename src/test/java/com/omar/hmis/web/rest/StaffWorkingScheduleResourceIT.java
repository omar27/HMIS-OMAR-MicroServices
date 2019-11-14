package com.omar.hmis.web.rest;

import com.omar.hmis.PatientServiceApp;
import com.omar.hmis.config.TestSecurityConfiguration;
import com.omar.hmis.domain.StaffWorkingSchedule;
import com.omar.hmis.domain.Staff;
import com.omar.hmis.domain.WorkingSchedule;
import com.omar.hmis.repository.StaffWorkingScheduleRepository;
import com.omar.hmis.repository.search.StaffWorkingScheduleSearchRepository;
import com.omar.hmis.service.StaffWorkingScheduleService;
import com.omar.hmis.service.dto.StaffWorkingScheduleDTO;
import com.omar.hmis.service.mapper.StaffWorkingScheduleMapper;
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

/**
 * Integration tests for the {@link StaffWorkingScheduleResource} REST controller.
 */
@SpringBootTest(classes = {PatientServiceApp.class, TestSecurityConfiguration.class})
public class StaffWorkingScheduleResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private StaffWorkingScheduleRepository staffWorkingScheduleRepository;

    @Autowired
    private StaffWorkingScheduleMapper staffWorkingScheduleMapper;

    @Autowired
    private StaffWorkingScheduleService staffWorkingScheduleService;

    /**
     * This repository is mocked in the com.omar.hmis.repository.search test package.
     *
     * @see com.omar.hmis.repository.search.StaffWorkingScheduleSearchRepositoryMockConfiguration
     */
    @Autowired
    private StaffWorkingScheduleSearchRepository mockStaffWorkingScheduleSearchRepository;

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

    private MockMvc restStaffWorkingScheduleMockMvc;

    private StaffWorkingSchedule staffWorkingSchedule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StaffWorkingScheduleResource staffWorkingScheduleResource = new StaffWorkingScheduleResource(staffWorkingScheduleService);
        this.restStaffWorkingScheduleMockMvc = MockMvcBuilders.standaloneSetup(staffWorkingScheduleResource)
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
    public static StaffWorkingSchedule createEntity(EntityManager em) {
        StaffWorkingSchedule staffWorkingSchedule = new StaffWorkingSchedule()
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Staff staff;
        if (TestUtil.findAll(em, Staff.class).isEmpty()) {
            staff = StaffResourceIT.createEntity(em);
            em.persist(staff);
            em.flush();
        } else {
            staff = TestUtil.findAll(em, Staff.class).get(0);
        }
        staffWorkingSchedule.setStaff(staff);
        // Add required entity
        WorkingSchedule workingSchedule;
        if (TestUtil.findAll(em, WorkingSchedule.class).isEmpty()) {
            workingSchedule = WorkingScheduleResourceIT.createEntity(em);
            em.persist(workingSchedule);
            em.flush();
        } else {
            workingSchedule = TestUtil.findAll(em, WorkingSchedule.class).get(0);
        }
        staffWorkingSchedule.setWorkingSchedule(workingSchedule);
        return staffWorkingSchedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaffWorkingSchedule createUpdatedEntity(EntityManager em) {
        StaffWorkingSchedule staffWorkingSchedule = new StaffWorkingSchedule()
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        Staff staff;
        if (TestUtil.findAll(em, Staff.class).isEmpty()) {
            staff = StaffResourceIT.createUpdatedEntity(em);
            em.persist(staff);
            em.flush();
        } else {
            staff = TestUtil.findAll(em, Staff.class).get(0);
        }
        staffWorkingSchedule.setStaff(staff);
        // Add required entity
        WorkingSchedule workingSchedule;
        if (TestUtil.findAll(em, WorkingSchedule.class).isEmpty()) {
            workingSchedule = WorkingScheduleResourceIT.createUpdatedEntity(em);
            em.persist(workingSchedule);
            em.flush();
        } else {
            workingSchedule = TestUtil.findAll(em, WorkingSchedule.class).get(0);
        }
        staffWorkingSchedule.setWorkingSchedule(workingSchedule);
        return staffWorkingSchedule;
    }

    @BeforeEach
    public void initTest() {
        staffWorkingSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaffWorkingSchedule() throws Exception {
        int databaseSizeBeforeCreate = staffWorkingScheduleRepository.findAll().size();

        // Create the StaffWorkingSchedule
        StaffWorkingScheduleDTO staffWorkingScheduleDTO = staffWorkingScheduleMapper.toDto(staffWorkingSchedule);
        restStaffWorkingScheduleMockMvc.perform(post("/api/staff-working-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffWorkingScheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the StaffWorkingSchedule in the database
        List<StaffWorkingSchedule> staffWorkingScheduleList = staffWorkingScheduleRepository.findAll();
        assertThat(staffWorkingScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        StaffWorkingSchedule testStaffWorkingSchedule = staffWorkingScheduleList.get(staffWorkingScheduleList.size() - 1);
        assertThat(testStaffWorkingSchedule.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the StaffWorkingSchedule in Elasticsearch
        verify(mockStaffWorkingScheduleSearchRepository, times(1)).save(testStaffWorkingSchedule);
    }

    @Test
    @Transactional
    public void createStaffWorkingScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staffWorkingScheduleRepository.findAll().size();

        // Create the StaffWorkingSchedule with an existing ID
        staffWorkingSchedule.setId(1L);
        StaffWorkingScheduleDTO staffWorkingScheduleDTO = staffWorkingScheduleMapper.toDto(staffWorkingSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaffWorkingScheduleMockMvc.perform(post("/api/staff-working-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffWorkingScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StaffWorkingSchedule in the database
        List<StaffWorkingSchedule> staffWorkingScheduleList = staffWorkingScheduleRepository.findAll();
        assertThat(staffWorkingScheduleList).hasSize(databaseSizeBeforeCreate);

        // Validate the StaffWorkingSchedule in Elasticsearch
        verify(mockStaffWorkingScheduleSearchRepository, times(0)).save(staffWorkingSchedule);
    }


    @Test
    @Transactional
    public void getAllStaffWorkingSchedules() throws Exception {
        // Initialize the database
        staffWorkingScheduleRepository.saveAndFlush(staffWorkingSchedule);

        // Get all the staffWorkingScheduleList
        restStaffWorkingScheduleMockMvc.perform(get("/api/staff-working-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staffWorkingSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getStaffWorkingSchedule() throws Exception {
        // Initialize the database
        staffWorkingScheduleRepository.saveAndFlush(staffWorkingSchedule);

        // Get the staffWorkingSchedule
        restStaffWorkingScheduleMockMvc.perform(get("/api/staff-working-schedules/{id}", staffWorkingSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(staffWorkingSchedule.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingStaffWorkingSchedule() throws Exception {
        // Get the staffWorkingSchedule
        restStaffWorkingScheduleMockMvc.perform(get("/api/staff-working-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaffWorkingSchedule() throws Exception {
        // Initialize the database
        staffWorkingScheduleRepository.saveAndFlush(staffWorkingSchedule);

        int databaseSizeBeforeUpdate = staffWorkingScheduleRepository.findAll().size();

        // Update the staffWorkingSchedule
        StaffWorkingSchedule updatedStaffWorkingSchedule = staffWorkingScheduleRepository.findById(staffWorkingSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedStaffWorkingSchedule are not directly saved in db
        em.detach(updatedStaffWorkingSchedule);
        updatedStaffWorkingSchedule
            .description(UPDATED_DESCRIPTION);
        StaffWorkingScheduleDTO staffWorkingScheduleDTO = staffWorkingScheduleMapper.toDto(updatedStaffWorkingSchedule);

        restStaffWorkingScheduleMockMvc.perform(put("/api/staff-working-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffWorkingScheduleDTO)))
            .andExpect(status().isOk());

        // Validate the StaffWorkingSchedule in the database
        List<StaffWorkingSchedule> staffWorkingScheduleList = staffWorkingScheduleRepository.findAll();
        assertThat(staffWorkingScheduleList).hasSize(databaseSizeBeforeUpdate);
        StaffWorkingSchedule testStaffWorkingSchedule = staffWorkingScheduleList.get(staffWorkingScheduleList.size() - 1);
        assertThat(testStaffWorkingSchedule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the StaffWorkingSchedule in Elasticsearch
        verify(mockStaffWorkingScheduleSearchRepository, times(1)).save(testStaffWorkingSchedule);
    }

    @Test
    @Transactional
    public void updateNonExistingStaffWorkingSchedule() throws Exception {
        int databaseSizeBeforeUpdate = staffWorkingScheduleRepository.findAll().size();

        // Create the StaffWorkingSchedule
        StaffWorkingScheduleDTO staffWorkingScheduleDTO = staffWorkingScheduleMapper.toDto(staffWorkingSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaffWorkingScheduleMockMvc.perform(put("/api/staff-working-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffWorkingScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StaffWorkingSchedule in the database
        List<StaffWorkingSchedule> staffWorkingScheduleList = staffWorkingScheduleRepository.findAll();
        assertThat(staffWorkingScheduleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StaffWorkingSchedule in Elasticsearch
        verify(mockStaffWorkingScheduleSearchRepository, times(0)).save(staffWorkingSchedule);
    }

    @Test
    @Transactional
    public void deleteStaffWorkingSchedule() throws Exception {
        // Initialize the database
        staffWorkingScheduleRepository.saveAndFlush(staffWorkingSchedule);

        int databaseSizeBeforeDelete = staffWorkingScheduleRepository.findAll().size();

        // Delete the staffWorkingSchedule
        restStaffWorkingScheduleMockMvc.perform(delete("/api/staff-working-schedules/{id}", staffWorkingSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StaffWorkingSchedule> staffWorkingScheduleList = staffWorkingScheduleRepository.findAll();
        assertThat(staffWorkingScheduleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StaffWorkingSchedule in Elasticsearch
        verify(mockStaffWorkingScheduleSearchRepository, times(1)).deleteById(staffWorkingSchedule.getId());
    }

    @Test
    @Transactional
    public void searchStaffWorkingSchedule() throws Exception {
        // Initialize the database
        staffWorkingScheduleRepository.saveAndFlush(staffWorkingSchedule);
        when(mockStaffWorkingScheduleSearchRepository.search(queryStringQuery("id:" + staffWorkingSchedule.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(staffWorkingSchedule), PageRequest.of(0, 1), 1));
        // Search the staffWorkingSchedule
        restStaffWorkingScheduleMockMvc.perform(get("/api/_search/staff-working-schedules?query=id:" + staffWorkingSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staffWorkingSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaffWorkingSchedule.class);
        StaffWorkingSchedule staffWorkingSchedule1 = new StaffWorkingSchedule();
        staffWorkingSchedule1.setId(1L);
        StaffWorkingSchedule staffWorkingSchedule2 = new StaffWorkingSchedule();
        staffWorkingSchedule2.setId(staffWorkingSchedule1.getId());
        assertThat(staffWorkingSchedule1).isEqualTo(staffWorkingSchedule2);
        staffWorkingSchedule2.setId(2L);
        assertThat(staffWorkingSchedule1).isNotEqualTo(staffWorkingSchedule2);
        staffWorkingSchedule1.setId(null);
        assertThat(staffWorkingSchedule1).isNotEqualTo(staffWorkingSchedule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaffWorkingScheduleDTO.class);
        StaffWorkingScheduleDTO staffWorkingScheduleDTO1 = new StaffWorkingScheduleDTO();
        staffWorkingScheduleDTO1.setId(1L);
        StaffWorkingScheduleDTO staffWorkingScheduleDTO2 = new StaffWorkingScheduleDTO();
        assertThat(staffWorkingScheduleDTO1).isNotEqualTo(staffWorkingScheduleDTO2);
        staffWorkingScheduleDTO2.setId(staffWorkingScheduleDTO1.getId());
        assertThat(staffWorkingScheduleDTO1).isEqualTo(staffWorkingScheduleDTO2);
        staffWorkingScheduleDTO2.setId(2L);
        assertThat(staffWorkingScheduleDTO1).isNotEqualTo(staffWorkingScheduleDTO2);
        staffWorkingScheduleDTO1.setId(null);
        assertThat(staffWorkingScheduleDTO1).isNotEqualTo(staffWorkingScheduleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(staffWorkingScheduleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(staffWorkingScheduleMapper.fromId(null)).isNull();
    }
}
