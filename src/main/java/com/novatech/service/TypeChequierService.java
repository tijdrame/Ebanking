package com.novatech.service;

import com.novatech.domain.TypeChequier;
import com.novatech.repository.TypeChequierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TypeChequier.
 */
@Service
@Transactional
public class TypeChequierService {

    private final Logger log = LoggerFactory.getLogger(TypeChequierService.class);

    private final TypeChequierRepository typeChequierRepository;

    public TypeChequierService(TypeChequierRepository typeChequierRepository) {
        this.typeChequierRepository = typeChequierRepository;
    }

    /**
     * Save a typeChequier.
     *
     * @param typeChequier the entity to save
     * @return the persisted entity
     */
    public TypeChequier save(TypeChequier typeChequier) {
        log.debug("Request to save TypeChequier : {}", typeChequier);
        typeChequier.deleted(false);
        return typeChequierRepository.save(typeChequier);
    }

    /**
     * Get all the typeChequiers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeChequier> findAll(Pageable pageable) {
        log.debug("Request to get all TypeChequiers");
        return typeChequierRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one typeChequier by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TypeChequier findOne(Long id) {
        log.debug("Request to get TypeChequier : {}", id);
        return typeChequierRepository.findOne(id);
    }

    /**
     * Delete the typeChequier by id.
     *
     * @param id the id of the entity
     */
    public void delete(TypeChequier id) {
        log.debug("Request to delete TypeChequier : {}", id);
        id.deleted(true);
        typeChequierRepository.save(id);
    }
}
