package com.novatech.service;

import com.novatech.domain.TypeCarte;
import com.novatech.repository.TypeCarteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TypeCarte.
 */
@Service
@Transactional
public class TypeCarteService {

    private final Logger log = LoggerFactory.getLogger(TypeCarteService.class);

    private final TypeCarteRepository typeCarteRepository;

    public TypeCarteService(TypeCarteRepository typeCarteRepository) {
        this.typeCarteRepository = typeCarteRepository;
    }

    /**
     * Save a typeCarte.
     *
     * @param typeCarte the entity to save
     * @return the persisted entity
     */
    public TypeCarte save(TypeCarte typeCarte) {
        log.debug("Request to save TypeCarte : {}", typeCarte);
        typeCarte.deleted(false);
        return typeCarteRepository.save(typeCarte);
    }

    /**
     * Get all the typeCartes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeCarte> findAll(Pageable pageable) {
        log.debug("Request to get all TypeCartes");
        return typeCarteRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one typeCarte by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TypeCarte findOne(Long id) {
        log.debug("Request to get TypeCarte : {}", id);
        return typeCarteRepository.findOne(id);
    }

    /**
     * Delete the typeCarte by id.
     *
     * @param id the id of the entity
     */
    public void delete(TypeCarte id) {
        log.debug("Request to delete TypeCarte : {}", id);
        id.deleted(true);
        typeCarteRepository.save(id);
    }
}
