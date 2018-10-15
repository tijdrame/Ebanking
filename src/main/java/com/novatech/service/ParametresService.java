package com.novatech.service;

import com.novatech.domain.Parametres;
import com.novatech.repository.ParametresRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Parametres.
 */
@Service
@Transactional
public class ParametresService {

    private final Logger log = LoggerFactory.getLogger(ParametresService.class);

    private final ParametresRepository parametresRepository;

    public ParametresService(ParametresRepository parametresRepository) {
        this.parametresRepository = parametresRepository;
    }

    /**
     * Save a parametres.
     *
     * @param parametres the entity to save
     * @return the persisted entity
     */
    public Parametres save(Parametres parametres) {
        log.debug("Request to save Parametres : {}", parametres);
        parametres.deleted(false);
        return parametresRepository.save(parametres);
    }

    /**
     * Get all the parametres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Parametres> findAll(Pageable pageable) {
        log.debug("Request to get all Parametres");
        return parametresRepository.findByDeletedFalseOrderByCle(pageable);
    }

    /**
     * Get one parametres by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Parametres findOne(Long id) {
        log.debug("Request to get Parametres : {}", id);
        return parametresRepository.findOne(id);
    }

    /**
     * Delete the parametres by id.
     *
     * @param id the id of the entity
     */
    public void delete(Parametres id) {
        log.debug("Request to delete Parametres : {}", id);
        id.deleted(true);
        parametresRepository.save(id);
    }
}
