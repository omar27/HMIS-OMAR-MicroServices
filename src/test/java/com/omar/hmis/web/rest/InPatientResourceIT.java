package com.omar.hmis.web.rest;

import com.omar.hmis.PatientServiceApp;
import com.omar.hmis.config.TestSecurityConfiguration;
import com.omar.hmis.domain.InPatient;
import com.omar.hmis.domain.Room;
import com.omar.hmis.domain.Staff;
import com.omar.hmis.domain.Patient;
import com.omar.hmis.repository.InPatientRepository;
import com.omar.hmis.repository.search.InPatientSearchRepository;
import com.omar.hmis.service.InPatientService;
import com.omar.hmis.service.dto.InPatientDTO;
import com.omar.hmis.service.mapper.InPatientMapper;
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

/**
 * Integration tests for the {@link InPatientResource} REST controller.
 */
@SpringBootTest(classes = {PatientServiceApp.class, TestSecurityConfiguration.class})
public class InPatientResourceIT {

    private static final LocalDate DEFAULT_ADMIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADMIT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DISCHARGE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DISCHARGE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private InPatientRepository inPatientRepository;

    @Autowired
    private InPatientMapper inPatientMapper;

    @Autowired
    private InPatientService inPatientService;

    /**
     * This repository is mocked in the com.omar.hmis.repository.search test package.
     *
     * @see com.omar.hmis.repository.search.InPatientSearchRepositoryMockConfiguration
     */
    @Autowired
    private InPatientSearchRepository mockInPatientSearchRepository;

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

    private MockMvc restInPatientMockMvc;

    private InPatient inPatient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InPatientResource inPatientResource = new InPatientResource(inPatientService);
        this.restInPatientMockMvc = MockMvcBuilders.standaloneSetup(inPatientResource)
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
    public static InPatient createEntity(EntityManager em) {
        InPatient inPatient = new InPatient()
            .admitDate(DEFAULT_ADMIT_DATE)
            .dischargeDate(DEFAULT_DISCHARGE_DATE);
        // Add required entity
        Room room;
        if (TestUtil.findAll(em, Room.class).isEmpty()) {
            room = RoomResourceIT.createEntity(em);
            em.persist(room);
            em.flush();
        } else {
            room = TestUtil.findAll(em, Room.class).get(0);
        }
        inPatient.setRoom(room);
        // Add required entity
        Staff staff;
        if (TestUtil.findAll(em, Staff.class).isEmpty()) {
            staff = StaffResourceIT.createEntity(em);
            em.persist(staff);
            em.flush();
        } else {
            staff = TestUtil.findAll(em, Staff.class).get(0);
        }
        inPatient.setSuggestedBy(staff);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        inPatient.setPatient(patient);
        return inPatient;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InPatient createUpdatedEntity(EntityManager em) {
        InPatient inPatient = new InPatient()
            .admitDate(UPDATED_ADMIT_DATE)
            .dischargeDate(UPDATED_DISCHARGE_DATE);
        // Add required entity
        Room room;
        if (TestUtil.findAll(em, Room.class).isEmpty()) {
            room = RoomResourceIT.createUpdatedEntity(em);
            em.persist(room);
            em.flush();
        } else {
            room = TestUtil.findAll(em, Room.class).get(0);
        }
        inPatient.setRoom(room);
        // Add required entity
        Staff staff;
        if (TestUtil.findAll(em, Staff.class).isEmpty()) {
            staff = StaffResourceIT.createUpdatedEntity(em);
            em.persist(staff);
            em.flush();
        } else {
            staff = TestUtil.findAll(em, Staff.class).get(0);
        }
        inPatient.setSuggestedBy(staff);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createUpdatedEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        inPatient.setPatient(patient);
        return inPatient;
    }

    @BeforeEach
    public void initTest() {
        inPatient = createEntity(em);
    }

    @Test
    @Transactional
    public void createInPatient() throws Exception {
        int databaseSizeBeforeCreate = inPatientRepository.findAll().size();

        // Create the InPatient
        InPatientDTO inPatientDTO = inPatientMapper.toDto(inPatient);
        restInPatientMockMvc.perform(post("/api/in-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inPatientDTO)))
            .andExpect(status().isCreated());

        // Validate the InPatient in the database
        List<InPatient> inPatientList = inPatientRepository.findAll();
        assertThat(inPatientList).hasSize(databaseSizeBeforeCreate + 1);
        InPatient testInPatient = inPatientList.get(inPatientList.size() - 1);
        assertThat(testInPatient.getAdmitDate()).isEqualTo(DEFAULT_ADMIT_DATE);
        assertThat(testInPatient.getDischargeDate()).isEqualTo(DEFAULT_DISCHARGE_DATE);

        // Validate the InPatient in Elasticsearch
        verify(mockInPatientSearchRepository, times(1)).save(testInPatient);
    }

    @Test
    @Transactional
    public void createInPatientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inPatientRepository.findAll().size();

        // Create the InPatient with an existing ID
        inPatient.setId(1L);
        InPatientDTO inPatientDTO = inPatientMapper.toDto(inPatient);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInPatientMockMvc.perform(post("/api/in-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inPatientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InPatient in the database
        List<InPatient> inPatientList = inPatientRepository.findAll();
        assertThat(inPatientList).hasSize(databaseSizeBeforeCreate);

        // Validate the InPatient in Elasticsearch
        verify(mockInPatientSearchRepository, times(0)).save(inPatient);
    }


    @Test
    @Transactional
    public void checkAdmitDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = inPatientRepository.findAll().size();
        // set the field null
        inPatient.setAdmitDate(null);

        // Create the InPatient, which fails.
        InPatientDTO inPatientDTO = inPatientMapper.toDto(inPatient);

        restInPatientMockMvc.perform(post("/api/in-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inPatientDTO)))
            .andExpect(status().isBadRequest());

        List<InPatient> inPatientList = inPatientRepository.findAll();
        assertThat(inPatientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDischargeDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = inPatientRepository.findAll().size();
        // set the field null
        inPatient.setDischargeDate(null);

        // Create the InPatient, which fails.
        InPatientDTO inPatientDTO = inPatientMapper.toDto(inPatient);

        restInPatientMockMvc.perform(post("/api/in-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inPatientDTO)))
            .andExpect(status().isBadRequest());

        List<InPatient> inPatientList = inPatientRepository.findAll();
        assertThat(inPatientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInPatients() throws Exception {
        // Initialize the database
        inPatientRepository.saveAndFlush(inPatient);

        // Get all the inPatientList
        restInPatientMockMvc.perform(get("/api/in-patients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inPatient.getId().intValue())))
            .andExpect(jsonPath("$.[*].admitDate").value(hasItem(DEFAULT_ADMIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dischargeDate").value(hasItem(DEFAULT_DISCHARGE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getInPatient() throws Exception {
        // Initialize the database
        inPatientRepository.saveAndFlush(inPatient);

        // Get the inPatient
        restInPatientMockMvc.perform(get("/api/in-patients/{id}", inPatient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inPatient.getId().intValue()))
            .andExpect(jsonPath("$.admitDate").value(DEFAULT_ADMIT_DATE.toString()))
            .andExpect(jsonPath("$.dischargeDate").value(DEFAULT_DISCHARGE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInPatient() throws Exception {
        // Get the inPatient
        restInPatientMockMvc.perform(get("/api/in-patients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInPatient() throws Exception {
        // Initialize the database
        inPatientRepository.saveAndFlush(inPatient);

        int databaseSizeBeforeUpdate = inPatientRepository.findAll().size();

        // Update the inPatient
        InPatient updatedInPatient = inPatientRepository.findById(inPatient.getId()).get();
        // Disconnect from session so that the updates on updatedInPatient are not directly saved in db
        em.detach(updatedInPatient);
        updatedInPatient
            .admitDate(UPDATED_ADMIT_DATE)
            .dischargeDate(UPDATED_DISCHARGE_DATE);
        InPatientDTO inPatientDTO = inPatientMapper.toDto(updatedInPatient);

        restInPatientMockMvc.perform(put("/api/in-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inPatientDTO)))
            .andExpect(status().isOk());

        // Validate the InPatient in the database
        List<InPatient> inPatientList = inPatientRepository.findAll();
        assertThat(inPatientList).hasSize(databaseSizeBeforeUpdate);
        InPatient testInPatient = inPatientList.get(inPatientList.size() - 1);
        assertThat(testInPatient.getAdmitDate()).isEqualTo(UPDATED_ADMIT_DATE);
        assertThat(testInPatient.getDischargeDate()).isEqualTo(UPDATED_DISCHARGE_DATE);

        // Validate the InPatient in Elasticsearch
        verify(mockInPatientSearchRepository, times(1)).save(testInPatient);
    }

    @Test
    @Transactional
    public void updateNonExistingInPatient() throws Exception {
        int databaseSizeBeforeUpdate = inPatientRepository.findAll().size();

        // Create the InPatient
        InPatientDTO inPatientDTO = inPatientMapper.toDto(inPatient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInPatientMockMvc.perform(put("/api/in-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inPatientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InPatient in the database
        List<InPatient> inPatientList = inPatientRepository.findAll();
        assertThat(inPatientList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InPatient in Elasticsearch
        verify(mockInPatientSearchRepository, times(0)).save(inPatient);
    }

    @Test
    @Transactional
    public void deleteInPatient() throws Exception {
        // Initialize the database
        inPatientRepository.saveAndFlush(inPatient);

        int databaseSizeBeforeDelete = inPatientRepository.findAll().size();

        // Delete the inPatient
        restInPatientMockMvc.perform(delete("/api/in-patients/{id}", inPatient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InPatient> inPatientList = inPatientRepository.findAll();
        assertThat(inPatientList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InPatient in Elasticsearch
        verify(mockInPatientSearchRepository, times(1)).deleteById(inPatient.getId());
    }

    @Test
    @Transactional
    public void searchInPatient() throws Exception {
        // Initialize the database
        inPatientRepository.saveAndFlush(inPatient);
        when(mockInPatientSearchRepository.search(queryStringQuery("id:" + inPatient.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(inPatient), PageRequest.of(0, 1), 1));
        // Search the inPatient
        restInPatientMockMvc.perform(get("/api/_search/in-patients?query=id:" + inPatient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inPatient.getId().intValue())))
            .andExpect(jsonPath("$.[*].admitDate").value(hasItem(DEFAULT_ADMIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].dischargeDate").value(hasItem(DEFAULT_DISCHARGE_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InPatient.class);
        InPatient inPatient1 = new InPatient();
        inPatient1.setId(1L);
        InPatient inPatient2 = new InPatient();
        inPatient2.setId(inPatient1.getId());
        assertThat(inPatient1).isEqualTo(inPatient2);
        inPatient2.setId(2L);
        assertThat(inPatient1).isNotEqualTo(inPatient2);
        inPatient1.setId(null);
        assertThat(inPatient1).isNotEqualTo(inPatient2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InPatientDTO.class);
        InPatientDTO inPatientDTO1 = new InPatientDTO();
        inPatientDTO1.setId(1L);
        InPatientDTO inPatientDTO2 = new InPatientDTO();
        assertThat(inPatientDTO1).isNotEqualTo(inPatientDTO2);
        inPatientDTO2.setId(inPatientDTO1.getId());
        assertThat(inPatientDTO1).isEqualTo(inPatientDTO2);
        inPatientDTO2.setId(2L);
        assertThat(inPatientDTO1).isNotEqualTo(inPatientDTO2);
        inPatientDTO1.setId(null);
        assertThat(inPatientDTO1).isNotEqualTo(inPatientDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inPatientMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(inPatientMapper.fromId(null)).isNull();
    }
}
