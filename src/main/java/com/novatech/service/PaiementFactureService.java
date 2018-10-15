package com.novatech.service;

import com.novatech.domain.PaiementFacture;
import com.novatech.repository.PaiementFactureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PaiementFacture.
 */
@Service
@Transactional
public class PaiementFactureService {

    private final Logger log = LoggerFactory.getLogger(PaiementFactureService.class);

    private final PaiementFactureRepository paiementFactureRepository;

    public PaiementFactureService(PaiementFactureRepository paiementFactureRepository) {
        this.paiementFactureRepository = paiementFactureRepository;
    }

    /**
     * Save a paiementFacture.
     *
     * @param paiementFacture the entity to save
     * @return the persisted entity
     */
    public PaiementFacture save(PaiementFacture paiementFacture) {
        log.debug("Request to save PaiementFacture : {}", paiementFacture);
        return paiementFactureRepository.save(paiementFacture);
    }

    /**
     * Get all the paiementFactures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PaiementFacture> findAll(Pageable pageable) {
        log.debug("Request to get all PaiementFactures");
        return paiementFactureRepository.findAll(pageable);
    }

    /**
     * Get one paiementFacture by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PaiementFacture findOne(Long id) {
        log.debug("Request to get PaiementFacture : {}", id);
        return paiementFactureRepository.findOne(id);
    }

    /**
     * Delete the paiementFacture by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PaiementFacture : {}", id);
        paiementFactureRepository.delete(id);
    }
}
