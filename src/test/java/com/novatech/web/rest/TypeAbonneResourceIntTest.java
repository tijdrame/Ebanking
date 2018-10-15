package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.TypeAbonne;
import com.novatech.repository.TypeAbonneRepository;
import com.novatech.service.TypeAbonneService;
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
 * Test class for the TypeAbonneResource REST controller.
 *
 * @see TypeAbonneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class TypeAbonneResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private TypeAbonneRepository typeAbonneRepository;

    @Autowired
    private TypeAbonneService typeAbonneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeAbonneMockMvc;

    private TypeAbonne typeAbonne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeAbonneResource typeAbonneResource = new TypeAbonneResource(typeAbonneService);
        this.restTypeAbonneMockMvc = MockMvcBuilders.standaloneSetup(typeAbonneResource)
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
    public static TypeAbonne createEntity(EntityManager em) {
        TypeAbonne typeAbonne = new TypeAbonne()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return typeAbonne;
    }

    @Before
    public void initTest() {
        typeAbonne = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeAbonne() throws Exception {
        int databaseSizeBeforeCreate = typeAbonneRepository.findAll().size();

        // Create the TypeAbonne
        restTypeAbonneMockMvc.perform(post("/api/type-abonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeAbonne)))
            .andExpect(status().isCreated());

        // Validate the TypeAbonne in the database
        List<TypeAbonne> typeAbonneList = typeAbonneRepository.findAll();
        assertThat(typeAbonneList).hasSize(databaseSizeBeforeCreate + 1);
        TypeAbonne testTypeAbonne = typeAbonneList.get(typeAbonneList.size() - 1);
        assertThat(testTypeAbonne.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeAbonne.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeAbonne.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createTypeAbonneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeAbonneRepository.findAll().size();

        // Create the TypeAbonne with an existing ID
        typeAbonne.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeAbonneMockMvc.perform(post("/api/type-abonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeAbonne)))
            .andExpect(status().isBadRequest());

        // Validate the TypeAbonne in the database
        List<TypeAbonne> typeAbonneList = typeAbonneRepository.findAll();
        assertThat(typeAbonneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeAbonnes() throws Exception {
        // Initialize the database
        typeAbonneRepository.saveAndFlush(typeAbonne);

        // Get all the typeAbonneList
        restTypeAbonneMockMvc.perform(get("/api/type-abonnes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeAbonne.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getTypeAbonne() throws Exception {
        // Initialize the database
        typeAbonneRepository.saveAndFlush(typeAbonne);

        // Get the typeAbonne
        restTypeAbonneMockMvc.perform(get("/api/type-abonnes/{id}", typeAbonne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeAbonne.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeAbonne() throws Exception {
        // Get the typeAbonne
        restTypeAbonneMockMvc.perform(get("/api/type-abonnes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeAbonne() throws Exception {
        // Initialize the database
        typeAbonneService.save(typeAbonne);

        int databaseSizeBeforeUpdate = typeAbonneRepository.findAll().size();

        // Update the typeAbonne
        TypeAbonne updatedTypeAbonne = typeAbonneRepository.findOne(typeAbonne.getId());
        // Disconnect from session so that the updates on updatedTypeAbonne are not directly saved in db
        em.detach(updatedTypeAbonne);
        updatedTypeAbonne
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restTypeAbonneMockMvc.perform(put("/api/type-abonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeAbonne)))
            .andExpect(status().isOk());

        // Validate the TypeAbonne in the database
        List<TypeAbonne> typeAbonneList = typeAbonneRepository.findAll();
        assertThat(typeAbonneList).hasSize(databaseSizeBeforeUpdate);
        TypeAbonne testTypeAbonne = typeAbonneList.get(typeAbonneList.size() - 1);
        assertThat(testTypeAbonne.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeAbonne.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeAbonne.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeAbonne() throws Exception {
        int databaseSizeBeforeUpdate = typeAbonneRepository.findAll().size();

        // Create the TypeAbonne

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeAbonneMockMvc.perform(put("/api/type-abonnes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeAbonne)))
            .andExpect(status().isCreated());

        // Validate the TypeAbonne in the database
        List<TypeAbonne> typeAbonneList = typeAbonneRepository.findAll();
        assertThat(typeAbonneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeAbonne() throws Exception {
        // Initialize the database
        typeAbonneService.save(typeAbonne);

        int databaseSizeBeforeDelete = typeAbonneRepository.findAll().size();

        // Get the typeAbonne
        restTypeAbonneMockMvc.perform(delete("/api/type-abonnes/{id}", typeAbonne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeAbonne> typeAbonneList = typeAbonneRepository.findAll();
        assertThat(typeAbonneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeAbonne.class);
        TypeAbonne typeAbonne1 = new TypeAbonne();
        typeAbonne1.setId(1L);
        TypeAbonne typeAbonne2 = new TypeAbonne();
        typeAbonne2.setId(typeAbonne1.getId());
        assertThat(typeAbonne1).isEqualTo(typeAbonne2);
        typeAbonne2.setId(2L);
        assertThat(typeAbonne1).isNotEqualTo(typeAbonne2);
        typeAbonne1.setId(null);
        assertThat(typeAbonne1).isNotEqualTo(typeAbonne2);
    }
}
