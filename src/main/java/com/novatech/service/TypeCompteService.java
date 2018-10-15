package com.novatech.service;

import com.novatech.domain.TypeCompte;
import com.novatech.repository.TypeCompteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TypeCompte.
 */
@Service
@Transactional
public class TypeCompteService {

    private final Logger log = LoggerFactory.getLogger(TypeCompteService.class);

    private final TypeCompteRepository typeCompteRepository;

    public TypeCompteService(TypeCompteRepository typeCompteRepository) {
        this.typeCompteRepository = typeCompteRepository;
    }

    /**
     * Save a typeCompte.
     *
     * @param typeCompte the entity to save
     * @return the persisted entity
     */
    public TypeCompte save(TypeCompte typeCompte) {
        log.debug("Request to save TypeCompte : {}", typeCompte);
        typeCompte.deleted(false);
        return typeCompteRepository.save(typeCompte);
    }

    /**
     * Get all the typeComptes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeCompte> findAll(Pageable pageable) {
        log.debug("Request to get all TypeComptes");
        return typeCompteRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one typeCompte by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TypeCompte findOne(Long id) {
        log.debug("Request to get TypeCompte : {}", id);
        return typeCompteRepository.findOne(id);
    }

    /**
     * Delete the typeCompte by id.
     *
     * @param id the id of the entity
     */
    public void delete(TypeCompte id) {
        log.debug("Request to delete TypeCompte : {}", id);
        id.deleted(true);
        typeCompteRepository.save(id);
    }
}
