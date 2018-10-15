package com.novatech.service;

import com.novatech.domain.TypeOpposition;
import com.novatech.repository.TypeOppositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TypeOpposition.
 */
@Service
@Transactional
public class TypeOppositionService {

    private final Logger log = LoggerFactory.getLogger(TypeOppositionService.class);

    private final TypeOppositionRepository typeOppositionRepository;

    public TypeOppositionService(TypeOppositionRepository typeOppositionRepository) {
        this.typeOppositionRepository = typeOppositionRepository;
    }

    /**
     * Save a typeOpposition.
     *
     * @param typeOpposition the entity to save
     * @return the persisted entity
     */
    public TypeOpposition save(TypeOpposition typeOpposition) {
        log.debug("Request to save TypeOpposition : {}", typeOpposition);
        typeOpposition.deleted(false);
        return typeOppositionRepository.save(typeOpposition);
    }

    /**
     * Get all the typeOppositions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeOpposition> findAll(Pageable pageable) {
        log.debug("Request to get all TypeOppositions");
        return typeOppositionRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one typeOpposition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TypeOpposition findOne(Long id) {
        log.debug("Request to get TypeOpposition : {}", id);
        return typeOppositionRepository.findOne(id);
    }

    /**
     * Delete the typeOpposition by id.
     *
     * @param id the id of the entity
     */
    public void delete(TypeOpposition id) {
        log.debug("Request to delete TypeOpposition : {}", id);
        id.deleted(true);
        typeOppositionRepository.save(id);
    }
}
