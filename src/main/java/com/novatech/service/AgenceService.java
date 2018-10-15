package com.novatech.service;

import com.novatech.domain.Agence;
import com.novatech.repository.AgenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Agence.
 */
@Service
@Transactional
public class AgenceService {

    private final Logger log = LoggerFactory.getLogger(AgenceService.class);

    private final AgenceRepository agenceRepository;
    private final UserService userService;
    private final LogEvenementService logEvenementService;

    public AgenceService(AgenceRepository agenceRepository, UserService userService,
                         LogEvenementService logEvenementService) {
        this.agenceRepository = agenceRepository;
        this.userService = userService;
        this.logEvenementService = logEvenementService;
    }

    /**
     * Save a agence.
     *
     * @param agence the entity to save
     * @return the persisted entity
     */
    public Agence save(Agence agence) {
        log.debug("Request to save Agence : {}", agence);
        agence.deleted(false);
        agence = agenceRepository.save(agence);
        logEvenementService.createLogEvent(Agence.class.getSimpleName(), userService.getUserWithAuthorities().get(),
            "CREATION", agence.getId());
        return agence;
    }

    public Agence update(Agence agence) {
        log.debug("Request to update Agence : {}", agence);
        agence.deleted(false);
        agence = agenceRepository.save(agence);
        logEvenementService.createLogEvent(Agence.class.getSimpleName(), userService.getUserWithAuthorities().get(),
            "MODIFICATION", agence.getId());
        return agence;
    }

    /**
     * Get all the agences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Agence> findAll(Pageable pageable) {
        log.debug("Request to get all Agences");
        return agenceRepository.findByDeletedFalseOrderByNom(pageable);
    }

    /**
     * Get one agence by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Agence findOne(Long id) {
        log.debug("Request to get Agence : {}", id);
        return agenceRepository.findOne(id);
    }

    /**
     * Delete the agence by id.
     *
     * @param agence the id of the entity
     */
    public void delete(Agence agence) {
        log.debug("Request to delete Agence : {}", agence);
        agence.deleted(true);
        agenceRepository.save(agence);
        logEvenementService.createLogEvent(Agence.class.getSimpleName(), userService.getUserWithAuthorities().get(),
            "SUPPRESSION", agence.getId());
    }
}
