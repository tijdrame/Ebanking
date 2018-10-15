package com.novatech.service;

import com.novatech.domain.BanquesPartenaires;
import com.novatech.repository.BanquesPartenairesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BanquesPartenaires.
 */
@Service
@Transactional
public class BanquesPartenairesService {

    private final Logger log = LoggerFactory.getLogger(BanquesPartenairesService.class);

    private final BanquesPartenairesRepository banquesPartenairesRepository;

    public BanquesPartenairesService(BanquesPartenairesRepository banquesPartenairesRepository) {
        this.banquesPartenairesRepository = banquesPartenairesRepository;
    }

    /**
     * Save a banquesPartenaires.
     *
     * @param banquesPartenaires the entity to save
     * @return the persisted entity
     */
    public BanquesPartenaires save(BanquesPartenaires banquesPartenaires) {
        log.debug("Request to save BanquesPartenaires : {}", banquesPartenaires);
        banquesPartenaires.deleted(false);
        return banquesPartenairesRepository.save(banquesPartenaires);
    }

    /**
     * Get all the banquesPartenaires.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BanquesPartenaires> findAll(Pageable pageable) {
        log.debug("Request to get all BanquesPartenaires");
        return banquesPartenairesRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one banquesPartenaires by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public BanquesPartenaires findOne(Long id) {
        log.debug("Request to get BanquesPartenaires : {}", id);
        return banquesPartenairesRepository.findOne(id);
    }

    /**
     * Delete the banquesPartenaires by id.
     *
     * @param id the id of the entity
     */
    public void delete(BanquesPartenaires id) {
        log.debug("Request to delete BanquesPartenaires : {}", id);
        id.deleted(true);
        banquesPartenairesRepository.save(id);
    }
}
