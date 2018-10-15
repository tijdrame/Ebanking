package com.novatech.web.rest;

import com.novatech.EbankingApp;

import com.novatech.domain.TypeOpposition;
import com.novatech.repository.TypeOppositionRepository;
import com.novatech.service.TypeOppositionService;
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
 * Test class for the TypeOppositionResource REST controller.
 *
 * @see TypeOppositionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbankingApp.class)
public class TypeOppositionResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    @Autowired
    private TypeOppositionRepository typeOppositionRepository;

    @Autowired
    private TypeOppositionService typeOppositionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeOppositionMockMvc;

    private TypeOpposition typeOpposition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeOppositionResource typeOppositionResource = new TypeOppositionResource(typeOppositionService);
        this.restTypeOppositionMockMvc = MockMvcBuilders.standaloneSetup(typeOppositionResource)
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
    public static TypeOpposition createEntity(EntityManager em) {
        TypeOpposition typeOpposition = new TypeOpposition()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE)
            .deleted(DEFAULT_DELETED);
        return typeOpposition;
    }

    @Before
    public void initTest() {
        typeOpposition = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeOpposition() throws Exception {
        int databaseSizeBeforeCreate = typeOppositionRepository.findAll().size();

        // Create the TypeOpposition
        restTypeOppositionMockMvc.perform(post("/api/type-oppositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeOpposition)))
            .andExpect(status().isCreated());

        // Validate the TypeOpposition in the database
        List<TypeOpposition> typeOppositionList = typeOppositionRepository.findAll();
        assertThat(typeOppositionList).hasSize(databaseSizeBeforeCreate + 1);
        TypeOpposition testTypeOpposition = typeOppositionList.get(typeOppositionList.size() - 1);
        assertThat(testTypeOpposition.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeOpposition.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeOpposition.isDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    @Transactional
    public void createTypeOppositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeOppositionRepository.findAll().size();

        // Create the TypeOpposition with an existing ID
        typeOpposition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeOppositionMockMvc.perform(post("/api/type-oppositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeOpposition)))
            .andExpect(status().isBadRequest());

        // Validate the TypeOpposition in the database
        List<TypeOpposition> typeOppositionList = typeOppositionRepository.findAll();
        assertThat(typeOppositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeOppositions() throws Exception {
        // Initialize the database
        typeOppositionRepository.saveAndFlush(typeOpposition);

        // Get all the typeOppositionList
        restTypeOppositionMockMvc.perform(get("/api/type-oppositions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeOpposition.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getTypeOpposition() throws Exception {
        // Initialize the database
        typeOppositionRepository.saveAndFlush(typeOpposition);

        // Get the typeOpposition
        restTypeOppositionMockMvc.perform(get("/api/type-oppositions/{id}", typeOpposition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeOpposition.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeOpposition() throws Exception {
        // Get the typeOpposition
        restTypeOppositionMockMvc.perform(get("/api/type-oppositions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeOpposition() throws Exception {
        // Initialize the database
        typeOppositionService.save(typeOpposition);

        int databaseSizeBeforeUpdate = typeOppositionRepository.findAll().size();

        // Update the typeOpposition
        TypeOpposition updatedTypeOpposition = typeOppositionRepository.findOne(typeOpposition.getId());
        // Disconnect from session so that the updates on updatedTypeOpposition are not directly saved in db
        em.detach(updatedTypeOpposition);
        updatedTypeOpposition
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE)
            .deleted(UPDATED_DELETED);

        restTypeOppositionMockMvc.perform(put("/api/type-oppositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeOpposition)))
            .andExpect(status().isOk());

        // Validate the TypeOpposition in the database
        List<TypeOpposition> typeOppositionList = typeOppositionRepository.findAll();
        assertThat(typeOppositionList).hasSize(databaseSizeBeforeUpdate);
        TypeOpposition testTypeOpposition = typeOppositionList.get(typeOppositionList.size() - 1);
        assertThat(testTypeOpposition.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeOpposition.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeOpposition.isDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeOpposition() throws Exception {
        int databaseSizeBeforeUpdate = typeOppositionRepository.findAll().size();

        // Create the TypeOpposition

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypeOppositionMockMvc.perform(put("/api/type-oppositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeOpposition)))
            .andExpect(status().isCreated());

        // Validate the TypeOpposition in the database
        List<TypeOpposition> typeOppositionList = typeOppositionRepository.findAll();
        assertThat(typeOppositionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTypeOpposition() throws Exception {
        // Initialize the database
        typeOppositionService.save(typeOpposition);

        int databaseSizeBeforeDelete = typeOppositionRepository.findAll().size();

        // Get the typeOpposition
        restTypeOppositionMockMvc.perform(delete("/api/type-oppositions/{id}", typeOpposition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeOpposition> typeOppositionList = typeOppositionRepository.findAll();
        assertThat(typeOppositionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOpposition.class);
        TypeOpposition typeOpposition1 = new TypeOpposition();
        typeOpposition1.setId(1L);
        TypeOpposition typeOpposition2 = new TypeOpposition();
        typeOpposition2.setId(typeOpposition1.getId());
        assertThat(typeOpposition1).isEqualTo(typeOpposition2);
        typeOpposition2.setId(2L);
        assertThat(typeOpposition1).isNotEqualTo(typeOpposition2);
        typeOpposition1.setId(null);
        assertThat(typeOpposition1).isNotEqualTo(typeOpposition2);
    }
}
