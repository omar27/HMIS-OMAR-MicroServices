package com.omar.hmis.web.rest;

import com.omar.hmis.PatientServiceApp;
import com.omar.hmis.config.TestSecurityConfiguration;
import com.omar.hmis.domain.Staff;
import com.omar.hmis.domain.Department;
import com.omar.hmis.domain.JobDetails;
import com.omar.hmis.domain.StaffWorkingSchedule;
import com.omar.hmis.repository.StaffRepository;
import com.omar.hmis.repository.search.StaffSearchRepository;
import com.omar.hmis.service.StaffService;
import com.omar.hmis.service.dto.StaffDTO;
import com.omar.hmis.service.mapper.StaffMapper;
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

import com.omar.hmis.domain.enumeration.StaffType;
/**
 * Integration tests for the {@link StaffResource} REST controller.
 */
@SpringBootTest(classes = {PatientServiceApp.class, TestSecurityConfiguration.class})
public class StaffResourceIT {

    private static final StaffType DEFAULT_STAFF_TYPE = StaffType.DOCTOR;
    private static final StaffType UPDATED_STAFF_TYPE = StaffType.RECEPTIONIST;

    private static final String DEFAULT_QUALIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_QUALIFICATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_JOINING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOINING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_EXPERIENCE = 0;
    private static final Integer UPDATED_EXPERIENCE = 1;

    private static final Boolean DEFAULT_IS_REGULAR = false;
    private static final Boolean UPDATED_IS_REGULAR = true;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private StaffService staffService;

    /**
     * This repository is mocked in the com.omar.hmis.repository.search test package.
     *
     * @see com.omar.hmis.repository.search.StaffSearchRepositoryMockConfiguration
     */
    @Autowired
    private StaffSearchRepository mockStaffSearchRepository;

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

    private MockMvc restStaffMockMvc;

    private Staff staff;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StaffResource staffResource = new StaffResource(staffService);
        this.restStaffMockMvc = MockMvcBuilders.standaloneSetup(staffResource)
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
    public static Staff createEntity(EntityManager em) {
        Staff staff = new Staff()
            .staffType(DEFAULT_STAFF_TYPE)
            .qualification(DEFAULT_QUALIFICATION)
            .joiningDate(DEFAULT_JOINING_DATE)
            .experience(DEFAULT_EXPERIENCE)
            .isRegular(DEFAULT_IS_REGULAR);
        // Add required entity
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            department = DepartmentResourceIT.createEntity(em);
            em.persist(department);
            em.flush();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        staff.setDepartment(department);
        // Add required entity
        JobDetails jobDetails;
        if (TestUtil.findAll(em, JobDetails.class).isEmpty()) {
            jobDetails = JobDetailsResourceIT.createEntity(em);
            em.persist(jobDetails);
            em.flush();
        } else {
            jobDetails = TestUtil.findAll(em, JobDetails.class).get(0);
        }
        staff.setJobDetails(jobDetails);
        // Add required entity
        StaffWorkingSchedule staffWorkingSchedule;
        if (TestUtil.findAll(em, StaffWorkingSchedule.class).isEmpty()) {
            staffWorkingSchedule = StaffWorkingScheduleResourceIT.createEntity(em);
            em.persist(staffWorkingSchedule);
            em.flush();
        } else {
            staffWorkingSchedule = TestUtil.findAll(em, StaffWorkingSchedule.class).get(0);
        }
        staff.setStaffWorkingSchedule(staffWorkingSchedule);
        return staff;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Staff createUpdatedEntity(EntityManager em) {
        Staff staff = new Staff()
            .staffType(UPDATED_STAFF_TYPE)
            .qualification(UPDATED_QUALIFICATION)
            .joiningDate(UPDATED_JOINING_DATE)
            .experience(UPDATED_EXPERIENCE)
            .isRegular(UPDATED_IS_REGULAR);
        // Add required entity
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            department = DepartmentResourceIT.createUpdatedEntity(em);
            em.persist(department);
            em.flush();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        staff.setDepartment(department);
        // Add required entity
        JobDetails jobDetails;
        if (TestUtil.findAll(em, JobDetails.class).isEmpty()) {
            jobDetails = JobDetailsResourceIT.createUpdatedEntity(em);
            em.persist(jobDetails);
            em.flush();
        } else {
            jobDetails = TestUtil.findAll(em, JobDetails.class).get(0);
        }
        staff.setJobDetails(jobDetails);
        // Add required entity
        StaffWorkingSchedule staffWorkingSchedule;
        if (TestUtil.findAll(em, StaffWorkingSchedule.class).isEmpty()) {
            staffWorkingSchedule = StaffWorkingScheduleResourceIT.createUpdatedEntity(em);
            em.persist(staffWorkingSchedule);
            em.flush();
        } else {
            staffWorkingSchedule = TestUtil.findAll(em, StaffWorkingSchedule.class).get(0);
        }
        staff.setStaffWorkingSchedule(staffWorkingSchedule);
        return staff;
    }

    @BeforeEach
    public void initTest() {
        staff = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaff() throws Exception {
        int databaseSizeBeforeCreate = staffRepository.findAll().size();

        // Create the Staff
        StaffDTO staffDTO = staffMapper.toDto(staff);
        restStaffMockMvc.perform(post("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffDTO)))
            .andExpect(status().isCreated());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeCreate + 1);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getStaffType()).isEqualTo(DEFAULT_STAFF_TYPE);
        assertThat(testStaff.getQualification()).isEqualTo(DEFAULT_QUALIFICATION);
        assertThat(testStaff.getJoiningDate()).isEqualTo(DEFAULT_JOINING_DATE);
        assertThat(testStaff.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testStaff.isIsRegular()).isEqualTo(DEFAULT_IS_REGULAR);

        // Validate the Staff in Elasticsearch
        verify(mockStaffSearchRepository, times(1)).save(testStaff);
    }

    @Test
    @Transactional
    public void createStaffWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staffRepository.findAll().size();

        // Create the Staff with an existing ID
        staff.setId(1L);
        StaffDTO staffDTO = staffMapper.toDto(staff);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaffMockMvc.perform(post("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeCreate);

        // Validate the Staff in Elasticsearch
        verify(mockStaffSearchRepository, times(0)).save(staff);
    }


    @Test
    @Transactional
    public void checkStaffTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffRepository.findAll().size();
        // set the field null
        staff.setStaffType(null);

        // Create the Staff, which fails.
        StaffDTO staffDTO = staffMapper.toDto(staff);

        restStaffMockMvc.perform(post("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffDTO)))
            .andExpect(status().isBadRequest());

        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQualificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffRepository.findAll().size();
        // set the field null
        staff.setQualification(null);

        // Create the Staff, which fails.
        StaffDTO staffDTO = staffMapper.toDto(staff);

        restStaffMockMvc.perform(post("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffDTO)))
            .andExpect(status().isBadRequest());

        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJoiningDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffRepository.findAll().size();
        // set the field null
        staff.setJoiningDate(null);

        // Create the Staff, which fails.
        StaffDTO staffDTO = staffMapper.toDto(staff);

        restStaffMockMvc.perform(post("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffDTO)))
            .andExpect(status().isBadRequest());

        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExperienceIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffRepository.findAll().size();
        // set the field null
        staff.setExperience(null);

        // Create the Staff, which fails.
        StaffDTO staffDTO = staffMapper.toDto(staff);

        restStaffMockMvc.perform(post("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffDTO)))
            .andExpect(status().isBadRequest());

        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        // Get all the staffList
        restStaffMockMvc.perform(get("/api/staff?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staff.getId().intValue())))
            .andExpect(jsonPath("$.[*].staffType").value(hasItem(DEFAULT_STAFF_TYPE.toString())))
            .andExpect(jsonPath("$.[*].qualification").value(hasItem(DEFAULT_QUALIFICATION)))
            .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].isRegular").value(hasItem(DEFAULT_IS_REGULAR.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        // Get the staff
        restStaffMockMvc.perform(get("/api/staff/{id}", staff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(staff.getId().intValue()))
            .andExpect(jsonPath("$.staffType").value(DEFAULT_STAFF_TYPE.toString()))
            .andExpect(jsonPath("$.qualification").value(DEFAULT_QUALIFICATION))
            .andExpect(jsonPath("$.joiningDate").value(DEFAULT_JOINING_DATE.toString()))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE))
            .andExpect(jsonPath("$.isRegular").value(DEFAULT_IS_REGULAR.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStaff() throws Exception {
        // Get the staff
        restStaffMockMvc.perform(get("/api/staff/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        int databaseSizeBeforeUpdate = staffRepository.findAll().size();

        // Update the staff
        Staff updatedStaff = staffRepository.findById(staff.getId()).get();
        // Disconnect from session so that the updates on updatedStaff are not directly saved in db
        em.detach(updatedStaff);
        updatedStaff
            .staffType(UPDATED_STAFF_TYPE)
            .qualification(UPDATED_QUALIFICATION)
            .joiningDate(UPDATED_JOINING_DATE)
            .experience(UPDATED_EXPERIENCE)
            .isRegular(UPDATED_IS_REGULAR);
        StaffDTO staffDTO = staffMapper.toDto(updatedStaff);

        restStaffMockMvc.perform(put("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffDTO)))
            .andExpect(status().isOk());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getStaffType()).isEqualTo(UPDATED_STAFF_TYPE);
        assertThat(testStaff.getQualification()).isEqualTo(UPDATED_QUALIFICATION);
        assertThat(testStaff.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testStaff.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testStaff.isIsRegular()).isEqualTo(UPDATED_IS_REGULAR);

        // Validate the Staff in Elasticsearch
        verify(mockStaffSearchRepository, times(1)).save(testStaff);
    }

    @Test
    @Transactional
    public void updateNonExistingStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();

        // Create the Staff
        StaffDTO staffDTO = staffMapper.toDto(staff);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaffMockMvc.perform(put("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staffDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Staff in Elasticsearch
        verify(mockStaffSearchRepository, times(0)).save(staff);
    }

    @Test
    @Transactional
    public void deleteStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        int databaseSizeBeforeDelete = staffRepository.findAll().size();

        // Delete the staff
        restStaffMockMvc.perform(delete("/api/staff/{id}", staff.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Staff in Elasticsearch
        verify(mockStaffSearchRepository, times(1)).deleteById(staff.getId());
    }

    @Test
    @Transactional
    public void searchStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);
        when(mockStaffSearchRepository.search(queryStringQuery("id:" + staff.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(staff), PageRequest.of(0, 1), 1));
        // Search the staff
        restStaffMockMvc.perform(get("/api/_search/staff?query=id:" + staff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staff.getId().intValue())))
            .andExpect(jsonPath("$.[*].staffType").value(hasItem(DEFAULT_STAFF_TYPE.toString())))
            .andExpect(jsonPath("$.[*].qualification").value(hasItem(DEFAULT_QUALIFICATION)))
            .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].isRegular").value(hasItem(DEFAULT_IS_REGULAR.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Staff.class);
        Staff staff1 = new Staff();
        staff1.setId(1L);
        Staff staff2 = new Staff();
        staff2.setId(staff1.getId());
        assertThat(staff1).isEqualTo(staff2);
        staff2.setId(2L);
        assertThat(staff1).isNotEqualTo(staff2);
        staff1.setId(null);
        assertThat(staff1).isNotEqualTo(staff2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaffDTO.class);
        StaffDTO staffDTO1 = new StaffDTO();
        staffDTO1.setId(1L);
        StaffDTO staffDTO2 = new StaffDTO();
        assertThat(staffDTO1).isNotEqualTo(staffDTO2);
        staffDTO2.setId(staffDTO1.getId());
        assertThat(staffDTO1).isEqualTo(staffDTO2);
        staffDTO2.setId(2L);
        assertThat(staffDTO1).isNotEqualTo(staffDTO2);
        staffDTO1.setId(null);
        assertThat(staffDTO1).isNotEqualTo(staffDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(staffMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(staffMapper.fromId(null)).isNull();
    }
}
