package com.omar.hmis.web.rest;

import com.omar.hmis.PatientServiceApp;
import com.omar.hmis.config.TestSecurityConfiguration;
import com.omar.hmis.domain.JobDetails;
import com.omar.hmis.repository.JobDetailsRepository;
import com.omar.hmis.repository.search.JobDetailsSearchRepository;
import com.omar.hmis.service.JobDetailsService;
import com.omar.hmis.service.dto.JobDetailsDTO;
import com.omar.hmis.service.mapper.JobDetailsMapper;
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

import com.omar.hmis.domain.enumeration.JobType;
/**
 * Integration tests for the {@link JobDetailsResource} REST controller.
 */
@SpringBootTest(classes = {PatientServiceApp.class, TestSecurityConfiguration.class})
public class JobDetailsResourceIT {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_GRADE = 1;
    private static final Integer UPDATED_GRADE = 2;

    private static final JobType DEFAULT_TYPE = JobType.CONTRACT;
    private static final JobType UPDATED_TYPE = JobType.PERMANENT;

    private static final Double DEFAULT_SALARY = 0D;
    private static final Double UPDATED_SALARY = 1D;

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    @Autowired
    private JobDetailsRepository jobDetailsRepository;

    @Autowired
    private JobDetailsMapper jobDetailsMapper;

    @Autowired
    private JobDetailsService jobDetailsService;

    /**
     * This repository is mocked in the com.omar.hmis.repository.search test package.
     *
     * @see com.omar.hmis.repository.search.JobDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private JobDetailsSearchRepository mockJobDetailsSearchRepository;

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

    private MockMvc restJobDetailsMockMvc;

    private JobDetails jobDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobDetailsResource jobDetailsResource = new JobDetailsResource(jobDetailsService);
        this.restJobDetailsMockMvc = MockMvcBuilders.standaloneSetup(jobDetailsResource)
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
    public static JobDetails createEntity(EntityManager em) {
        JobDetails jobDetails = new JobDetails()
            .designation(DEFAULT_DESIGNATION)
            .grade(DEFAULT_GRADE)
            .type(DEFAULT_TYPE)
            .salary(DEFAULT_SALARY)
            .details(DEFAULT_DETAILS);
        return jobDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobDetails createUpdatedEntity(EntityManager em) {
        JobDetails jobDetails = new JobDetails()
            .designation(UPDATED_DESIGNATION)
            .grade(UPDATED_GRADE)
            .type(UPDATED_TYPE)
            .salary(UPDATED_SALARY)
            .details(UPDATED_DETAILS);
        return jobDetails;
    }

    @BeforeEach
    public void initTest() {
        jobDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobDetails() throws Exception {
        int databaseSizeBeforeCreate = jobDetailsRepository.findAll().size();

        // Create the JobDetails
        JobDetailsDTO jobDetailsDTO = jobDetailsMapper.toDto(jobDetails);
        restJobDetailsMockMvc.perform(post("/api/job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the JobDetails in the database
        List<JobDetails> jobDetailsList = jobDetailsRepository.findAll();
        assertThat(jobDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        JobDetails testJobDetails = jobDetailsList.get(jobDetailsList.size() - 1);
        assertThat(testJobDetails.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testJobDetails.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testJobDetails.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testJobDetails.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testJobDetails.getDetails()).isEqualTo(DEFAULT_DETAILS);

        // Validate the JobDetails in Elasticsearch
        verify(mockJobDetailsSearchRepository, times(1)).save(testJobDetails);
    }

    @Test
    @Transactional
    public void createJobDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobDetailsRepository.findAll().size();

        // Create the JobDetails with an existing ID
        jobDetails.setId(1L);
        JobDetailsDTO jobDetailsDTO = jobDetailsMapper.toDto(jobDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobDetailsMockMvc.perform(post("/api/job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobDetails in the database
        List<JobDetails> jobDetailsList = jobDetailsRepository.findAll();
        assertThat(jobDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the JobDetails in Elasticsearch
        verify(mockJobDetailsSearchRepository, times(0)).save(jobDetails);
    }


    @Test
    @Transactional
    public void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobDetailsRepository.findAll().size();
        // set the field null
        jobDetails.setDesignation(null);

        // Create the JobDetails, which fails.
        JobDetailsDTO jobDetailsDTO = jobDetailsMapper.toDto(jobDetails);

        restJobDetailsMockMvc.perform(post("/api/job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<JobDetails> jobDetailsList = jobDetailsRepository.findAll();
        assertThat(jobDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGradeIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobDetailsRepository.findAll().size();
        // set the field null
        jobDetails.setGrade(null);

        // Create the JobDetails, which fails.
        JobDetailsDTO jobDetailsDTO = jobDetailsMapper.toDto(jobDetails);

        restJobDetailsMockMvc.perform(post("/api/job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<JobDetails> jobDetailsList = jobDetailsRepository.findAll();
        assertThat(jobDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobDetailsRepository.findAll().size();
        // set the field null
        jobDetails.setType(null);

        // Create the JobDetails, which fails.
        JobDetailsDTO jobDetailsDTO = jobDetailsMapper.toDto(jobDetails);

        restJobDetailsMockMvc.perform(post("/api/job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<JobDetails> jobDetailsList = jobDetailsRepository.findAll();
        assertThat(jobDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobDetailsRepository.findAll().size();
        // set the field null
        jobDetails.setSalary(null);

        // Create the JobDetails, which fails.
        JobDetailsDTO jobDetailsDTO = jobDetailsMapper.toDto(jobDetails);

        restJobDetailsMockMvc.perform(post("/api/job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<JobDetails> jobDetailsList = jobDetailsRepository.findAll();
        assertThat(jobDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDetailsIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobDetailsRepository.findAll().size();
        // set the field null
        jobDetails.setDetails(null);

        // Create the JobDetails, which fails.
        JobDetailsDTO jobDetailsDTO = jobDetailsMapper.toDto(jobDetails);

        restJobDetailsMockMvc.perform(post("/api/job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<JobDetails> jobDetailsList = jobDetailsRepository.findAll();
        assertThat(jobDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobDetails() throws Exception {
        // Initialize the database
        jobDetailsRepository.saveAndFlush(jobDetails);

        // Get all the jobDetailsList
        restJobDetailsMockMvc.perform(get("/api/job-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));
    }
    
    @Test
    @Transactional
    public void getJobDetails() throws Exception {
        // Initialize the database
        jobDetailsRepository.saveAndFlush(jobDetails);

        // Get the jobDetails
        restJobDetailsMockMvc.perform(get("/api/job-details/{id}", jobDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobDetails.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.doubleValue()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS));
    }

    @Test
    @Transactional
    public void getNonExistingJobDetails() throws Exception {
        // Get the jobDetails
        restJobDetailsMockMvc.perform(get("/api/job-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobDetails() throws Exception {
        // Initialize the database
        jobDetailsRepository.saveAndFlush(jobDetails);

        int databaseSizeBeforeUpdate = jobDetailsRepository.findAll().size();

        // Update the jobDetails
        JobDetails updatedJobDetails = jobDetailsRepository.findById(jobDetails.getId()).get();
        // Disconnect from session so that the updates on updatedJobDetails are not directly saved in db
        em.detach(updatedJobDetails);
        updatedJobDetails
            .designation(UPDATED_DESIGNATION)
            .grade(UPDATED_GRADE)
            .type(UPDATED_TYPE)
            .salary(UPDATED_SALARY)
            .details(UPDATED_DETAILS);
        JobDetailsDTO jobDetailsDTO = jobDetailsMapper.toDto(updatedJobDetails);

        restJobDetailsMockMvc.perform(put("/api/job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the JobDetails in the database
        List<JobDetails> jobDetailsList = jobDetailsRepository.findAll();
        assertThat(jobDetailsList).hasSize(databaseSizeBeforeUpdate);
        JobDetails testJobDetails = jobDetailsList.get(jobDetailsList.size() - 1);
        assertThat(testJobDetails.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testJobDetails.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testJobDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testJobDetails.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testJobDetails.getDetails()).isEqualTo(UPDATED_DETAILS);

        // Validate the JobDetails in Elasticsearch
        verify(mockJobDetailsSearchRepository, times(1)).save(testJobDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingJobDetails() throws Exception {
        int databaseSizeBeforeUpdate = jobDetailsRepository.findAll().size();

        // Create the JobDetails
        JobDetailsDTO jobDetailsDTO = jobDetailsMapper.toDto(jobDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobDetailsMockMvc.perform(put("/api/job-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobDetails in the database
        List<JobDetails> jobDetailsList = jobDetailsRepository.findAll();
        assertThat(jobDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the JobDetails in Elasticsearch
        verify(mockJobDetailsSearchRepository, times(0)).save(jobDetails);
    }

    @Test
    @Transactional
    public void deleteJobDetails() throws Exception {
        // Initialize the database
        jobDetailsRepository.saveAndFlush(jobDetails);

        int databaseSizeBeforeDelete = jobDetailsRepository.findAll().size();

        // Delete the jobDetails
        restJobDetailsMockMvc.perform(delete("/api/job-details/{id}", jobDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobDetails> jobDetailsList = jobDetailsRepository.findAll();
        assertThat(jobDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the JobDetails in Elasticsearch
        verify(mockJobDetailsSearchRepository, times(1)).deleteById(jobDetails.getId());
    }

    @Test
    @Transactional
    public void searchJobDetails() throws Exception {
        // Initialize the database
        jobDetailsRepository.saveAndFlush(jobDetails);
        when(mockJobDetailsSearchRepository.search(queryStringQuery("id:" + jobDetails.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(jobDetails), PageRequest.of(0, 1), 1));
        // Search the jobDetails
        restJobDetailsMockMvc.perform(get("/api/_search/job-details?query=id:" + jobDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobDetails.class);
        JobDetails jobDetails1 = new JobDetails();
        jobDetails1.setId(1L);
        JobDetails jobDetails2 = new JobDetails();
        jobDetails2.setId(jobDetails1.getId());
        assertThat(jobDetails1).isEqualTo(jobDetails2);
        jobDetails2.setId(2L);
        assertThat(jobDetails1).isNotEqualTo(jobDetails2);
        jobDetails1.setId(null);
        assertThat(jobDetails1).isNotEqualTo(jobDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobDetailsDTO.class);
        JobDetailsDTO jobDetailsDTO1 = new JobDetailsDTO();
        jobDetailsDTO1.setId(1L);
        JobDetailsDTO jobDetailsDTO2 = new JobDetailsDTO();
        assertThat(jobDetailsDTO1).isNotEqualTo(jobDetailsDTO2);
        jobDetailsDTO2.setId(jobDetailsDTO1.getId());
        assertThat(jobDetailsDTO1).isEqualTo(jobDetailsDTO2);
        jobDetailsDTO2.setId(2L);
        assertThat(jobDetailsDTO1).isNotEqualTo(jobDetailsDTO2);
        jobDetailsDTO1.setId(null);
        assertThat(jobDetailsDTO1).isNotEqualTo(jobDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(jobDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(jobDetailsMapper.fromId(null)).isNull();
    }
}
