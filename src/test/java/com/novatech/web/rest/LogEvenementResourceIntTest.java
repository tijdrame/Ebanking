package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.LogEvenement;
import com.novatech.repository.LogEvenementRepository;
import com.novatech.service.LogEvenementService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.novatech.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LogEvenementResource REST controller.
 *
 * @see LogEvenementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class LogEvenementResourceIntTest {

    private static final Long DEFAULT_CODE_OBJET = 1L;
    private static final Long UPDATED_CODE_OBJET = 2L;

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_MAC = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_MAC = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_IP = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_IP = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private LogEvenementRepository logEvenementRepository;

    @Autowired
    private LogEvenementService logEvenementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLogEvenementMockMvc;

    private LogEvenement logEvenement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LogEvenementResource logEvenementResource = new LogEvenementResource(logEvenementService);
        this.restLogEvenementMockMvc = MockMvcBuilders.standaloneSetup(logEvenementResource)
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
    public static LogEvenement createEntity(EntityManager em) {
        LogEvenement logEvenement = new LogEvenement()
            .codeObjet(DEFAULT_CODE_OBJET)
            .entityName(DEFAULT_ENTITY_NAME)
            .eventName(DEFAULT_EVENT_NAME)
            .adresseMac(DEFAULT_ADRESSE_MAC)
            .adresseIP(DEFAULT_ADRESSE_IP)
            .dateCreated(DEFAULT_DATE_CREATED);
        return logEvenement;
    }

    @Before
    public void initTest() {
        logEvenement = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogEvenement() throws Exception {
        int databaseSizeBeforeCreate = logEvenementRepository.findAll().size();

        // Create the LogEvenement
        restLogEvenementMockMvc.perform(post("/api/log-evenements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logEvenement)))
            .andExpect(status().isCreated());

        // Validate the LogEvenement in the database
        List<LogEvenement> logEvenementList = logEvenementRepository.findAll();
        assertThat(logEvenementList).hasSize(databaseSizeBeforeCreate + 1);
        LogEvenement testLogEvenement = logEvenementList.get(logEvenementList.size() - 1);
        assertThat(testLogEvenement.getCodeObjet()).isEqualTo(DEFAULT_CODE_OBJET);
        assertThat(testLogEvenement.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testLogEvenement.getEventName()).isEqualTo(DEFAULT_EVENT_NAME);
        assertThat(testLogEvenement.getAdresseMac()).isEqualTo(DEFAULT_ADRESSE_MAC);
        assertThat(testLogEvenement.getAdresseIP()).isEqualTo(DEFAULT_ADRESSE_IP);
        assertThat(testLogEvenement.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void createLogEvenementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logEvenementRepository.findAll().size();

        // Create the LogEvenement with an existing ID
        logEvenement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogEvenementMockMvc.perform(post("/api/log-evenements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logEvenement)))
            .andExpect(status().isBadRequest());

        // Validate the LogEvenement in the database
        List<LogEvenement> logEvenementList = logEvenementRepository.findAll();
        assertThat(logEvenementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLogEvenements() throws Exception {
        // Initialize the database
        logEvenementRepository.saveAndFlush(logEvenement);

        // Get all the logEvenementList
        restLogEvenementMockMvc.perform(get("/api/log-evenements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logEvenement.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeObjet").value(hasItem(DEFAULT_CODE_OBJET.intValue())))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME.toString())))
            .andExpect(jsonPath("$.[*].eventName").value(hasItem(DEFAULT_EVENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].adresseMac").value(hasItem(DEFAULT_ADRESSE_MAC.toString())))
            .andExpect(jsonPath("$.[*].adresseIP").value(hasItem(DEFAULT_ADRESSE_IP.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())));
    }

    @Test
    @Transactional
    public void getLogEvenement() throws Exception {
        // Initialize the database
        logEvenementRepository.saveAndFlush(logEvenement);

        // Get the logEvenement
        restLogEvenementMockMvc.perform(get("/api/log-evenements/{id}", logEvenement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(logEvenement.getId().intValue()))
            .andExpect(jsonPath("$.codeObjet").value(DEFAULT_CODE_OBJET.intValue()))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME.toString()))
            .andExpect(jsonPath("$.eventName").value(DEFAULT_EVENT_NAME.toString()))
            .andExpect(jsonPath("$.adresseMac").value(DEFAULT_ADRESSE_MAC.toString()))
            .andExpect(jsonPath("$.adresseIP").value(DEFAULT_ADRESSE_IP.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLogEvenement() throws Exception {
        // Get the logEvenement
        restLogEvenementMockMvc.perform(get("/api/log-evenements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogEvenement() throws Exception {
        // Initialize the database
        logEvenementService.save(logEvenement);

        int databaseSizeBeforeUpdate = logEvenementRepository.findAll().size();

        // Update the logEvenement
        LogEvenement updatedLogEvenement = logEvenementRepository.findOne(logEvenement.getId());
        // Disconnect from session so that the updates on updatedLogEvenement are not directly saved in db
        em.detach(updatedLogEvenement);
        updatedLogEvenement
            .codeObjet(UPDATED_CODE_OBJET)
            .entityName(UPDATED_ENTITY_NAME)
            .eventName(UPDATED_EVENT_NAME)
            .adresseMac(UPDATED_ADRESSE_MAC)
            .adresseIP(UPDATED_ADRESSE_IP)
            .dateCreated(UPDATED_DATE_CREATED);

        restLogEvenementMockMvc.perform(put("/api/log-evenements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLogEvenement)))
            .andExpect(status().isOk());

        // Validate the LogEvenement in the database
        List<LogEvenement> logEvenementList = logEvenementRepository.findAll();
        assertThat(logEvenementList).hasSize(databaseSizeBeforeUpdate);
        LogEvenement testLogEvenement = logEvenementList.get(logEvenementList.size() - 1);
        assertThat(testLogEvenement.getCodeObjet()).isEqualTo(UPDATED_CODE_OBJET);
        assertThat(testLogEvenement.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testLogEvenement.getEventName()).isEqualTo(UPDATED_EVENT_NAME);
        assertThat(testLogEvenement.getAdresseMac()).isEqualTo(UPDATED_ADRESSE_MAC);
        assertThat(testLogEvenement.getAdresseIP()).isEqualTo(UPDATED_ADRESSE_IP);
        assertThat(testLogEvenement.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingLogEvenement() throws Exception {
        int databaseSizeBeforeUpdate = logEvenementRepository.findAll().size();

        // Create the LogEvenement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLogEvenementMockMvc.perform(put("/api/log-evenements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logEvenement)))
            .andExpect(status().isCreated());

        // Validate the LogEvenement in the database
        List<LogEvenement> logEvenementList = logEvenementRepository.findAll();
        assertThat(logEvenementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLogEvenement() throws Exception {
        // Initialize the database
        logEvenementService.save(logEvenement);

        int databaseSizeBeforeDelete = logEvenementRepository.findAll().size();

        // Get the logEvenement
        restLogEvenementMockMvc.perform(delete("/api/log-evenements/{id}", logEvenement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LogEvenement> logEvenementList = logEvenementRepository.findAll();
        assertThat(logEvenementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogEvenement.class);
        LogEvenement logEvenement1 = new LogEvenement();
        logEvenement1.setId(1L);
        LogEvenement logEvenement2 = new LogEvenement();
        logEvenement2.setId(logEvenement1.getId());
        assertThat(logEvenement1).isEqualTo(logEvenement2);
        logEvenement2.setId(2L);
        assertThat(logEvenement1).isNotEqualTo(logEvenement2);
        logEvenement1.setId(null);
        assertThat(logEvenement1).isNotEqualTo(logEvenement2);
    }
}
