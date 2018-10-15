package com.novatech.service;

import com.novatech.domain.NbreFeuillesChequier;
import com.novatech.repository.NbreFeuillesChequierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing NbreFeuillesChequier.
 */
@Service
@Transactional
public class NbreFeuillesChequierService {

    private final Logger log = LoggerFactory.getLogger(NbreFeuillesChequierService.class);

    private final NbreFeuillesChequierRepository nbreFeuillesChequierRepository;

    public NbreFeuillesChequierService(NbreFeuillesChequierRepository nbreFeuillesChequierRepository) {
        this.nbreFeuillesChequierRepository = nbreFeuillesChequierRepository;
    }

    /**
     * Save a nbreFeuillesChequier.
     *
     * @param nbreFeuillesChequier the entity to save
     * @return the persisted entity
     */
    public NbreFeuillesChequier save(NbreFeuillesChequier nbreFeuillesChequier) {
        log.debug("Request to save NbreFeuillesChequier : {}", nbreFeuillesChequier);
        nbreFeuillesChequier.deleted(false);
        return nbreFeuillesChequierRepository.save(nbreFeuillesChequier);
    }

    /**
     * Get all the nbreFeuillesChequiers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NbreFeuillesChequier> findAll(Pageable pageable) {
        log.debug("Request to get all NbreFeuillesChequiers");
        return nbreFeuillesChequierRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one nbreFeuillesChequier by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public NbreFeuillesChequier findOne(Long id) {
        log.debug("Request to get NbreFeuillesChequier : {}", id);
        return nbreFeuillesChequierRepository.findOne(id);
    }

    /**
     * Delete the nbreFeuillesChequier by id.
     *
     * @param id the id of the entity
     */
    public void delete(NbreFeuillesChequier id) {
        log.debug("Request to delete NbreFeuillesChequier : {}", id);
        id.deleted(true);
        nbreFeuillesChequierRepository.save(id);
    }
}
