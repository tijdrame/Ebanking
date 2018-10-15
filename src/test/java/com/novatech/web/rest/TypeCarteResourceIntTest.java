package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.TypeCarte;
import com.novatech.repository.TypeCarteRepository;
import com.novatech.service.TypeCarteService;
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
 * Test class for the TypeCarteResource REST controller.
 *
 * @see TypeCarteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class TypeCarteResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private TypeCarteRepository typeCarteRepository;

    @Autowired
    private TypeCarteService typeCarteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeCarteMockMvc;

    private TypeCarte typeCarte;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeCarteResource typeCarteResource = new TypeCarteResource(typeCarteService);
        this.restTypeCarteMockMvc = MockMvcBuilders.standaloneSetup(typeCarteResource)
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
    public static TypeCarte createEntity(EntityManager em) {
        TypeCarte typeCarte = new TypeCarte()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return typeCarte;
    }

    @Before
    public void initTest() {
        typeCarte = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeCarte() throws Exception {
        int databaseSizeBeforeCreate = typeCarteRepository.findAll().size();

        // Create the TypeCarte
        restTypeCarteMockMvc.perform(post("/api/type-cartes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCarte)))
            .andExpect(status().isCreated());

        // Validate the TypeCarte in the database
        List<TypeCarte> typeCarteList = typeCarteRepository.findAll();
        assertThat(typeCarteList).hasSize(databaseSizeBeforeCreate + 1);
        TypeCarte testTypeCarte = typeCarteList.get(typeCarteList.size() - 1);
        assertThat(testTypeCarte.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeCarte.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeCarte.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createTypeCarteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeCarteRepository.findAll().size();

        // Create the TypeCarte with an existing ID
        typeCarte.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeCarteMockMvc.perform(post("/api/type-cartes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCarte)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCarte in the database
        List<TypeCarte> typeCarteList = typeCarteRepository.findAll();
        assertThat(typeCarteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeCartes() throws Exception {
        // Initialize the database
        typeCarteRepository.saveAndFlush(typeCarte);

        // Get all the typeCarteList
        restTypeCarteMockMvc.perform(get("/api/type-cartes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeCarte.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getTypeCarte() throws Exception {
        // Initialize the database
        typeCarteRepository.saveAndFlush(typeCarte);

        // Get the typeCarte
        restTypeCarteMockMvc.perform(get("/api/type-cartes/{id}", typeCarte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeCarte.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeCarte() throws Exception {
        // Get the typeCarte
        restTypeCarteMockMvc.perform(get("/api/type-cartes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeCarte() throws Exception {
        // Initialize the database
        typeCarteService.save(typeCarte);

        int databaseSizeBeforeUpdate = typeCarteRepository.findAll().size();

        // Update the typeCarte
        TypeCarte updatedTypeCarte = typeCarteRepository.findOne(typeCarte.getId());
        // Disconnect from session so that the updates on updatedTypeCarte are not directly saved in db
        em.detach(updatedTypeCarte);
        updatedTypeCarte
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restTypeCarteMockMvc.perform(put("/api/type-cartes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeCarte)))
            .andExpect(status().isOk());

        // Validate the TypeCarte in the database
        List<TypeCarte> typeCarteList = typeCarteRepository.findAll();
        assertThat(typeCarteList).hasSize(databaseSizeBeforeUpdate);
        TypeCarte testTypeCarte = typeCarteList.get(typeCarteList.size() - 1);
        assertThat(testTypeCarte.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeCarte.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeCarte.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeCarte() throws Exception {
        int databaseSizeBeforeUpdate = typeCarteRepository.findAll().size();

        // Create the TypeCarte

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeCarteMockMvc.perform(put("/api/type-cartes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCarte)))
            .andExpect(status().isCreated());

        // Validate the TypeCarte in the database
        List<TypeCarte> typeCarteList = typeCarteRepository.findAll();
        assertThat(typeCarteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeCarte() throws Exception {
        // Initialize the database
        typeCarteService.save(typeCarte);

        int databaseSizeBeforeDelete = typeCarteRepository.findAll().size();

        // Get the typeCarte
        restTypeCarteMockMvc.perform(delete("/api/type-cartes/{id}", typeCarte.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeCarte> typeCarteList = typeCarteRepository.findAll();
        assertThat(typeCarteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeCarte.class);
        TypeCarte typeCarte1 = new TypeCarte();
        typeCarte1.setId(1L);
        TypeCarte typeCarte2 = new TypeCarte();
        typeCarte2.setId(typeCarte1.getId());
        assertThat(typeCarte1).isEqualTo(typeCarte2);
        typeCarte2.setId(2L);
        assertThat(typeCarte1).isNotEqualTo(typeCarte2);
        typeCarte1.setId(null);
        assertThat(typeCarte1).isNotEqualTo(typeCarte2);
    }
}
