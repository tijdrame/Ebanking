package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.BanquesPartenaires;
import com.novatech.repository.BanquesPartenairesRepository;
import com.novatech.service.BanquesPartenairesService;
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
 * Test class for the BanquesPartenairesResource REST controller.
 *
 * @see BanquesPartenairesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class BanquesPartenairesResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private BanquesPartenairesRepository banquesPartenairesRepository;

    @Autowired
    private BanquesPartenairesService banquesPartenairesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBanquesPartenairesMockMvc;

    private BanquesPartenaires banquesPartenaires;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BanquesPartenairesResource banquesPartenairesResource = new BanquesPartenairesResource(banquesPartenairesService);
        this.restBanquesPartenairesMockMvc = MockMvcBuilders.standaloneSetup(banquesPartenairesResource)
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
    public static BanquesPartenaires createEntity(EntityManager em) {
        BanquesPartenaires banquesPartenaires = new BanquesPartenaires()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return banquesPartenaires;
    }

    @Before
    public void initTest() {
        banquesPartenaires = createEntity(em);
    }

    @Test
    @Transactional
    public void createBanquesPartenaires() throws Exception {
        int databaseSizeBeforeCreate = banquesPartenairesRepository.findAll().size();

        // Create the BanquesPartenaires
        restBanquesPartenairesMockMvc.perform(post("/api/banques-partenaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banquesPartenaires)))
            .andExpect(status().isCreated());

        // Validate the BanquesPartenaires in the database
        List<BanquesPartenaires> banquesPartenairesList = banquesPartenairesRepository.findAll();
        assertThat(banquesPartenairesList).hasSize(databaseSizeBeforeCreate + 1);
        BanquesPartenaires testBanquesPartenaires = banquesPartenairesList.get(banquesPartenairesList.size() - 1);
        assertThat(testBanquesPartenaires.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testBanquesPartenaires.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBanquesPartenaires.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createBanquesPartenairesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = banquesPartenairesRepository.findAll().size();

        // Create the BanquesPartenaires with an existing ID
        banquesPartenaires.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBanquesPartenairesMockMvc.perform(post("/api/banques-partenaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banquesPartenaires)))
            .andExpect(status().isBadRequest());

        // Validate the BanquesPartenaires in the database
        List<BanquesPartenaires> banquesPartenairesList = banquesPartenairesRepository.findAll();
        assertThat(banquesPartenairesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = banquesPartenairesRepository.findAll().size();
        // set the field null
        banquesPartenaires.setLibelle(null);

        // Create the BanquesPartenaires, which fails.

        restBanquesPartenairesMockMvc.perform(post("/api/banques-partenaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banquesPartenaires)))
            .andExpect(status().isBadRequest());

        List<BanquesPartenaires> banquesPartenairesList = banquesPartenairesRepository.findAll();
        assertThat(banquesPartenairesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = banquesPartenairesRepository.findAll().size();
        // set the field null
        banquesPartenaires.setCode(null);

        // Create the BanquesPartenaires, which fails.

        restBanquesPartenairesMockMvc.perform(post("/api/banques-partenaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banquesPartenaires)))
            .andExpect(status().isBadRequest());

        List<BanquesPartenaires> banquesPartenairesList = banquesPartenairesRepository.findAll();
        assertThat(banquesPartenairesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBanquesPartenaires() throws Exception {
        // Initialize the database
        banquesPartenairesRepository.saveAndFlush(banquesPartenaires);

        // Get all the banquesPartenairesList
        restBanquesPartenairesMockMvc.perform(get("/api/banques-partenaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banquesPartenaires.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getBanquesPartenaires() throws Exception {
        // Initialize the database
        banquesPartenairesRepository.saveAndFlush(banquesPartenaires);

        // Get the banquesPartenaires
        restBanquesPartenairesMockMvc.perform(get("/api/banques-partenaires/{id}", banquesPartenaires.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(banquesPartenaires.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBanquesPartenaires() throws Exception {
        // Get the banquesPartenaires
        restBanquesPartenairesMockMvc.perform(get("/api/banques-partenaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBanquesPartenaires() throws Exception {
        // Initialize the database
        banquesPartenairesService.save(banquesPartenaires);

        int databaseSizeBeforeUpdate = banquesPartenairesRepository.findAll().size();

        // Update the banquesPartenaires
        BanquesPartenaires updatedBanquesPartenaires = banquesPartenairesRepository.findOne(banquesPartenaires.getId());
        // Disconnect from session so that the updates on updatedBanquesPartenaires are not directly saved in db
        em.detach(updatedBanquesPartenaires);
        updatedBanquesPartenaires
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restBanquesPartenairesMockMvc.perform(put("/api/banques-partenaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBanquesPartenaires)))
            .andExpect(status().isOk());

        // Validate the BanquesPartenaires in the database
        List<BanquesPartenaires> banquesPartenairesList = banquesPartenairesRepository.findAll();
        assertThat(banquesPartenairesList).hasSize(databaseSizeBeforeUpdate);
        BanquesPartenaires testBanquesPartenaires = banquesPartenairesList.get(banquesPartenairesList.size() - 1);
        assertThat(testBanquesPartenaires.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testBanquesPartenaires.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBanquesPartenaires.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingBanquesPartenaires() throws Exception {
        int databaseSizeBeforeUpdate = banquesPartenairesRepository.findAll().size();

        // Create the BanquesPartenaires

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBanquesPartenairesMockMvc.perform(put("/api/banques-partenaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banquesPartenaires)))
            .andExpect(status().isCreated());

        // Validate the BanquesPartenaires in the database
        List<BanquesPartenaires> banquesPartenairesList = banquesPartenairesRepository.findAll();
        assertThat(banquesPartenairesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBanquesPartenaires() throws Exception {
        // Initialize the database
        banquesPartenairesService.save(banquesPartenaires);

        int databaseSizeBeforeDelete = banquesPartenairesRepository.findAll().size();

        // Get the banquesPartenaires
        restBanquesPartenairesMockMvc.perform(delete("/api/banques-partenaires/{id}", banquesPartenaires.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BanquesPartenaires> banquesPartenairesList = banquesPartenairesRepository.findAll();
        assertThat(banquesPartenairesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BanquesPartenaires.class);
        BanquesPartenaires banquesPartenaires1 = new BanquesPartenaires();
        banquesPartenaires1.setId(1L);
        BanquesPartenaires banquesPartenaires2 = new BanquesPartenaires();
        banquesPartenaires2.setId(banquesPartenaires1.getId());
        assertThat(banquesPartenaires1).isEqualTo(banquesPartenaires2);
        banquesPartenaires2.setId(2L);
        assertThat(banquesPartenaires1).isNotEqualTo(banquesPartenaires2);
        banquesPartenaires1.setId(null);
        assertThat(banquesPartenaires1).isNotEqualTo(banquesPartenaires2);
    }
}
