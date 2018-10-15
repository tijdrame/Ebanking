package com.novatech.service;

import com.novatech.domain.Beneficiaire;
import com.novatech.repository.BeneficiaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Beneficiaire.
 */
@Service
@Transactional
public class BeneficiaireService {

    private final Logger log = LoggerFactory.getLogger(BeneficiaireService.class);

    private final BeneficiaireRepository beneficiaireRepository;

    public BeneficiaireService(BeneficiaireRepository beneficiaireRepository) {
        this.beneficiaireRepository = beneficiaireRepository;
    }

    /**
     * Save a beneficiaire.
     *
     * @param beneficiaire the entity to save
     * @return the persisted entity
     */
    public Beneficiaire save(Beneficiaire beneficiaire) {
        log.debug("Request to save Beneficiaire : {}", beneficiaire);
        return beneficiaireRepository.save(beneficiaire);
    }

    /**
     * Get all the beneficiaires.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Beneficiaire> findAll(Pageable pageable) {
        log.debug("Request to get all Beneficiaires");
        return beneficiaireRepository.findAll(pageable);
    }

    /**
     * Get one beneficiaire by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Beneficiaire findOne(Long id) {
        log.debug("Request to get Beneficiaire : {}", id);
        return beneficiaireRepository.findOne(id);
    }

    /**
     * Delete the beneficiaire by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Beneficiaire : {}", id);
        beneficiaireRepository.delete(id);
    }
}
