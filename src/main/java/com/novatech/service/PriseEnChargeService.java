package com.novatech.service;

import com.novatech.domain.PriseEnCharge;
import com.novatech.repository.PriseEnChargeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PriseEnCharge.
 */
@Service
@Transactional
public class PriseEnChargeService {

    private final Logger log = LoggerFactory.getLogger(PriseEnChargeService.class);

    private final PriseEnChargeRepository priseEnChargeRepository;

    public PriseEnChargeService(PriseEnChargeRepository priseEnChargeRepository) {
        this.priseEnChargeRepository = priseEnChargeRepository;
    }

    /**
     * Save a priseEnCharge.
     *
     * @param priseEnCharge the entity to save
     * @return the persisted entity
     */
    public PriseEnCharge save(PriseEnCharge priseEnCharge) {
        log.debug("Request to save PriseEnCharge : {}", priseEnCharge);
        priseEnCharge.deleted(false);
        return priseEnChargeRepository.save(priseEnCharge);
    }

    /**
     * Get all the priseEnCharges.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PriseEnCharge> findAll(Pageable pageable) {
        log.debug("Request to get all PriseEnCharges");
        return priseEnChargeRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one priseEnCharge by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PriseEnCharge findOne(Long id) {
        log.debug("Request to get PriseEnCharge : {}", id);
        return priseEnChargeRepository.findOne(id);
    }

    /**
     * Delete the priseEnCharge by id.
     *
     * @param id the id of the entity
     */
    public void delete(PriseEnCharge id) {
        log.debug("Request to delete PriseEnCharge : {}", id);
        id.deleted(true);
        priseEnChargeRepository.save(id);
    }
}
