package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.Beneficiaire;
import com.novatech.repository.BeneficiaireRepository;
import com.novatech.service.BeneficiaireService;
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
 * Test class for the BeneficiaireResource REST controller.
 *
 * @see BeneficiaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class BeneficiaireResourceIntTest {

    private static final String DEFAULT_TITULAIRE = "AAAAAAAAAA";
    private static final String UPDATED_TITULAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_COMPTE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_COMPTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEMANDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEMANDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_ACCEPTATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ACCEPTATION = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private BeneficiaireRepository beneficiaireRepository;

    @Autowired
    private BeneficiaireService beneficiaireService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBeneficiaireMockMvc;

    private Beneficiaire beneficiaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BeneficiaireResource beneficiaireResource = new BeneficiaireResource(beneficiaireService);
        this.restBeneficiaireMockMvc = MockMvcBuilders.standaloneSetup(beneficiaireResource)
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
    public static Beneficiaire createEntity(EntityManager em) {
        Beneficiaire beneficiaire = new Beneficiaire()
            .titulaire(DEFAULT_TITULAIRE)
            .numeroCompte(DEFAULT_NUMERO_COMPTE)
            .dateDemande(DEFAULT_DATE_DEMANDE)
            .dateAcceptation(DEFAULT_DATE_ACCEPTATION)
            .deleted(DEFAULT_DELETED);
        return beneficiaire;
    }

    @Before
    public void initTest() {
        beneficiaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeneficiaire() throws Exception {
        int databaseSizeBeforeCreate = beneficiaireRepository.findAll().size();

        // Create the Beneficiaire
        restBeneficiaireMockMvc.perform(post("/api/beneficiaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaire)))
            .andExpect(status().isCreated());

        // Validate the Beneficiaire in the database
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeCreate + 1);
        Beneficiaire testBeneficiaire = beneficiaireList.get(beneficiaireList.size() - 1);
        assertThat(testBeneficiaire.getTitulaire()).isEqualTo(DEFAULT_TITULAIRE);
        assertThat(testBeneficiaire.getNumeroCompte()).isEqualTo(DEFAULT_NUMERO_COMPTE);
        assertThat(testBeneficiaire.getDateDemande()).isEqualTo(DEFAULT_DATE_DEMANDE);
        assertThat(testBeneficiaire.getDateAcceptation()).isEqualTo(DEFAULT_DATE_ACCEPTATION);
        assertThat(testBeneficiaire.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createBeneficiaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beneficiaireRepository.findAll().size();

        // Create the Beneficiaire with an existing ID
        beneficiaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeneficiaireMockMvc.perform(post("/api/beneficiaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaire)))
            .andExpect(status().isBadRequest());

        // Validate the Beneficiaire in the database
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitulaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaireRepository.findAll().size();
        // set the field null
        beneficiaire.setTitulaire(null);

        // Create the Beneficiaire, which fails.

        restBeneficiaireMockMvc.perform(post("/api/beneficiaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaire)))
            .andExpect(status().isBadRequest());

        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroCompteIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaireRepository.findAll().size();
        // set the field null
        beneficiaire.setNumeroCompte(null);

        // Create the Beneficiaire, which fails.

        restBeneficiaireMockMvc.perform(post("/api/beneficiaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaire)))
            .andExpect(status().isBadRequest());

        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBeneficiaires() throws Exception {
        // Initialize the database
        beneficiaireRepository.saveAndFlush(beneficiaire);

        // Get all the beneficiaireList
        restBeneficiaireMockMvc.perform(get("/api/beneficiaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficiaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulaire").value(hasItem(DEFAULT_TITULAIRE.toString())))
            .andExpect(jsonPath("$.[*].numeroCompte").value(hasItem(DEFAULT_NUMERO_COMPTE.toString())))
            .andExpect(jsonPath("$.[*].dateDemande").value(hasItem(DEFAULT_DATE_DEMANDE.toString())))
            .andExpect(jsonPath("$.[*].dateAcceptation").value(hasItem(DEFAULT_DATE_ACCEPTATION.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getBeneficiaire() throws Exception {
        // Initialize the database
        beneficiaireRepository.saveAndFlush(beneficiaire);

        // Get the beneficiaire
        restBeneficiaireMockMvc.perform(get("/api/beneficiaires/{id}", beneficiaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(beneficiaire.getId().intValue()))
            .andExpect(jsonPath("$.titulaire").value(DEFAULT_TITULAIRE.toString()))
            .andExpect(jsonPath("$.numeroCompte").value(DEFAULT_NUMERO_COMPTE.toString()))
            .andExpect(jsonPath("$.dateDemande").value(DEFAULT_DATE_DEMANDE.toString()))
            .andExpect(jsonPath("$.dateAcceptation").value(DEFAULT_DATE_ACCEPTATION.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBeneficiaire() throws Exception {
        // Get the beneficiaire
        restBeneficiaireMockMvc.perform(get("/api/beneficiaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeneficiaire() throws Exception {
        // Initialize the database
        beneficiaireService.save(beneficiaire);

        int databaseSizeBeforeUpdate = beneficiaireRepository.findAll().size();

        // Update the beneficiaire
        Beneficiaire updatedBeneficiaire = beneficiaireRepository.findOne(beneficiaire.getId());
        // Disconnect from session so that the updates on updatedBeneficiaire are not directly saved in db
        em.detach(updatedBeneficiaire);
        updatedBeneficiaire
            .titulaire(UPDATED_TITULAIRE)
            .numeroCompte(UPDATED_NUMERO_COMPTE)
            .dateDemande(UPDATED_DATE_DEMANDE)
            .dateAcceptation(UPDATED_DATE_ACCEPTATION)
            .deleted(UPDATED_DELETED);

        restBeneficiaireMockMvc.perform(put("/api/beneficiaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBeneficiaire)))
            .andExpect(status().isOk());

        // Validate the Beneficiaire in the database
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeUpdate);
        Beneficiaire testBeneficiaire = beneficiaireList.get(beneficiaireList.size() - 1);
        assertThat(testBeneficiaire.getTitulaire()).isEqualTo(UPDATED_TITULAIRE);
        assertThat(testBeneficiaire.getNumeroCompte()).isEqualTo(UPDATED_NUMERO_COMPTE);
        assertThat(testBeneficiaire.getDateDemande()).isEqualTo(UPDATED_DATE_DEMANDE);
        assertThat(testBeneficiaire.getDateAcceptation()).isEqualTo(UPDATED_DATE_ACCEPTATION);
        assertThat(testBeneficiaire.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingBeneficiaire() throws Exception {
        int databaseSizeBeforeUpdate = beneficiaireRepository.findAll().size();

        // Create the Beneficiaire

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBeneficiaireMockMvc.perform(put("/api/beneficiaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaire)))
            .andExpect(status().isCreated());

        // Validate the Beneficiaire in the database
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBeneficiaire() throws Exception {
        // Initialize the database
        beneficiaireService.save(beneficiaire);

        int databaseSizeBeforeDelete = beneficiaireRepository.findAll().size();

        // Get the beneficiaire
        restBeneficiaireMockMvc.perform(delete("/api/beneficiaires/{id}", beneficiaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Beneficiaire> beneficiaireList = beneficiaireRepository.findAll();
        assertThat(beneficiaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beneficiaire.class);
        Beneficiaire beneficiaire1 = new Beneficiaire();
        beneficiaire1.setId(1L);
        Beneficiaire beneficiaire2 = new Beneficiaire();
        beneficiaire2.setId(beneficiaire1.getId());
        assertThat(beneficiaire1).isEqualTo(beneficiaire2);
        beneficiaire2.setId(2L);
        assertThat(beneficiaire1).isNotEqualTo(beneficiaire2);
        beneficiaire1.setId(null);
        assertThat(beneficiaire1).isNotEqualTo(beneficiaire2);
    }
}
