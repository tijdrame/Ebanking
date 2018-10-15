package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.Abonne;
import com.novatech.repository.AbonneRepository;
import com.novatech.service.AbonneService;
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
 * Test class for the AbonneResource REST controller.
 *
 * @see AbonneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class AbonneResourceIntTest {

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DELETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DELETED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AbonneRepository abonneRepository;

    @Autowired
    private AbonneService abonneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAbonneMockMvc;

    private Abonne abonne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AbonneResource abonneResource = new AbonneResource(abonneService);
        this.restAbonneMockMvc = MockMvcBuilders.standaloneSetup(abonneResource)
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
    public static Abonne createEntity(EntityManager em) {
        Abonne abonne = new Abonne()
            .telephone(DEFAULT_TELEPHONE)
            .deleted(DEFAULT_DELETED)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .dateUpdated(DEFAULT_DATE_UPDATED);
        return abonne;
    }

    @Before
    public void initTest() {
        abonne = createEntity(em);
    }

    @Test
    @Transactional
    public void createAbonne() throws Exception {
        int databaseSizeBeforeCreate = abonneRepository.findAll().size();

        // Create the Abonne
        restAbonneMockMvc.perform(post("/api/abonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abonne)))
            .andExpect(status().isCreated());

        // Validate the Abonne in the database
        List<Abonne> abonneList = abonneRepository.findAll();
        assertThat(abonneList).hasSize(databaseSizeBeforeCreate + 1);
        Abonne testAbonne = abonneList.get(abonneList.size() - 1);
        assertThat(testAbonne.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testAbonne.isDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testAbonne.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testAbonne.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testAbonne.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void createAbonneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = abonneRepository.findAll().size();

        // Create the Abonne with an existing ID
        abonne.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbonneMockMvc.perform(post("/api/abonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abonne)))
            .andExpect(status().isBadRequest());

        // Validate the Abonne in the database
        List<Abonne> abonneList = abonneRepository.findAll();
        assertThat(abonneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAbonnes() throws Exception {
        // Initialize the database
        abonneRepository.saveAndFlush(abonne);

        // Get all the abonneList
        restAbonneMockMvc.perform(get("/api/abonnes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(abonne.getId().intValue())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())));
    }

    @Test
    @Transactional
    public void getAbonne() throws Exception {
        // Initialize the database
        abonneRepository.saveAndFlush(abonne);

        // Get the abonne
        restAbonneMockMvc.perform(get("/api/abonnes/{id}", abonne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(abonne.getId().intValue()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAbonne() throws Exception {
        // Get the abonne
        restAbonneMockMvc.perform(get("/api/abonnes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbonne() throws Exception {
        // Initialize the database
        abonneService.save(abonne);

        int databaseSizeBeforeUpdate = abonneRepository.findAll().size();

        // Update the abonne
        Abonne updatedAbonne = abonneRepository.findOne(abonne.getId());
        // Disconnect from session so that the updates on updatedAbonne are not directly saved in db
        em.detach(updatedAbonne);
        updatedAbonne
            .telephone(UPDATED_TELEPHONE)
            .deleted(UPDATED_DELETED)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .dateUpdated(UPDATED_DATE_UPDATED);

        restAbonneMockMvc.perform(put("/api/abonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAbonne)))
            .andExpect(status().isOk());

        // Validate the Abonne in the database
        List<Abonne> abonneList = abonneRepository.findAll();
        assertThat(abonneList).hasSize(databaseSizeBeforeUpdate);
        Abonne testAbonne = abonneList.get(abonneList.size() - 1);
        assertThat(testAbonne.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testAbonne.isDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testAbonne.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testAbonne.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testAbonne.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingAbonne() throws Exception {
        int databaseSizeBeforeUpdate = abonneRepository.findAll().size();

        // Create the Abonne

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAbonneMockMvc.perform(put("/api/abonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abonne)))
            .andExpect(status().isCreated());

        // Validate the Abonne in the database
        List<Abonne> abonneList = abonneRepository.findAll();
        assertThat(abonneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAbonne() throws Exception {
        // Initialize the database
        abonneService.save(abonne);

        int databaseSizeBeforeDelete = abonneRepository.findAll().size();

        // Get the abonne
        restAbonneMockMvc.perform(delete("/api/abonnes/{id}", abonne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Abonne> abonneList = abonneRepository.findAll();
        assertThat(abonneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Abonne.class);
        Abonne abonne1 = new Abonne();
        abonne1.setId(1L);
        Abonne abonne2 = new Abonne();
        abonne2.setId(abonne1.getId());
        assertThat(abonne1).isEqualTo(abonne2);
        abonne2.setId(2L);
        assertThat(abonne1).isNotEqualTo(abonne2);
        abonne1.setId(null);
        assertThat(abonne1).isNotEqualTo(abonne2);
    }
}
