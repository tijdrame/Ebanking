package com.novatech.service;

import com.novatech.domain.Facturier;
import com.novatech.repository.FacturierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Facturier.
 */
@Service
@Transactional
public class FacturierService {

    private final Logger log = LoggerFactory.getLogger(FacturierService.class);

    private final FacturierRepository facturierRepository;

    public FacturierService(FacturierRepository facturierRepository) {
        this.facturierRepository = facturierRepository;
    }

    /**
     * Save a facturier.
     *
     * @param facturier the entity to save
     * @return the persisted entity
     */
    public Facturier save(Facturier facturier) {
        log.debug("Request to save Facturier : {}", facturier);
        facturier.deleted(false);
        return facturierRepository.save(facturier);
    }

    /**
     * Get all the facturiers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Facturier> findAll(Pageable pageable) {
        log.debug("Request to get all Facturiers");
        return facturierRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one facturier by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Facturier findOne(Long id) {
        log.debug("Request to get Facturier : {}", id);
        return facturierRepository.findOne(id);
    }

    /**
     * Delete the facturier by id.
     *
     * @param id the id of the entity
     */
    public void delete(Facturier id) {
        log.debug("Request to delete Facturier : {}", id);
        id.deleted(true);
        facturierRepository.save(id);
    }
}
