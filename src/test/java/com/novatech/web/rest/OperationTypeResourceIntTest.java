package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.OperationType;
import com.novatech.repository.OperationTypeRepository;
import com.novatech.service.OperationTypeService;
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
 * Test class for the OperationTypeResource REST controller.
 *
 * @see OperationTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class OperationTypeResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private OperationTypeService operationTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOperationTypeMockMvc;

    private OperationType operationType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperationTypeResource operationTypeResource = new OperationTypeResource(operationTypeService);
        this.restOperationTypeMockMvc = MockMvcBuilders.standaloneSetup(operationTypeResource)
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
    public static OperationType createEntity(EntityManager em) {
        OperationType operationType = new OperationType()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return operationType;
    }

    @Before
    public void initTest() {
        operationType = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperationType() throws Exception {
        int databaseSizeBeforeCreate = operationTypeRepository.findAll().size();

        // Create the OperationType
        restOperationTypeMockMvc.perform(post("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationType)))
            .andExpect(status().isCreated());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OperationType testOperationType = operationTypeList.get(operationTypeList.size() - 1);
        assertThat(testOperationType.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testOperationType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOperationType.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createOperationTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operationTypeRepository.findAll().size();

        // Create the OperationType with an existing ID
        operationType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationTypeMockMvc.perform(post("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationType)))
            .andExpect(status().isBadRequest());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationTypeRepository.findAll().size();
        // set the field null
        operationType.setLibelle(null);

        // Create the OperationType, which fails.

        restOperationTypeMockMvc.perform(post("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationType)))
            .andExpect(status().isBadRequest());

        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationTypeRepository.findAll().size();
        // set the field null
        operationType.setCode(null);

        // Create the OperationType, which fails.

        restOperationTypeMockMvc.perform(post("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationType)))
            .andExpect(status().isBadRequest());

        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperationTypes() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList
        restOperationTypeMockMvc.perform(get("/api/operation-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getOperationType() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get the operationType
        restOperationTypeMockMvc.perform(get("/api/operation-types/{id}", operationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operationType.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOperationType() throws Exception {
        // Get the operationType
        restOperationTypeMockMvc.perform(get("/api/operation-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperationType() throws Exception {
        // Initialize the database
        operationTypeService.save(operationType);

        int databaseSizeBeforeUpdate = operationTypeRepository.findAll().size();

        // Update the operationType
        OperationType updatedOperationType = operationTypeRepository.findOne(operationType.getId());
        // Disconnect from session so that the updates on updatedOperationType are not directly saved in db
        em.detach(updatedOperationType);
        updatedOperationType
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restOperationTypeMockMvc.perform(put("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOperationType)))
            .andExpect(status().isOk());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeUpdate);
        OperationType testOperationType = operationTypeList.get(operationTypeList.size() - 1);
        assertThat(testOperationType.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testOperationType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOperationType.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingOperationType() throws Exception {
        int databaseSizeBeforeUpdate = operationTypeRepository.findAll().size();

        // Create the OperationType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOperationTypeMockMvc.perform(put("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationType)))
            .andExpect(status().isCreated());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOperationType() throws Exception {
        // Initialize the database
        operationTypeService.save(operationType);

        int databaseSizeBeforeDelete = operationTypeRepository.findAll().size();

        // Get the operationType
        restOperationTypeMockMvc.perform(delete("/api/operation-types/{id}", operationType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationType.class);
        OperationType operationType1 = new OperationType();
        operationType1.setId(1L);
        OperationType operationType2 = new OperationType();
        operationType2.setId(operationType1.getId());
        assertThat(operationType1).isEqualTo(operationType2);
        operationType2.setId(2L);
        assertThat(operationType1).isNotEqualTo(operationType2);
        operationType1.setId(null);
        assertThat(operationType1).isNotEqualTo(operationType2);
    }
}
