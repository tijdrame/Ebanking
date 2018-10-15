package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.PaiementFacture;
import com.novatech.repository.PaiementFactureRepository;
import com.novatech.service.PaiementFactureService;
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
 * Test class for the PaiementFactureResource REST controller.
 *
 * @see PaiementFactureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class PaiementFactureResourceIntTest {

    private static final String DEFAULT_NUMERO_FACTURE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_FACTURE = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_ACCEPTATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ACCEPTATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DEMANDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEMANDE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_EST_TELECHARGE = false;
    private static final Boolean UPDATED_EST_TELECHARGE = true;

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private PaiementFactureRepository paiementFactureRepository;

    @Autowired
    private PaiementFactureService paiementFactureService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaiementFactureMockMvc;

    private PaiementFacture paiementFacture;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaiementFactureResource paiementFactureResource = new PaiementFactureResource(paiementFactureService);
        this.restPaiementFactureMockMvc = MockMvcBuilders.standaloneSetup(paiementFactureResource)
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
    public static PaiementFacture createEntity(EntityManager em) {
        PaiementFacture paiementFacture = new PaiementFacture()
            .numeroFacture(DEFAULT_NUMERO_FACTURE)
            .montant(DEFAULT_MONTANT)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .dateAcceptation(DEFAULT_DATE_ACCEPTATION)
            .dateDemande(DEFAULT_DATE_DEMANDE)
            .estTelecharge(DEFAULT_EST_TELECHARGE)
            .deleted(DEFAULT_DELETED);
        return paiementFacture;
    }

    @Before
    public void initTest() {
        paiementFacture = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaiementFacture() throws Exception {
        int databaseSizeBeforeCreate = paiementFactureRepository.findAll().size();

        // Create the PaiementFacture
        restPaiementFactureMockMvc.perform(post("/api/paiement-factures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementFacture)))
            .andExpect(status().isCreated());

        // Validate the PaiementFacture in the database
        List<PaiementFacture> paiementFactureList = paiementFactureRepository.findAll();
        assertThat(paiementFactureList).hasSize(databaseSizeBeforeCreate + 1);
        PaiementFacture testPaiementFacture = paiementFactureList.get(paiementFactureList.size() - 1);
        assertThat(testPaiementFacture.getNumeroFacture()).isEqualTo(DEFAULT_NUMERO_FACTURE);
        assertThat(testPaiementFacture.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testPaiementFacture.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testPaiementFacture.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testPaiementFacture.getDateAcceptation()).isEqualTo(DEFAULT_DATE_ACCEPTATION);
        assertThat(testPaiementFacture.getDateDemande()).isEqualTo(DEFAULT_DATE_DEMANDE);
        assertThat(testPaiementFacture.isEstTelecharge()).isEqualTo(DEFAULT_EST_TELECHARGE);
        assertThat(testPaiementFacture.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createPaiementFactureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paiementFactureRepository.findAll().size();

        // Create the PaiementFacture with an existing ID
        paiementFacture.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaiementFactureMockMvc.perform(post("/api/paiement-factures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementFacture)))
            .andExpect(status().isBadRequest());

        // Validate the PaiementFacture in the database
        List<PaiementFacture> paiementFactureList = paiementFactureRepository.findAll();
        assertThat(paiementFactureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroFactureIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementFactureRepository.findAll().size();
        // set the field null
        paiementFacture.setNumeroFacture(null);

        // Create the PaiementFacture, which fails.

        restPaiementFactureMockMvc.perform(post("/api/paiement-factures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementFacture)))
            .andExpect(status().isBadRequest());

        List<PaiementFacture> paiementFactureList = paiementFactureRepository.findAll();
        assertThat(paiementFactureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementFactureRepository.findAll().size();
        // set the field null
        paiementFacture.setMontant(null);

        // Create the PaiementFacture, which fails.

        restPaiementFactureMockMvc.perform(post("/api/paiement-factures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementFacture)))
            .andExpect(status().isBadRequest());

        List<PaiementFacture> paiementFactureList = paiementFactureRepository.findAll();
        assertThat(paiementFactureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementFactureRepository.findAll().size();
        // set the field null
        paiementFacture.setDateDebut(null);

        // Create the PaiementFacture, which fails.

        restPaiementFactureMockMvc.perform(post("/api/paiement-factures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementFacture)))
            .andExpect(status().isBadRequest());

        List<PaiementFacture> paiementFactureList = paiementFactureRepository.findAll();
        assertThat(paiementFactureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementFactureRepository.findAll().size();
        // set the field null
        paiementFacture.setDateFin(null);

        // Create the PaiementFacture, which fails.

        restPaiementFactureMockMvc.perform(post("/api/paiement-factures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementFacture)))
            .andExpect(status().isBadRequest());

        List<PaiementFacture> paiementFactureList = paiementFactureRepository.findAll();
        assertThat(paiementFactureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaiementFactures() throws Exception {
        // Initialize the database
        paiementFactureRepository.saveAndFlush(paiementFacture);

        // Get all the paiementFactureList
        restPaiementFactureMockMvc.perform(get("/api/paiement-factures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paiementFacture.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroFacture").value(hasItem(DEFAULT_NUMERO_FACTURE.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].dateAcceptation").value(hasItem(DEFAULT_DATE_ACCEPTATION.toString())))
            .andExpect(jsonPath("$.[*].dateDemande").value(hasItem(DEFAULT_DATE_DEMANDE.toString())))
            .andExpect(jsonPath("$.[*].estTelecharge").value(hasItem(DEFAULT_EST_TELECHARGE.booleanValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getPaiementFacture() throws Exception {
        // Initialize the database
        paiementFactureRepository.saveAndFlush(paiementFacture);

        // Get the paiementFacture
        restPaiementFactureMockMvc.perform(get("/api/paiement-factures/{id}", paiementFacture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paiementFacture.getId().intValue()))
            .andExpect(jsonPath("$.numeroFacture").value(DEFAULT_NUMERO_FACTURE.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.dateAcceptation").value(DEFAULT_DATE_ACCEPTATION.toString()))
            .andExpect(jsonPath("$.dateDemande").value(DEFAULT_DATE_DEMANDE.toString()))
            .andExpect(jsonPath("$.estTelecharge").value(DEFAULT_EST_TELECHARGE.booleanValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPaiementFacture() throws Exception {
        // Get the paiementFacture
        restPaiementFactureMockMvc.perform(get("/api/paiement-factures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaiementFacture() throws Exception {
        // Initialize the database
        paiementFactureService.save(paiementFacture);

        int databaseSizeBeforeUpdate = paiementFactureRepository.findAll().size();

        // Update the paiementFacture
        PaiementFacture updatedPaiementFacture = paiementFactureRepository.findOne(paiementFacture.getId());
        // Disconnect from session so that the updates on updatedPaiementFacture are not directly saved in db
        em.detach(updatedPaiementFacture);
        updatedPaiementFacture
            .numeroFacture(UPDATED_NUMERO_FACTURE)
            .montant(UPDATED_MONTANT)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .dateAcceptation(UPDATED_DATE_ACCEPTATION)
            .dateDemande(UPDATED_DATE_DEMANDE)
            .estTelecharge(UPDATED_EST_TELECHARGE)
            .deleted(UPDATED_DELETED);

        restPaiementFactureMockMvc.perform(put("/api/paiement-factures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaiementFacture)))
            .andExpect(status().isOk());

        // Validate the PaiementFacture in the database
        List<PaiementFacture> paiementFactureList = paiementFactureRepository.findAll();
        assertThat(paiementFactureList).hasSize(databaseSizeBeforeUpdate);
        PaiementFacture testPaiementFacture = paiementFactureList.get(paiementFactureList.size() - 1);
        assertThat(testPaiementFacture.getNumeroFacture()).isEqualTo(UPDATED_NUMERO_FACTURE);
        assertThat(testPaiementFacture.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testPaiementFacture.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testPaiementFacture.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testPaiementFacture.getDateAcceptation()).isEqualTo(UPDATED_DATE_ACCEPTATION);
        assertThat(testPaiementFacture.getDateDemande()).isEqualTo(UPDATED_DATE_DEMANDE);
        assertThat(testPaiementFacture.isEstTelecharge()).isEqualTo(UPDATED_EST_TELECHARGE);
        assertThat(testPaiementFacture.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingPaiementFacture() throws Exception {
        int databaseSizeBeforeUpdate = paiementFactureRepository.findAll().size();

        // Create the PaiementFacture

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaiementFactureMockMvc.perform(put("/api/paiement-factures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiementFacture)))
            .andExpect(status().isCreated());

        // Validate the PaiementFacture in the database
        List<PaiementFacture> paiementFactureList = paiementFactureRepository.findAll();
        assertThat(paiementFactureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaiementFacture() throws Exception {
        // Initialize the database
        paiementFactureService.save(paiementFacture);

        int databaseSizeBeforeDelete = paiementFactureRepository.findAll().size();

        // Get the paiementFacture
        restPaiementFactureMockMvc.perform(delete("/api/paiement-factures/{id}", paiementFacture.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaiementFacture> paiementFactureList = paiementFactureRepository.findAll();
        assertThat(paiementFactureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaiementFacture.class);
        PaiementFacture paiementFacture1 = new PaiementFacture();
        paiementFacture1.setId(1L);
        PaiementFacture paiementFacture2 = new PaiementFacture();
        paiementFacture2.setId(paiementFacture1.getId());
        assertThat(paiementFacture1).isEqualTo(paiementFacture2);
        paiementFacture2.setId(2L);
        assertThat(paiementFacture1).isNotEqualTo(paiementFacture2);
        paiementFacture1.setId(null);
        assertThat(paiementFacture1).isNotEqualTo(paiementFacture2);
    }
}
