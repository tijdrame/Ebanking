package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.NbreFeuillesChequier;
import com.novatech.repository.NbreFeuillesChequierRepository;
import com.novatech.service.NbreFeuillesChequierService;
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
 * Test class for the NbreFeuillesChequierResource REST controller.
 *
 * @see NbreFeuillesChequierResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class NbreFeuillesChequierResourceIntTest {

    private static final Integer DEFAULT_NB_FEUILLES = 1;
    private static final Integer UPDATED_NB_FEUILLES = 2;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private NbreFeuillesChequierRepository nbreFeuillesChequierRepository;

    @Autowired
    private NbreFeuillesChequierService nbreFeuillesChequierService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNbreFeuillesChequierMockMvc;

    private NbreFeuillesChequier nbreFeuillesChequier;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NbreFeuillesChequierResource nbreFeuillesChequierResource = new NbreFeuillesChequierResource(nbreFeuillesChequierService);
        this.restNbreFeuillesChequierMockMvc = MockMvcBuilders.standaloneSetup(nbreFeuillesChequierResource)
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
    public static NbreFeuillesChequier createEntity(EntityManager em) {
        NbreFeuillesChequier nbreFeuillesChequier = new NbreFeuillesChequier()
            .nbFeuilles(DEFAULT_NB_FEUILLES)
            .libelle(DEFAULT_LIBELLE)
            .deleted(DEFAULT_DELETED);
        return nbreFeuillesChequier;
    }

    @Before
    public void initTest() {
        nbreFeuillesChequier = createEntity(em);
    }

    @Test
    @Transactional
    public void createNbreFeuillesChequier() throws Exception {
        int databaseSizeBeforeCreate = nbreFeuillesChequierRepository.findAll().size();

        // Create the NbreFeuillesChequier
        restNbreFeuillesChequierMockMvc.perform(post("/api/nbre-feuilles-chequiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nbreFeuillesChequier)))
            .andExpect(status().isCreated());

        // Validate the NbreFeuillesChequier in the database
        List<NbreFeuillesChequier> nbreFeuillesChequierList = nbreFeuillesChequierRepository.findAll();
        assertThat(nbreFeuillesChequierList).hasSize(databaseSizeBeforeCreate + 1);
        NbreFeuillesChequier testNbreFeuillesChequier = nbreFeuillesChequierList.get(nbreFeuillesChequierList.size() - 1);
        assertThat(testNbreFeuillesChequier.getNbFeuilles()).isEqualTo(DEFAULT_NB_FEUILLES);
        assertThat(testNbreFeuillesChequier.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testNbreFeuillesChequier.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createNbreFeuillesChequierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nbreFeuillesChequierRepository.findAll().size();

        // Create the NbreFeuillesChequier with an existing ID
        nbreFeuillesChequier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNbreFeuillesChequierMockMvc.perform(post("/api/nbre-feuilles-chequiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nbreFeuillesChequier)))
            .andExpect(status().isBadRequest());

        // Validate the NbreFeuillesChequier in the database
        List<NbreFeuillesChequier> nbreFeuillesChequierList = nbreFeuillesChequierRepository.findAll();
        assertThat(nbreFeuillesChequierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNbFeuillesIsRequired() throws Exception {
        int databaseSizeBeforeTest = nbreFeuillesChequierRepository.findAll().size();
        // set the field null
        nbreFeuillesChequier.setNbFeuilles(null);

        // Create the NbreFeuillesChequier, which fails.

        restNbreFeuillesChequierMockMvc.perform(post("/api/nbre-feuilles-chequiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nbreFeuillesChequier)))
            .andExpect(status().isBadRequest());

        List<NbreFeuillesChequier> nbreFeuillesChequierList = nbreFeuillesChequierRepository.findAll();
        assertThat(nbreFeuillesChequierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNbreFeuillesChequiers() throws Exception {
        // Initialize the database
        nbreFeuillesChequierRepository.saveAndFlush(nbreFeuillesChequier);

        // Get all the nbreFeuillesChequierList
        restNbreFeuillesChequierMockMvc.perform(get("/api/nbre-feuilles-chequiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nbreFeuillesChequier.getId().intValue())))
            .andExpect(jsonPath("$.[*].nbFeuilles").value(hasItem(DEFAULT_NB_FEUILLES)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getNbreFeuillesChequier() throws Exception {
        // Initialize the database
        nbreFeuillesChequierRepository.saveAndFlush(nbreFeuillesChequier);

        // Get the nbreFeuillesChequier
        restNbreFeuillesChequierMockMvc.perform(get("/api/nbre-feuilles-chequiers/{id}", nbreFeuillesChequier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nbreFeuillesChequier.getId().intValue()))
            .andExpect(jsonPath("$.nbFeuilles").value(DEFAULT_NB_FEUILLES))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNbreFeuillesChequier() throws Exception {
        // Get the nbreFeuillesChequier
        restNbreFeuillesChequierMockMvc.perform(get("/api/nbre-feuilles-chequiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNbreFeuillesChequier() throws Exception {
        // Initialize the database
        nbreFeuillesChequierService.save(nbreFeuillesChequier);

        int databaseSizeBeforeUpdate = nbreFeuillesChequierRepository.findAll().size();

        // Update the nbreFeuillesChequier
        NbreFeuillesChequier updatedNbreFeuillesChequier = nbreFeuillesChequierRepository.findOne(nbreFeuillesChequier.getId());
        // Disconnect from session so that the updates on updatedNbreFeuillesChequier are not directly saved in db
        em.detach(updatedNbreFeuillesChequier);
        updatedNbreFeuillesChequier
            .nbFeuilles(UPDATED_NB_FEUILLES)
            .libelle(UPDATED_LIBELLE)
            .deleted(UPDATED_DELETED);

        restNbreFeuillesChequierMockMvc.perform(put("/api/nbre-feuilles-chequiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNbreFeuillesChequier)))
            .andExpect(status().isOk());

        // Validate the NbreFeuillesChequier in the database
        List<NbreFeuillesChequier> nbreFeuillesChequierList = nbreFeuillesChequierRepository.findAll();
        assertThat(nbreFeuillesChequierList).hasSize(databaseSizeBeforeUpdate);
        NbreFeuillesChequier testNbreFeuillesChequier = nbreFeuillesChequierList.get(nbreFeuillesChequierList.size() - 1);
        assertThat(testNbreFeuillesChequier.getNbFeuilles()).isEqualTo(UPDATED_NB_FEUILLES);
        assertThat(testNbreFeuillesChequier.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testNbreFeuillesChequier.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingNbreFeuillesChequier() throws Exception {
        int databaseSizeBeforeUpdate = nbreFeuillesChequierRepository.findAll().size();

        // Create the NbreFeuillesChequier

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNbreFeuillesChequierMockMvc.perform(put("/api/nbre-feuilles-chequiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nbreFeuillesChequier)))
            .andExpect(status().isCreated());

        // Validate the NbreFeuillesChequier in the database
        List<NbreFeuillesChequier> nbreFeuillesChequierList = nbreFeuillesChequierRepository.findAll();
        assertThat(nbreFeuillesChequierList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNbreFeuillesChequier() throws Exception {
        // Initialize the database
        nbreFeuillesChequierService.save(nbreFeuillesChequier);

        int databaseSizeBeforeDelete = nbreFeuillesChequierRepository.findAll().size();

        // Get the nbreFeuillesChequier
        restNbreFeuillesChequierMockMvc.perform(delete("/api/nbre-feuilles-chequiers/{id}", nbreFeuillesChequier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NbreFeuillesChequier> nbreFeuillesChequierList = nbreFeuillesChequierRepository.findAll();
        assertThat(nbreFeuillesChequierList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NbreFeuillesChequier.class);
        NbreFeuillesChequier nbreFeuillesChequier1 = new NbreFeuillesChequier();
        nbreFeuillesChequier1.setId(1L);
        NbreFeuillesChequier nbreFeuillesChequier2 = new NbreFeuillesChequier();
        nbreFeuillesChequier2.setId(nbreFeuillesChequier1.getId());
        assertThat(nbreFeuillesChequier1).isEqualTo(nbreFeuillesChequier2);
        nbreFeuillesChequier2.setId(2L);
        assertThat(nbreFeuillesChequier1).isNotEqualTo(nbreFeuillesChequier2);
        nbreFeuillesChequier1.setId(null);
        assertThat(nbreFeuillesChequier1).isNotEqualTo(nbreFeuillesChequier2);
    }
}
