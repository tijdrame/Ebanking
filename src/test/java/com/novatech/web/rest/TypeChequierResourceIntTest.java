package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.TypeChequier;
import com.novatech.repository.TypeChequierRepository;
import com.novatech.service.TypeChequierService;
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
 * Test class for the TypeChequierResource REST controller.
 *
 * @see TypeChequierResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class TypeChequierResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private TypeChequierRepository typeChequierRepository;

    @Autowired
    private TypeChequierService typeChequierService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeChequierMockMvc;

    private TypeChequier typeChequier;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeChequierResource typeChequierResource = new TypeChequierResource(typeChequierService);
        this.restTypeChequierMockMvc = MockMvcBuilders.standaloneSetup(typeChequierResource)
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
    public static TypeChequier createEntity(EntityManager em) {
        TypeChequier typeChequier = new TypeChequier()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return typeChequier;
    }

    @Before
    public void initTest() {
        typeChequier = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeChequier() throws Exception {
        int databaseSizeBeforeCreate = typeChequierRepository.findAll().size();

        // Create the TypeChequier
        restTypeChequierMockMvc.perform(post("/api/type-chequiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeChequier)))
            .andExpect(status().isCreated());

        // Validate the TypeChequier in the database
        List<TypeChequier> typeChequierList = typeChequierRepository.findAll();
        assertThat(typeChequierList).hasSize(databaseSizeBeforeCreate + 1);
        TypeChequier testTypeChequier = typeChequierList.get(typeChequierList.size() - 1);
        assertThat(testTypeChequier.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeChequier.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeChequier.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createTypeChequierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeChequierRepository.findAll().size();

        // Create the TypeChequier with an existing ID
        typeChequier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeChequierMockMvc.perform(post("/api/type-chequiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeChequier)))
            .andExpect(status().isBadRequest());

        // Validate the TypeChequier in the database
        List<TypeChequier> typeChequierList = typeChequierRepository.findAll();
        assertThat(typeChequierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeChequiers() throws Exception {
        // Initialize the database
        typeChequierRepository.saveAndFlush(typeChequier);

        // Get all the typeChequierList
        restTypeChequierMockMvc.perform(get("/api/type-chequiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeChequier.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getTypeChequier() throws Exception {
        // Initialize the database
        typeChequierRepository.saveAndFlush(typeChequier);

        // Get the typeChequier
        restTypeChequierMockMvc.perform(get("/api/type-chequiers/{id}", typeChequier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeChequier.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeChequier() throws Exception {
        // Get the typeChequier
        restTypeChequierMockMvc.perform(get("/api/type-chequiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeChequier() throws Exception {
        // Initialize the database
        typeChequierService.save(typeChequier);

        int databaseSizeBeforeUpdate = typeChequierRepository.findAll().size();

        // Update the typeChequier
        TypeChequier updatedTypeChequier = typeChequierRepository.findOne(typeChequier.getId());
        // Disconnect from session so that the updates on updatedTypeChequier are not directly saved in db
        em.detach(updatedTypeChequier);
        updatedTypeChequier
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restTypeChequierMockMvc.perform(put("/api/type-chequiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeChequier)))
            .andExpect(status().isOk());

        // Validate the TypeChequier in the database
        List<TypeChequier> typeChequierList = typeChequierRepository.findAll();
        assertThat(typeChequierList).hasSize(databaseSizeBeforeUpdate);
        TypeChequier testTypeChequier = typeChequierList.get(typeChequierList.size() - 1);
        assertThat(testTypeChequier.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeChequier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeChequier.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeChequier() throws Exception {
        int databaseSizeBeforeUpdate = typeChequierRepository.findAll().size();

        // Create the TypeChequier

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeChequierMockMvc.perform(put("/api/type-chequiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeChequier)))
            .andExpect(status().isCreated());

        // Validate the TypeChequier in the database
        List<TypeChequier> typeChequierList = typeChequierRepository.findAll();
        assertThat(typeChequierList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeChequier() throws Exception {
        // Initialize the database
        typeChequierService.save(typeChequier);

        int databaseSizeBeforeDelete = typeChequierRepository.findAll().size();

        // Get the typeChequier
        restTypeChequierMockMvc.perform(delete("/api/type-chequiers/{id}", typeChequier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeChequier> typeChequierList = typeChequierRepository.findAll();
        assertThat(typeChequierList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeChequier.class);
        TypeChequier typeChequier1 = new TypeChequier();
        typeChequier1.setId(1L);
        TypeChequier typeChequier2 = new TypeChequier();
        typeChequier2.setId(typeChequier1.getId());
        assertThat(typeChequier1).isEqualTo(typeChequier2);
        typeChequier2.setId(2L);
        assertThat(typeChequier1).isNotEqualTo(typeChequier2);
        typeChequier1.setId(null);
        assertThat(typeChequier1).isNotEqualTo(typeChequier2);
    }
}
