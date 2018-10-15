package com.novatech.service;

import com.novatech.domain.TypeAbonne;
import com.novatech.repository.TypeAbonneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TypeAbonne.
 */
@Service
@Transactional
public class TypeAbonneService {

    private final Logger log = LoggerFactory.getLogger(TypeAbonneService.class);

    private final TypeAbonneRepository typeAbonneRepository;

    public TypeAbonneService(TypeAbonneRepository typeAbonneRepository) {
        this.typeAbonneRepository = typeAbonneRepository;
    }

    /**
     * Save a typeAbonne.
     *
     * @param typeAbonne the entity to save
     * @return the persisted entity
     */
    public TypeAbonne save(TypeAbonne typeAbonne) {
        log.debug("Request to save TypeAbonne : {}", typeAbonne);
        typeAbonne.deleted(false);
        return typeAbonneRepository.save(typeAbonne);
    }

    /**
     * Get all the typeAbonnes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeAbonne> findAll(Pageable pageable) {
        log.debug("Request to get all TypeAbonnes");
        return typeAbonneRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one typeAbonne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TypeAbonne findOne(Long id) {
        log.debug("Request to get TypeAbonne : {}", id);
        return typeAbonneRepository.findOne(id);
    }

    /**
     * Delete the typeAbonne by id.
     *
     * @param id the id of the entity
     */
    public void delete(TypeAbonne id) {
        log.debug("Request to delete TypeAbonne : {}", id);
        id.deleted(true);
        typeAbonneRepository.save(id);
    }
}
