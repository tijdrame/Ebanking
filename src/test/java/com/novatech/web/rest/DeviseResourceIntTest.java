package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.Devise;
import com.novatech.repository.DeviseRepository;
import com.novatech.service.DeviseService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.novatech.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DeviseResource REST controller.
 *
 * @see DeviseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class DeviseResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_ACHAT_BB = 1D;
    private static final Double UPDATED_ACHAT_BB = 2D;

    private static final Double DEFAULT_ACHAT_TC = 1D;
    private static final Double UPDATED_ACHAT_TC = 2D;

    private static final Double DEFAULT_ACHAT_TR = 1D;
    private static final Double UPDATED_ACHAT_TR = 2D;

    private static final Double DEFAULT_VENTE_BB = 1D;
    private static final Double UPDATED_VENTE_BB = 2D;

    private static final Double DEFAULT_VENTE_TC = 1D;
    private static final Double UPDATED_VENTE_TC = 2D;

    private static final Double DEFAULT_VENTE_TR = 1D;
    private static final Double UPDATED_VENTE_TR = 2D;

    private static final LocalDate DEFAULT_DATE_MAJ = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MAJ = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private DeviseRepository deviseRepository;

    @Autowired
    private DeviseService deviseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeviseMockMvc;

    private Devise devise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeviseResource deviseResource = new DeviseResource(deviseService);
        this.restDeviseMockMvc = MockMvcBuilders.standaloneSetup(deviseResource)
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
    public static Devise createEntity(EntityManager em) {
        Devise devise = new Devise()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .achatBb(DEFAULT_ACHAT_BB)
            .achatTc(DEFAULT_ACHAT_TC)
            .achatTr(DEFAULT_ACHAT_TR)
            .venteBb(DEFAULT_VENTE_BB)
            .venteTc(DEFAULT_VENTE_TC)
            .venteTr(DEFAULT_VENTE_TR)
            .dateMaj(DEFAULT_DATE_MAJ)
            .deleted(DEFAULT_DELETED);
        return devise;
    }

    @Before
    public void initTest() {
        devise = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevise() throws Exception {
        int databaseSizeBeforeCreate = deviseRepository.findAll().size();

        // Create the Devise
        restDeviseMockMvc.perform(post("/api/devises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devise)))
            .andExpect(status().isCreated());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeCreate + 1);
        Devise testDevise = deviseList.get(deviseList.size() - 1);
        assertThat(testDevise.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testDevise.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDevise.getAchatBb()).isEqualTo(DEFAULT_ACHAT_BB);
        assertThat(testDevise.getAchatTc()).isEqualTo(DEFAULT_ACHAT_TC);
        assertThat(testDevise.getAchatTr()).isEqualTo(DEFAULT_ACHAT_TR);
        assertThat(testDevise.getVenteBb()).isEqualTo(DEFAULT_VENTE_BB);
        assertThat(testDevise.getVenteTc()).isEqualTo(DEFAULT_VENTE_TC);
        assertThat(testDevise.getVenteTr()).isEqualTo(DEFAULT_VENTE_TR);
        assertThat(testDevise.getDateMaj()).isEqualTo(DEFAULT_DATE_MAJ);
        assertThat(testDevise.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createDeviseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviseRepository.findAll().size();

        // Create the Devise with an existing ID
        devise.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviseMockMvc.perform(post("/api/devises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devise)))
            .andExpect(status().isBadRequest());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviseRepository.findAll().size();
        // set the field null
        devise.setLibelle(null);

        // Create the Devise, which fails.

        restDeviseMockMvc.perform(post("/api/devises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devise)))
            .andExpect(status().isBadRequest());

        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDevises() throws Exception {
        // Initialize the database
        deviseRepository.saveAndFlush(devise);

        // Get all the deviseList
        restDeviseMockMvc.perform(get("/api/devises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devise.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].achatBb").value(hasItem(DEFAULT_ACHAT_BB.doubleValue())))
            .andExpect(jsonPath("$.[*].achatTc").value(hasItem(DEFAULT_ACHAT_TC.doubleValue())))
            .andExpect(jsonPath("$.[*].achatTr").value(hasItem(DEFAULT_ACHAT_TR.doubleValue())))
            .andExpect(jsonPath("$.[*].venteBb").value(hasItem(DEFAULT_VENTE_BB.doubleValue())))
            .andExpect(jsonPath("$.[*].venteTc").value(hasItem(DEFAULT_VENTE_TC.doubleValue())))
            .andExpect(jsonPath("$.[*].venteTr").value(hasItem(DEFAULT_VENTE_TR.doubleValue())))
            .andExpect(jsonPath("$.[*].dateMaj").value(hasItem(DEFAULT_DATE_MAJ.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getDevise() throws Exception {
        // Initialize the database
        deviseRepository.saveAndFlush(devise);

        // Get the devise
        restDeviseMockMvc.perform(get("/api/devises/{id}", devise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(devise.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.achatBb").value(DEFAULT_ACHAT_BB.doubleValue()))
            .andExpect(jsonPath("$.achatTc").value(DEFAULT_ACHAT_TC.doubleValue()))
            .andExpect(jsonPath("$.achatTr").value(DEFAULT_ACHAT_TR.doubleValue()))
            .andExpect(jsonPath("$.venteBb").value(DEFAULT_VENTE_BB.doubleValue()))
            .andExpect(jsonPath("$.venteTc").value(DEFAULT_VENTE_TC.doubleValue()))
            .andExpect(jsonPath("$.venteTr").value(DEFAULT_VENTE_TR.doubleValue()))
            .andExpect(jsonPath("$.dateMaj").value(DEFAULT_DATE_MAJ.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDevise() throws Exception {
        // Get the devise
        restDeviseMockMvc.perform(get("/api/devises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevise() throws Exception {
        // Initialize the database
        deviseService.save(devise);

        int databaseSizeBeforeUpdate = deviseRepository.findAll().size();

        // Update the devise
        Devise updatedDevise = deviseRepository.findOne(devise.getId());
        // Disconnect from session so that the updates on updatedDevise are not directly saved in db
        em.detach(updatedDevise);
        updatedDevise
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .achatBb(UPDATED_ACHAT_BB)
            .achatTc(UPDATED_ACHAT_TC)
            .achatTr(UPDATED_ACHAT_TR)
            .venteBb(UPDATED_VENTE_BB)
            .venteTc(UPDATED_VENTE_TC)
            .venteTr(UPDATED_VENTE_TR)
            .dateMaj(UPDATED_DATE_MAJ)
            .deleted(UPDATED_DELETED);

        restDeviseMockMvc.perform(put("/api/devises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDevise)))
            .andExpect(status().isOk());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeUpdate);
        Devise testDevise = deviseList.get(deviseList.size() - 1);
        assertThat(testDevise.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testDevise.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDevise.getAchatBb()).isEqualTo(UPDATED_ACHAT_BB);
        assertThat(testDevise.getAchatTc()).isEqualTo(UPDATED_ACHAT_TC);
        assertThat(testDevise.getAchatTr()).isEqualTo(UPDATED_ACHAT_TR);
        assertThat(testDevise.getVenteBb()).isEqualTo(UPDATED_VENTE_BB);
        assertThat(testDevise.getVenteTc()).isEqualTo(UPDATED_VENTE_TC);
        assertThat(testDevise.getVenteTr()).isEqualTo(UPDATED_VENTE_TR);
        assertThat(testDevise.getDateMaj()).isEqualTo(UPDATED_DATE_MAJ);
        assertThat(testDevise.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingDevise() throws Exception {
        int databaseSizeBeforeUpdate = deviseRepository.findAll().size();

        // Create the Devise

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeviseMockMvc.perform(put("/api/devises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(devise)))
            .andExpect(status().isCreated());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDevise() throws Exception {
        // Initialize the database
        deviseService.save(devise);

        int databaseSizeBeforeDelete = deviseRepository.findAll().size();

        // Get the devise
        restDeviseMockMvc.perform(delete("/api/devises/{id}", devise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Devise.class);
        Devise devise1 = new Devise();
        devise1.setId(1L);
        Devise devise2 = new Devise();
        devise2.setId(devise1.getId());
        assertThat(devise1).isEqualTo(devise2);
        devise2.setId(2L);
        assertThat(devise1).isNotEqualTo(devise2);
        devise1.setId(null);
        assertThat(devise1).isNotEqualTo(devise2);
    }
}
