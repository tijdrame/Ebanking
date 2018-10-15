package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.Parametres;
import com.novatech.repository.ParametresRepository;
import com.novatech.service.ParametresService;
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
 * Test class for the ParametresResource REST controller.
 *
 * @see ParametresResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class ParametresResourceIntTest {

    private static final String DEFAULT_CLE = "AAAAAAAAAA";
    private static final String UPDATED_CLE = "BBBBBBBBBB";

    private static final String DEFAULT_VALEUR = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private ParametresRepository parametresRepository;

    @Autowired
    private ParametresService parametresService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParametresMockMvc;

    private Parametres parametres;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParametresResource parametresResource = new ParametresResource(parametresService);
        this.restParametresMockMvc = MockMvcBuilders.standaloneSetup(parametresResource)
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
    public static Parametres createEntity(EntityManager em) {
        Parametres parametres = new Parametres()
            .cle(DEFAULT_CLE)
            .valeur(DEFAULT_VALEUR)
            .deleted(DEFAULT_DELETED);
        return parametres;
    }

    @Before
    public void initTest() {
        parametres = createEntity(em);
    }

    @Test
    @Transactional
    public void createParametres() throws Exception {
        int databaseSizeBeforeCreate = parametresRepository.findAll().size();

        // Create the Parametres
        restParametresMockMvc.perform(post("/api/parametres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametres)))
            .andExpect(status().isCreated());

        // Validate the Parametres in the database
        List<Parametres> parametresList = parametresRepository.findAll();
        assertThat(parametresList).hasSize(databaseSizeBeforeCreate + 1);
        Parametres testParametres = parametresList.get(parametresList.size() - 1);
        assertThat(testParametres.getCle()).isEqualTo(DEFAULT_CLE);
        assertThat(testParametres.getValeur()).isEqualTo(DEFAULT_VALEUR);
        assertThat(testParametres.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createParametresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametresRepository.findAll().size();

        // Create the Parametres with an existing ID
        parametres.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametresMockMvc.perform(post("/api/parametres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametres)))
            .andExpect(status().isBadRequest());

        // Validate the Parametres in the database
        List<Parametres> parametresList = parametresRepository.findAll();
        assertThat(parametresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCleIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametresRepository.findAll().size();
        // set the field null
        parametres.setCle(null);

        // Create the Parametres, which fails.

        restParametresMockMvc.perform(post("/api/parametres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametres)))
            .andExpect(status().isBadRequest());

        List<Parametres> parametresList = parametresRepository.findAll();
        assertThat(parametresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValeurIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametresRepository.findAll().size();
        // set the field null
        parametres.setValeur(null);

        // Create the Parametres, which fails.

        restParametresMockMvc.perform(post("/api/parametres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametres)))
            .andExpect(status().isBadRequest());

        List<Parametres> parametresList = parametresRepository.findAll();
        assertThat(parametresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParametres() throws Exception {
        // Initialize the database
        parametresRepository.saveAndFlush(parametres);

        // Get all the parametresList
        restParametresMockMvc.perform(get("/api/parametres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametres.getId().intValue())))
            .andExpect(jsonPath("$.[*].cle").value(hasItem(DEFAULT_CLE.toString())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getParametres() throws Exception {
        // Initialize the database
        parametresRepository.saveAndFlush(parametres);

        // Get the parametres
        restParametresMockMvc.perform(get("/api/parametres/{id}", parametres.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parametres.getId().intValue()))
            .andExpect(jsonPath("$.cle").value(DEFAULT_CLE.toString()))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingParametres() throws Exception {
        // Get the parametres
        restParametresMockMvc.perform(get("/api/parametres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParametres() throws Exception {
        // Initialize the database
        parametresService.save(parametres);

        int databaseSizeBeforeUpdate = parametresRepository.findAll().size();

        // Update the parametres
        Parametres updatedParametres = parametresRepository.findOne(parametres.getId());
        // Disconnect from session so that the updates on updatedParametres are not directly saved in db
        em.detach(updatedParametres);
        updatedParametres
            .cle(UPDATED_CLE)
            .valeur(UPDATED_VALEUR)
            .deleted(UPDATED_DELETED);

        restParametresMockMvc.perform(put("/api/parametres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParametres)))
            .andExpect(status().isOk());

        // Validate the Parametres in the database
        List<Parametres> parametresList = parametresRepository.findAll();
        assertThat(parametresList).hasSize(databaseSizeBeforeUpdate);
        Parametres testParametres = parametresList.get(parametresList.size() - 1);
        assertThat(testParametres.getCle()).isEqualTo(UPDATED_CLE);
        assertThat(testParametres.getValeur()).isEqualTo(UPDATED_VALEUR);
        assertThat(testParametres.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingParametres() throws Exception {
        int databaseSizeBeforeUpdate = parametresRepository.findAll().size();

        // Create the Parametres

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParametresMockMvc.perform(put("/api/parametres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametres)))
            .andExpect(status().isCreated());

        // Validate the Parametres in the database
        List<Parametres> parametresList = parametresRepository.findAll();
        assertThat(parametresList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParametres() throws Exception {
        // Initialize the database
        parametresService.save(parametres);

        int databaseSizeBeforeDelete = parametresRepository.findAll().size();

        // Get the parametres
        restParametresMockMvc.perform(delete("/api/parametres/{id}", parametres.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Parametres> parametresList = parametresRepository.findAll();
        assertThat(parametresList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parametres.class);
        Parametres parametres1 = new Parametres();
        parametres1.setId(1L);
        Parametres parametres2 = new Parametres();
        parametres2.setId(parametres1.getId());
        assertThat(parametres1).isEqualTo(parametres2);
        parametres2.setId(2L);
        assertThat(parametres1).isNotEqualTo(parametres2);
        parametres1.setId(null);
        assertThat(parametres1).isNotEqualTo(parametres2);
    }
}
