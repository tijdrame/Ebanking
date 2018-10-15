package com.novatech.service;

import com.novatech.domain.Devise;
import com.novatech.repository.DeviseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Devise.
 */
@Service
@Transactional
public class DeviseService {

    private final Logger log = LoggerFactory.getLogger(DeviseService.class);

    private final DeviseRepository deviseRepository;

    public DeviseService(DeviseRepository deviseRepository) {
        this.deviseRepository = deviseRepository;
    }

    /**
     * Save a devise.
     *
     * @param devise the entity to save
     * @return the persisted entity
     */
    public Devise save(Devise devise) {
        log.debug("Request to save Devise : {}", devise);
        devise.deleted(false);
        return deviseRepository.save(devise);
    }

    /**
     * Get all the devises.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Devise> findAll(Pageable pageable) {
        log.debug("Request to get all Devises");
        return deviseRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one devise by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Devise findOne(Long id) {
        log.debug("Request to get Devise : {}", id);
        return deviseRepository.findOne(id);
    }

    /**
     * Delete the devise by id.
     *
     * @param id the id of the entity
     */
    public void delete(Devise id) {
        log.debug("Request to delete Devise : {}", id);
        id.deleted(true);
        deviseRepository.save(id);
    }
}
