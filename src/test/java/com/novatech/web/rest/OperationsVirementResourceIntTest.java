package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.OperationsVirement;
import com.novatech.repository.OperationsVirementRepository;
import com.novatech.service.OperationsVirementService;
import com.novatech.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.novatech.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OperationsVirementResource REST controller.
 *
 * @see OperationsVirementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class OperationsVirementResourceIntTest {

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private OperationsVirementRepository operationsVirementRepository;

    @Autowired
    private OperationsVirementService operationsVirementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOperationsVirementMockMvc;

    private OperationsVirement operationsVirement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperationsVirementResource operationsVirementResource = new OperationsVirementResource(operationsVirementService);
        this.restOperationsVirementMockMvc = MockMvcBuilders.standaloneSetup(operationsVirementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationsVirement createEntity(EntityManager em) {
        OperationsVirement operationsVirement = new OperationsVirement()
            .montant(DEFAULT_MONTANT)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return operationsVirement;
    }

    @Before
    public void initTest() {
        operationsVirement = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperationsVirement() throws Exception {
        int databaseSizeBeforeCreate = operationsVirementRepository.findAll().size();

        // Create the OperationsVirement
        restOperationsVirementMockMvc.perform(post("/api/operations-virements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationsVirement)))
            .andExpect(status().isCreated());

        // Validate the OperationsVirement in the database
        List<OperationsVirement> operationsVirementList = operationsVirementRepository.findAll();
        assertThat(operationsVirementList).hasSize(databaseSizeBeforeCreate + 1);
        OperationsVirement testOperationsVirement = operationsVirementList.get(operationsVirementList.size() - 1);
        assertThat(testOperationsVirement.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testOperationsVirement.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOperationsVirement.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createOperationsVirementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operationsVirementRepository.findAll().size();

        // Create the OperationsVirement with an existing ID
        operationsVirement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationsVirementMockMvc.perform(post("/api/operations-virements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationsVirement)))
            .andExpect(status().isBadRequest());

        // Validate the OperationsVirement in the database
        List<OperationsVirement> operationsVirementList = operationsVirementRepository.findAll();
        assertThat(operationsVirementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationsVirementRepository.findAll().size();
        // set the field null
        operationsVirement.setMontant(null);

        // Create the OperationsVirement, which fails.

        restOperationsVirementMockMvc.perform(post("/api/operations-virements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationsVirement)))
            .andExpect(status().isBadRequest());

        List<OperationsVirement> operationsVirementList = operationsVirementRepository.findAll();
        assertThat(operationsVirementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationsVirementRepository.findAll().size();
        // set the field null
        operationsVirement.setCode(null);

        // Create the OperationsVirement, which fails.

        restOperationsVirementMockMvc.perform(post("/api/operations-virements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationsVirement)))
            .andExpect(status().isBadRequest());

        List<OperationsVirement> operationsVirementList = operationsVirementRepository.findAll();
        assertThat(operationsVirementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperationsVirements() throws Exception {
        // Initialize the database
        operationsVirementRepository.saveAndFlush(operationsVirement);

        // Get all the operationsVirementList
        restOperationsVirementMockMvc.perform(get("/api/operations-virements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operationsVirement.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getOperationsVirement() throws Exception {
        // Initialize the database
        operationsVirementRepository.saveAndFlush(operationsVirement);

        // Get the operationsVirement
        restOperationsVirementMockMvc.perform(get("/api/operations-virements/{id}", operationsVirement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operationsVirement.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOperationsVirement() throws Exception {
        // Get the operationsVirement
        restOperationsVirementMockMvc.perform(get("/api/operations-virements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperationsVirement() throws Exception {
        // Initialize the database
        operationsVirementService.save(operationsVirement);

        int databaseSizeBeforeUpdate = operationsVirementRepository.findAll().size();

        // Update the operationsVirement
        OperationsVirement updatedOperationsVirement = operationsVirementRepository.findOne(operationsVirement.getId());
        // Disconnect from session so that the updates on updatedOperationsVirement are not directly saved in db
        em.detach(updatedOperationsVirement);
        updatedOperationsVirement
            .montant(UPDATED_MONTANT)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restOperationsVirementMockMvc.perform(put("/api/operations-virements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOperationsVirement)))
            .andExpect(status().isOk());

        // Validate the OperationsVirement in the database
        List<OperationsVirement> operationsVirementList = operationsVirementRepository.findAll();
        assertThat(operationsVirementList).hasSize(databaseSizeBeforeUpdate);
        OperationsVirement testOperationsVirement = operationsVirementList.get(operationsVirementList.size() - 1);
        assertThat(testOperationsVirement.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testOperationsVirement.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOperationsVirement.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingOperationsVirement() throws Exception {
        int databaseSizeBeforeUpdate = operationsVirementRepository.findAll().size();

        // Create the OperationsVirement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOperationsVirementMockMvc.perform(put("/api/operations-virements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationsVirement)))
            .andExpect(status().isCreated());

        // Validate the OperationsVirement in the database
        List<OperationsVirement> operationsVirementList = operationsVirementRepository.findAll();
        assertThat(operationsVirementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOperationsVirement() throws Exception {
        // Initialize the database
        operationsVirementService.save(operationsVirement);

        int databaseSizeBeforeDelete = operationsVirementRepository.findAll().size();

        // Get the operationsVirement
        restOperationsVirementMockMvc.perform(delete("/api/operations-virements/{id}", operationsVirement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OperationsVirement> operationsVirementList = operationsVirementRepository.findAll();
        assertThat(operationsVirementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationsVirement.class);
        OperationsVirement operationsVirement1 = new OperationsVirement();
        operationsVirement1.setId(1L);
        OperationsVirement operationsVirement2 = new OperationsVirement();
        operationsVirement2.setId(operationsVirement1.getId());
        assertThat(operationsVirement1).isEqualTo(operationsVirement2);
        operationsVirement2.setId(2L);
        assertThat(operationsVirement1).isNotEqualTo(operationsVirement2);
        operationsVirement1.setId(null);
        assertThat(operationsVirement1).isNotEqualTo(operationsVirement2);
    }
}
