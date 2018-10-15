package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.TypeCompte;
import com.novatech.repository.TypeCompteRepository;
import com.novatech.service.TypeCompteService;
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
 * Test class for the TypeCompteResource REST controller.
 *
 * @see TypeCompteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class TypeCompteResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private TypeCompteRepository typeCompteRepository;

    @Autowired
    private TypeCompteService typeCompteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeCompteMockMvc;

    private TypeCompte typeCompte;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeCompteResource typeCompteResource = new TypeCompteResource(typeCompteService);
        this.restTypeCompteMockMvc = MockMvcBuilders.standaloneSetup(typeCompteResource)
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
    public static TypeCompte createEntity(EntityManager em) {
        TypeCompte typeCompte = new TypeCompte()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return typeCompte;
    }

    @Before
    public void initTest() {
        typeCompte = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeCompte() throws Exception {
        int databaseSizeBeforeCreate = typeCompteRepository.findAll().size();

        // Create the TypeCompte
        restTypeCompteMockMvc.perform(post("/api/type-comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCompte)))
            .andExpect(status().isCreated());

        // Validate the TypeCompte in the database
        List<TypeCompte> typeCompteList = typeCompteRepository.findAll();
        assertThat(typeCompteList).hasSize(databaseSizeBeforeCreate + 1);
        TypeCompte testTypeCompte = typeCompteList.get(typeCompteList.size() - 1);
        assertThat(testTypeCompte.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeCompte.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeCompte.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createTypeCompteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeCompteRepository.findAll().size();

        // Create the TypeCompte with an existing ID
        typeCompte.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeCompteMockMvc.perform(post("/api/type-comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCompte)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCompte in the database
        List<TypeCompte> typeCompteList = typeCompteRepository.findAll();
        assertThat(typeCompteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeComptes() throws Exception {
        // Initialize the database
        typeCompteRepository.saveAndFlush(typeCompte);

        // Get all the typeCompteList
        restTypeCompteMockMvc.perform(get("/api/type-comptes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeCompte.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getTypeCompte() throws Exception {
        // Initialize the database
        typeCompteRepository.saveAndFlush(typeCompte);

        // Get the typeCompte
        restTypeCompteMockMvc.perform(get("/api/type-comptes/{id}", typeCompte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeCompte.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeCompte() throws Exception {
        // Get the typeCompte
        restTypeCompteMockMvc.perform(get("/api/type-comptes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeCompte() throws Exception {
        // Initialize the database
        typeCompteService.save(typeCompte);

        int databaseSizeBeforeUpdate = typeCompteRepository.findAll().size();

        // Update the typeCompte
        TypeCompte updatedTypeCompte = typeCompteRepository.findOne(typeCompte.getId());
        // Disconnect from session so that the updates on updatedTypeCompte are not directly saved in db
        em.detach(updatedTypeCompte);
        updatedTypeCompte
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restTypeCompteMockMvc.perform(put("/api/type-comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeCompte)))
            .andExpect(status().isOk());

        // Validate the TypeCompte in the database
        List<TypeCompte> typeCompteList = typeCompteRepository.findAll();
        assertThat(typeCompteList).hasSize(databaseSizeBeforeUpdate);
        TypeCompte testTypeCompte = typeCompteList.get(typeCompteList.size() - 1);
        assertThat(testTypeCompte.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeCompte.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeCompte.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeCompte() throws Exception {
        int databaseSizeBeforeUpdate = typeCompteRepository.findAll().size();

        // Create the TypeCompte

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeCompteMockMvc.perform(put("/api/type-comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCompte)))
            .andExpect(status().isCreated());

        // Validate the TypeCompte in the database
        List<TypeCompte> typeCompteList = typeCompteRepository.findAll();
        assertThat(typeCompteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeCompte() throws Exception {
        // Initialize the database
        typeCompteService.save(typeCompte);

        int databaseSizeBeforeDelete = typeCompteRepository.findAll().size();

        // Get the typeCompte
        restTypeCompteMockMvc.perform(delete("/api/type-comptes/{id}", typeCompte.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeCompte> typeCompteList = typeCompteRepository.findAll();
        assertThat(typeCompteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeCompte.class);
        TypeCompte typeCompte1 = new TypeCompte();
        typeCompte1.setId(1L);
        TypeCompte typeCompte2 = new TypeCompte();
        typeCompte2.setId(typeCompte1.getId());
        assertThat(typeCompte1).isEqualTo(typeCompte2);
        typeCompte2.setId(2L);
        assertThat(typeCompte1).isNotEqualTo(typeCompte2);
        typeCompte1.setId(null);
        assertThat(typeCompte1).isNotEqualTo(typeCompte2);
    }
}
