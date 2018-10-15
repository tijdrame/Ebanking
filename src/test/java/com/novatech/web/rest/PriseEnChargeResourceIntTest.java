package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.PriseEnCharge;
import com.novatech.repository.PriseEnChargeRepository;
import com.novatech.service.PriseEnChargeService;
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
 * Test class for the PriseEnChargeResource REST controller.
 *
 * @see PriseEnChargeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class PriseEnChargeResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private PriseEnChargeRepository priseEnChargeRepository;

    @Autowired
    private PriseEnChargeService priseEnChargeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPriseEnChargeMockMvc;

    private PriseEnCharge priseEnCharge;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PriseEnChargeResource priseEnChargeResource = new PriseEnChargeResource(priseEnChargeService);
        this.restPriseEnChargeMockMvc = MockMvcBuilders.standaloneSetup(priseEnChargeResource)
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
    public static PriseEnCharge createEntity(EntityManager em) {
        PriseEnCharge priseEnCharge = new PriseEnCharge()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return priseEnCharge;
    }

    @Before
    public void initTest() {
        priseEnCharge = createEntity(em);
    }

    @Test
    @Transactional
    public void createPriseEnCharge() throws Exception {
        int databaseSizeBeforeCreate = priseEnChargeRepository.findAll().size();

        // Create the PriseEnCharge
        restPriseEnChargeMockMvc.perform(post("/api/prise-en-charges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priseEnCharge)))
            .andExpect(status().isCreated());

        // Validate the PriseEnCharge in the database
        List<PriseEnCharge> priseEnChargeList = priseEnChargeRepository.findAll();
        assertThat(priseEnChargeList).hasSize(databaseSizeBeforeCreate + 1);
        PriseEnCharge testPriseEnCharge = priseEnChargeList.get(priseEnChargeList.size() - 1);
        assertThat(testPriseEnCharge.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testPriseEnCharge.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPriseEnCharge.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createPriseEnChargeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priseEnChargeRepository.findAll().size();

        // Create the PriseEnCharge with an existing ID
        priseEnCharge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriseEnChargeMockMvc.perform(post("/api/prise-en-charges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priseEnCharge)))
            .andExpect(status().isBadRequest());

        // Validate the PriseEnCharge in the database
        List<PriseEnCharge> priseEnChargeList = priseEnChargeRepository.findAll();
        assertThat(priseEnChargeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = priseEnChargeRepository.findAll().size();
        // set the field null
        priseEnCharge.setLibelle(null);

        // Create the PriseEnCharge, which fails.

        restPriseEnChargeMockMvc.perform(post("/api/prise-en-charges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priseEnCharge)))
            .andExpect(status().isBadRequest());

        List<PriseEnCharge> priseEnChargeList = priseEnChargeRepository.findAll();
        assertThat(priseEnChargeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = priseEnChargeRepository.findAll().size();
        // set the field null
        priseEnCharge.setCode(null);

        // Create the PriseEnCharge, which fails.

        restPriseEnChargeMockMvc.perform(post("/api/prise-en-charges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priseEnCharge)))
            .andExpect(status().isBadRequest());

        List<PriseEnCharge> priseEnChargeList = priseEnChargeRepository.findAll();
        assertThat(priseEnChargeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPriseEnCharges() throws Exception {
        // Initialize the database
        priseEnChargeRepository.saveAndFlush(priseEnCharge);

        // Get all the priseEnChargeList
        restPriseEnChargeMockMvc.perform(get("/api/prise-en-charges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priseEnCharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getPriseEnCharge() throws Exception {
        // Initialize the database
        priseEnChargeRepository.saveAndFlush(priseEnCharge);

        // Get the priseEnCharge
        restPriseEnChargeMockMvc.perform(get("/api/prise-en-charges/{id}", priseEnCharge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(priseEnCharge.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPriseEnCharge() throws Exception {
        // Get the priseEnCharge
        restPriseEnChargeMockMvc.perform(get("/api/prise-en-charges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriseEnCharge() throws Exception {
        // Initialize the database
        priseEnChargeService.save(priseEnCharge);

        int databaseSizeBeforeUpdate = priseEnChargeRepository.findAll().size();

        // Update the priseEnCharge
        PriseEnCharge updatedPriseEnCharge = priseEnChargeRepository.findOne(priseEnCharge.getId());
        // Disconnect from session so that the updates on updatedPriseEnCharge are not directly saved in db
        em.detach(updatedPriseEnCharge);
        updatedPriseEnCharge
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restPriseEnChargeMockMvc.perform(put("/api/prise-en-charges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPriseEnCharge)))
            .andExpect(status().isOk());

        // Validate the PriseEnCharge in the database
        List<PriseEnCharge> priseEnChargeList = priseEnChargeRepository.findAll();
        assertThat(priseEnChargeList).hasSize(databaseSizeBeforeUpdate);
        PriseEnCharge testPriseEnCharge = priseEnChargeList.get(priseEnChargeList.size() - 1);
        assertThat(testPriseEnCharge.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testPriseEnCharge.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPriseEnCharge.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingPriseEnCharge() throws Exception {
        int databaseSizeBeforeUpdate = priseEnChargeRepository.findAll().size();

        // Create the PriseEnCharge

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPriseEnChargeMockMvc.perform(put("/api/prise-en-charges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priseEnCharge)))
            .andExpect(status().isCreated());

        // Validate the PriseEnCharge in the database
        List<PriseEnCharge> priseEnChargeList = priseEnChargeRepository.findAll();
        assertThat(priseEnChargeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePriseEnCharge() throws Exception {
        // Initialize the database
        priseEnChargeService.save(priseEnCharge);

        int databaseSizeBeforeDelete = priseEnChargeRepository.findAll().size();

        // Get the priseEnCharge
        restPriseEnChargeMockMvc.perform(delete("/api/prise-en-charges/{id}", priseEnCharge.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PriseEnCharge> priseEnChargeList = priseEnChargeRepository.findAll();
        assertThat(priseEnChargeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriseEnCharge.class);
        PriseEnCharge priseEnCharge1 = new PriseEnCharge();
        priseEnCharge1.setId(1L);
        PriseEnCharge priseEnCharge2 = new PriseEnCharge();
        priseEnCharge2.setId(priseEnCharge1.getId());
        assertThat(priseEnCharge1).isEqualTo(priseEnCharge2);
        priseEnCharge2.setId(2L);
        assertThat(priseEnCharge1).isNotEqualTo(priseEnCharge2);
        priseEnCharge1.setId(null);
        assertThat(priseEnCharge1).isNotEqualTo(priseEnCharge2);
    }
}
