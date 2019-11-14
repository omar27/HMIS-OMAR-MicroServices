package com.omar.hmis.web.rest;

import com.omar.hmis.PatientServiceApp;
import com.omar.hmis.config.TestSecurityConfiguration;
import com.omar.hmis.domain.PersonalInfo;
import com.omar.hmis.repository.PersonalInfoRepository;
import com.omar.hmis.repository.search.PersonalInfoSearchRepository;
import com.omar.hmis.service.PersonalInfoService;
import com.omar.hmis.service.dto.PersonalInfoDTO;
import com.omar.hmis.service.mapper.PersonalInfoMapper;
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

import com.omar.hmis.domain.enumeration.Gender;
import com.omar.hmis.domain.enumeration.EntityType;
/**
 * Integration tests for the {@link PersonalInfoResource} REST controller.
 */
@SpringBootTest(classes = {PatientServiceApp.class, TestSecurityConfiguration.class})
public class PersonalInfoResourceIT {

    private static final String DEFAULT_CNIC = "7";
    private static final String UPDATED_CNIC = "5";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "5";
    private static final String UPDATED_PHONE_NUMBER = "";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final EntityType DEFAULT_ENTITY_TYPE = EntityType.PATIENT;
    private static final EntityType UPDATED_ENTITY_TYPE = EntityType.STAFF;

    private static final Integer DEFAULT_ENTITY_ID = 1;
    private static final Integer UPDATED_ENTITY_ID = 2;

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private PersonalInfoMapper personalInfoMapper;

    @Autowired
    private PersonalInfoService personalInfoService;

    /**
     * This repository is mocked in the com.omar.hmis.repository.search test package.
     *
     * @see com.omar.hmis.repository.search.PersonalInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private PersonalInfoSearchRepository mockPersonalInfoSearchRepository;

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

    private MockMvc restPersonalInfoMockMvc;

    private PersonalInfo personalInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonalInfoResource personalInfoResource = new PersonalInfoResource(personalInfoService);
        this.restPersonalInfoMockMvc = MockMvcBuilders.standaloneSetup(personalInfoResource)
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
    public static PersonalInfo createEntity(EntityManager em) {
        PersonalInfo personalInfo = new PersonalInfo()
            .cnic(DEFAULT_CNIC)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .email(DEFAULT_EMAIL)
            .gender(DEFAULT_GENDER)
            .age(DEFAULT_AGE)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .entityType(DEFAULT_ENTITY_TYPE)
            .entityId(DEFAULT_ENTITY_ID);
        return personalInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonalInfo createUpdatedEntity(EntityManager em) {
        PersonalInfo personalInfo = new PersonalInfo()
            .cnic(UPDATED_CNIC)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .gender(UPDATED_GENDER)
            .age(UPDATED_AGE)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID);
        return personalInfo;
    }

    @BeforeEach
    public void initTest() {
        personalInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonalInfo() throws Exception {
        int databaseSizeBeforeCreate = personalInfoRepository.findAll().size();

        // Create the PersonalInfo
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);
        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeCreate + 1);
        PersonalInfo testPersonalInfo = personalInfoList.get(personalInfoList.size() - 1);
        assertThat(testPersonalInfo.getCnic()).isEqualTo(DEFAULT_CNIC);
        assertThat(testPersonalInfo.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPersonalInfo.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPersonalInfo.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPersonalInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPersonalInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPersonalInfo.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPersonalInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPersonalInfo.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testPersonalInfo.getEntityType()).isEqualTo(DEFAULT_ENTITY_TYPE);
        assertThat(testPersonalInfo.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);

        // Validate the PersonalInfo in Elasticsearch
        verify(mockPersonalInfoSearchRepository, times(1)).save(testPersonalInfo);
    }

    @Test
    @Transactional
    public void createPersonalInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personalInfoRepository.findAll().size();

        // Create the PersonalInfo with an existing ID
        personalInfo.setId(1L);
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the PersonalInfo in Elasticsearch
        verify(mockPersonalInfoSearchRepository, times(0)).save(personalInfo);
    }


    @Test
    @Transactional
    public void checkCnicIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInfoRepository.findAll().size();
        // set the field null
        personalInfo.setCnic(null);

        // Create the PersonalInfo, which fails.
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInfoRepository.findAll().size();
        // set the field null
        personalInfo.setFirstName(null);

        // Create the PersonalInfo, which fails.
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInfoRepository.findAll().size();
        // set the field null
        personalInfo.setLastName(null);

        // Create the PersonalInfo, which fails.
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInfoRepository.findAll().size();
        // set the field null
        personalInfo.setPhoneNumber(null);

        // Create the PersonalInfo, which fails.
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInfoRepository.findAll().size();
        // set the field null
        personalInfo.setEmail(null);

        // Create the PersonalInfo, which fails.
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInfoRepository.findAll().size();
        // set the field null
        personalInfo.setGender(null);

        // Create the PersonalInfo, which fails.
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInfoRepository.findAll().size();
        // set the field null
        personalInfo.setAge(null);

        // Create the PersonalInfo, which fails.
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInfoRepository.findAll().size();
        // set the field null
        personalInfo.setAddress(null);

        // Create the PersonalInfo, which fails.
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInfoRepository.findAll().size();
        // set the field null
        personalInfo.setCity(null);

        // Create the PersonalInfo, which fails.
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEntityTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInfoRepository.findAll().size();
        // set the field null
        personalInfo.setEntityType(null);

        // Create the PersonalInfo, which fails.
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEntityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalInfoRepository.findAll().size();
        // set the field null
        personalInfo.setEntityId(null);

        // Create the PersonalInfo, which fails.
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        restPersonalInfoMockMvc.perform(post("/api/personal-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonalInfos() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get all the personalInfoList
        restPersonalInfoMockMvc.perform(get("/api/personal-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].cnic").value(hasItem(DEFAULT_CNIC)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID)));
    }
    
    @Test
    @Transactional
    public void getPersonalInfo() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        // Get the personalInfo
        restPersonalInfoMockMvc.perform(get("/api/personal-infos/{id}", personalInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personalInfo.getId().intValue()))
            .andExpect(jsonPath("$.cnic").value(DEFAULT_CNIC))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.entityType").value(DEFAULT_ENTITY_TYPE.toString()))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID));
    }

    @Test
    @Transactional
    public void getNonExistingPersonalInfo() throws Exception {
        // Get the personalInfo
        restPersonalInfoMockMvc.perform(get("/api/personal-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonalInfo() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        int databaseSizeBeforeUpdate = personalInfoRepository.findAll().size();

        // Update the personalInfo
        PersonalInfo updatedPersonalInfo = personalInfoRepository.findById(personalInfo.getId()).get();
        // Disconnect from session so that the updates on updatedPersonalInfo are not directly saved in db
        em.detach(updatedPersonalInfo);
        updatedPersonalInfo
            .cnic(UPDATED_CNIC)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .gender(UPDATED_GENDER)
            .age(UPDATED_AGE)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID);
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(updatedPersonalInfo);

        restPersonalInfoMockMvc.perform(put("/api/personal-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO)))
            .andExpect(status().isOk());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeUpdate);
        PersonalInfo testPersonalInfo = personalInfoList.get(personalInfoList.size() - 1);
        assertThat(testPersonalInfo.getCnic()).isEqualTo(UPDATED_CNIC);
        assertThat(testPersonalInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPersonalInfo.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPersonalInfo.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPersonalInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPersonalInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPersonalInfo.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPersonalInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPersonalInfo.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testPersonalInfo.getEntityType()).isEqualTo(UPDATED_ENTITY_TYPE);
        assertThat(testPersonalInfo.getEntityId()).isEqualTo(UPDATED_ENTITY_ID);

        // Validate the PersonalInfo in Elasticsearch
        verify(mockPersonalInfoSearchRepository, times(1)).save(testPersonalInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonalInfo() throws Exception {
        int databaseSizeBeforeUpdate = personalInfoRepository.findAll().size();

        // Create the PersonalInfo
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toDto(personalInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalInfoMockMvc.perform(put("/api/personal-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonalInfo in the database
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PersonalInfo in Elasticsearch
        verify(mockPersonalInfoSearchRepository, times(0)).save(personalInfo);
    }

    @Test
    @Transactional
    public void deletePersonalInfo() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);

        int databaseSizeBeforeDelete = personalInfoRepository.findAll().size();

        // Delete the personalInfo
        restPersonalInfoMockMvc.perform(delete("/api/personal-infos/{id}", personalInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonalInfo> personalInfoList = personalInfoRepository.findAll();
        assertThat(personalInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PersonalInfo in Elasticsearch
        verify(mockPersonalInfoSearchRepository, times(1)).deleteById(personalInfo.getId());
    }

    @Test
    @Transactional
    public void searchPersonalInfo() throws Exception {
        // Initialize the database
        personalInfoRepository.saveAndFlush(personalInfo);
        when(mockPersonalInfoSearchRepository.search(queryStringQuery("id:" + personalInfo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(personalInfo), PageRequest.of(0, 1), 1));
        // Search the personalInfo
        restPersonalInfoMockMvc.perform(get("/api/_search/personal-infos?query=id:" + personalInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].cnic").value(hasItem(DEFAULT_CNIC)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonalInfo.class);
        PersonalInfo personalInfo1 = new PersonalInfo();
        personalInfo1.setId(1L);
        PersonalInfo personalInfo2 = new PersonalInfo();
        personalInfo2.setId(personalInfo1.getId());
        assertThat(personalInfo1).isEqualTo(personalInfo2);
        personalInfo2.setId(2L);
        assertThat(personalInfo1).isNotEqualTo(personalInfo2);
        personalInfo1.setId(null);
        assertThat(personalInfo1).isNotEqualTo(personalInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonalInfoDTO.class);
        PersonalInfoDTO personalInfoDTO1 = new PersonalInfoDTO();
        personalInfoDTO1.setId(1L);
        PersonalInfoDTO personalInfoDTO2 = new PersonalInfoDTO();
        assertThat(personalInfoDTO1).isNotEqualTo(personalInfoDTO2);
        personalInfoDTO2.setId(personalInfoDTO1.getId());
        assertThat(personalInfoDTO1).isEqualTo(personalInfoDTO2);
        personalInfoDTO2.setId(2L);
        assertThat(personalInfoDTO1).isNotEqualTo(personalInfoDTO2);
        personalInfoDTO1.setId(null);
        assertThat(personalInfoDTO1).isNotEqualTo(personalInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personalInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personalInfoMapper.fromId(null)).isNull();
    }
}
