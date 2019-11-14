package com.omar.hmis.web.rest;

import com.omar.hmis.PatientServiceApp;
import com.omar.hmis.config.TestSecurityConfiguration;
import com.omar.hmis.domain.Bill;
import com.omar.hmis.domain.Patient;
import com.omar.hmis.repository.BillRepository;
import com.omar.hmis.repository.search.BillSearchRepository;
import com.omar.hmis.service.BillService;
import com.omar.hmis.service.dto.BillDTO;
import com.omar.hmis.service.mapper.BillMapper;
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

import com.omar.hmis.domain.enumeration.BillPaidStatus;
/**
 * Integration tests for the {@link BillResource} REST controller.
 */
@SpringBootTest(classes = {PatientServiceApp.class, TestSecurityConfiguration.class})
public class BillResourceIT {

    private static final Double DEFAULT_DOCTOR_FEE = 0D;
    private static final Double UPDATED_DOCTOR_FEE = 1D;

    private static final Double DEFAULT_MEDICINE_CHARGES = 0D;
    private static final Double UPDATED_MEDICINE_CHARGES = 1D;

    private static final Double DEFAULT_TESTS_FEE = 0D;
    private static final Double UPDATED_TESTS_FEE = 1D;

    private static final Double DEFAULT_ROOM_CHARGES = 0D;
    private static final Double UPDATED_ROOM_CHARGES = 1D;

    private static final Double DEFAULT_OTHER_CHARGES = 0D;
    private static final Double UPDATED_OTHER_CHARGES = 1D;

    private static final Double DEFAULT_TOTAL_BILL = 1D;
    private static final Double UPDATED_TOTAL_BILL = 2D;

    private static final BillPaidStatus DEFAULT_PAID_STATUS = BillPaidStatus.PAID;
    private static final BillPaidStatus UPDATED_PAID_STATUS = BillPaidStatus.PENDING;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private BillService billService;

    /**
     * This repository is mocked in the com.omar.hmis.repository.search test package.
     *
     * @see com.omar.hmis.repository.search.BillSearchRepositoryMockConfiguration
     */
    @Autowired
    private BillSearchRepository mockBillSearchRepository;

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

    private MockMvc restBillMockMvc;

    private Bill bill;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillResource billResource = new BillResource(billService);
        this.restBillMockMvc = MockMvcBuilders.standaloneSetup(billResource)
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
    public static Bill createEntity(EntityManager em) {
        Bill bill = new Bill()
            .doctorFee(DEFAULT_DOCTOR_FEE)
            .medicineCharges(DEFAULT_MEDICINE_CHARGES)
            .testsFee(DEFAULT_TESTS_FEE)
            .roomCharges(DEFAULT_ROOM_CHARGES)
            .otherCharges(DEFAULT_OTHER_CHARGES)
            .totalBill(DEFAULT_TOTAL_BILL)
            .paidStatus(DEFAULT_PAID_STATUS);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        bill.setPatient(patient);
        return bill;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bill createUpdatedEntity(EntityManager em) {
        Bill bill = new Bill()
            .doctorFee(UPDATED_DOCTOR_FEE)
            .medicineCharges(UPDATED_MEDICINE_CHARGES)
            .testsFee(UPDATED_TESTS_FEE)
            .roomCharges(UPDATED_ROOM_CHARGES)
            .otherCharges(UPDATED_OTHER_CHARGES)
            .totalBill(UPDATED_TOTAL_BILL)
            .paidStatus(UPDATED_PAID_STATUS);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createUpdatedEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        bill.setPatient(patient);
        return bill;
    }

    @BeforeEach
    public void initTest() {
        bill = createEntity(em);
    }

    @Test
    @Transactional
    public void createBill() throws Exception {
        int databaseSizeBeforeCreate = billRepository.findAll().size();

        // Create the Bill
        BillDTO billDTO = billMapper.toDto(bill);
        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isCreated());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeCreate + 1);
        Bill testBill = billList.get(billList.size() - 1);
        assertThat(testBill.getDoctorFee()).isEqualTo(DEFAULT_DOCTOR_FEE);
        assertThat(testBill.getMedicineCharges()).isEqualTo(DEFAULT_MEDICINE_CHARGES);
        assertThat(testBill.getTestsFee()).isEqualTo(DEFAULT_TESTS_FEE);
        assertThat(testBill.getRoomCharges()).isEqualTo(DEFAULT_ROOM_CHARGES);
        assertThat(testBill.getOtherCharges()).isEqualTo(DEFAULT_OTHER_CHARGES);
        assertThat(testBill.getTotalBill()).isEqualTo(DEFAULT_TOTAL_BILL);
        assertThat(testBill.getPaidStatus()).isEqualTo(DEFAULT_PAID_STATUS);

        // Validate the Bill in Elasticsearch
        verify(mockBillSearchRepository, times(1)).save(testBill);
    }

    @Test
    @Transactional
    public void createBillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billRepository.findAll().size();

        // Create the Bill with an existing ID
        bill.setId(1L);
        BillDTO billDTO = billMapper.toDto(bill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeCreate);

        // Validate the Bill in Elasticsearch
        verify(mockBillSearchRepository, times(0)).save(bill);
    }


    @Test
    @Transactional
    public void checkDoctorFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setDoctorFee(null);

        // Create the Bill, which fails.
        BillDTO billDTO = billMapper.toDto(bill);

        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMedicineChargesIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setMedicineCharges(null);

        // Create the Bill, which fails.
        BillDTO billDTO = billMapper.toDto(bill);

        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTestsFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setTestsFee(null);

        // Create the Bill, which fails.
        BillDTO billDTO = billMapper.toDto(bill);

        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRoomChargesIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setRoomCharges(null);

        // Create the Bill, which fails.
        BillDTO billDTO = billMapper.toDto(bill);

        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOtherChargesIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setOtherCharges(null);

        // Create the Bill, which fails.
        BillDTO billDTO = billMapper.toDto(bill);

        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalBillIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setTotalBill(null);

        // Create the Bill, which fails.
        BillDTO billDTO = billMapper.toDto(bill);

        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaidStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setPaidStatus(null);

        // Create the Bill, which fails.
        BillDTO billDTO = billMapper.toDto(bill);

        restBillMockMvc.perform(post("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBills() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the billList
        restBillMockMvc.perform(get("/api/bills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bill.getId().intValue())))
            .andExpect(jsonPath("$.[*].doctorFee").value(hasItem(DEFAULT_DOCTOR_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].medicineCharges").value(hasItem(DEFAULT_MEDICINE_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].testsFee").value(hasItem(DEFAULT_TESTS_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].roomCharges").value(hasItem(DEFAULT_ROOM_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].otherCharges").value(hasItem(DEFAULT_OTHER_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].totalBill").value(hasItem(DEFAULT_TOTAL_BILL.doubleValue())))
            .andExpect(jsonPath("$.[*].paidStatus").value(hasItem(DEFAULT_PAID_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", bill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bill.getId().intValue()))
            .andExpect(jsonPath("$.doctorFee").value(DEFAULT_DOCTOR_FEE.doubleValue()))
            .andExpect(jsonPath("$.medicineCharges").value(DEFAULT_MEDICINE_CHARGES.doubleValue()))
            .andExpect(jsonPath("$.testsFee").value(DEFAULT_TESTS_FEE.doubleValue()))
            .andExpect(jsonPath("$.roomCharges").value(DEFAULT_ROOM_CHARGES.doubleValue()))
            .andExpect(jsonPath("$.otherCharges").value(DEFAULT_OTHER_CHARGES.doubleValue()))
            .andExpect(jsonPath("$.totalBill").value(DEFAULT_TOTAL_BILL.doubleValue()))
            .andExpect(jsonPath("$.paidStatus").value(DEFAULT_PAID_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBill() throws Exception {
        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Update the bill
        Bill updatedBill = billRepository.findById(bill.getId()).get();
        // Disconnect from session so that the updates on updatedBill are not directly saved in db
        em.detach(updatedBill);
        updatedBill
            .doctorFee(UPDATED_DOCTOR_FEE)
            .medicineCharges(UPDATED_MEDICINE_CHARGES)
            .testsFee(UPDATED_TESTS_FEE)
            .roomCharges(UPDATED_ROOM_CHARGES)
            .otherCharges(UPDATED_OTHER_CHARGES)
            .totalBill(UPDATED_TOTAL_BILL)
            .paidStatus(UPDATED_PAID_STATUS);
        BillDTO billDTO = billMapper.toDto(updatedBill);

        restBillMockMvc.perform(put("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isOk());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);
        Bill testBill = billList.get(billList.size() - 1);
        assertThat(testBill.getDoctorFee()).isEqualTo(UPDATED_DOCTOR_FEE);
        assertThat(testBill.getMedicineCharges()).isEqualTo(UPDATED_MEDICINE_CHARGES);
        assertThat(testBill.getTestsFee()).isEqualTo(UPDATED_TESTS_FEE);
        assertThat(testBill.getRoomCharges()).isEqualTo(UPDATED_ROOM_CHARGES);
        assertThat(testBill.getOtherCharges()).isEqualTo(UPDATED_OTHER_CHARGES);
        assertThat(testBill.getTotalBill()).isEqualTo(UPDATED_TOTAL_BILL);
        assertThat(testBill.getPaidStatus()).isEqualTo(UPDATED_PAID_STATUS);

        // Validate the Bill in Elasticsearch
        verify(mockBillSearchRepository, times(1)).save(testBill);
    }

    @Test
    @Transactional
    public void updateNonExistingBill() throws Exception {
        int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Create the Bill
        BillDTO billDTO = billMapper.toDto(bill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillMockMvc.perform(put("/api/bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bill in the database
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Bill in Elasticsearch
        verify(mockBillSearchRepository, times(0)).save(bill);
    }

    @Test
    @Transactional
    public void deleteBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        int databaseSizeBeforeDelete = billRepository.findAll().size();

        // Delete the bill
        restBillMockMvc.perform(delete("/api/bills/{id}", bill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bill> billList = billRepository.findAll();
        assertThat(billList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Bill in Elasticsearch
        verify(mockBillSearchRepository, times(1)).deleteById(bill.getId());
    }

    @Test
    @Transactional
    public void searchBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);
        when(mockBillSearchRepository.search(queryStringQuery("id:" + bill.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(bill), PageRequest.of(0, 1), 1));
        // Search the bill
        restBillMockMvc.perform(get("/api/_search/bills?query=id:" + bill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bill.getId().intValue())))
            .andExpect(jsonPath("$.[*].doctorFee").value(hasItem(DEFAULT_DOCTOR_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].medicineCharges").value(hasItem(DEFAULT_MEDICINE_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].testsFee").value(hasItem(DEFAULT_TESTS_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].roomCharges").value(hasItem(DEFAULT_ROOM_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].otherCharges").value(hasItem(DEFAULT_OTHER_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].totalBill").value(hasItem(DEFAULT_TOTAL_BILL.doubleValue())))
            .andExpect(jsonPath("$.[*].paidStatus").value(hasItem(DEFAULT_PAID_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bill.class);
        Bill bill1 = new Bill();
        bill1.setId(1L);
        Bill bill2 = new Bill();
        bill2.setId(bill1.getId());
        assertThat(bill1).isEqualTo(bill2);
        bill2.setId(2L);
        assertThat(bill1).isNotEqualTo(bill2);
        bill1.setId(null);
        assertThat(bill1).isNotEqualTo(bill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillDTO.class);
        BillDTO billDTO1 = new BillDTO();
        billDTO1.setId(1L);
        BillDTO billDTO2 = new BillDTO();
        assertThat(billDTO1).isNotEqualTo(billDTO2);
        billDTO2.setId(billDTO1.getId());
        assertThat(billDTO1).isEqualTo(billDTO2);
        billDTO2.setId(2L);
        assertThat(billDTO1).isNotEqualTo(billDTO2);
        billDTO1.setId(null);
        assertThat(billDTO1).isNotEqualTo(billDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(billMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(billMapper.fromId(null)).isNull();
    }
}
