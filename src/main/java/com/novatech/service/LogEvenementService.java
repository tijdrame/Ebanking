package com.novatech.service;

import com.novatech.domain.LogEvenement;
import com.novatech.domain.User;
import com.novatech.repository.LogEvenementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;


/**
 * Service Implementation for managing LogEvenement.
 */
@Service
@Transactional
public class LogEvenementService {

    private final Logger log = LoggerFactory.getLogger(LogEvenementService.class);

    private final LogEvenementRepository logEvenementRepository;

    public LogEvenementService(LogEvenementRepository logEvenementRepository) {
        this.logEvenementRepository = logEvenementRepository;
    }

    /**
     * Save a logEvenement.
     *
     * @param logEvenement the entity to save
     * @return the persisted entity
     */
    public LogEvenement save(LogEvenement logEvenement) {
        log.debug("Request to save LogEvenement : {}", logEvenement);
        return logEvenementRepository.save(logEvenement);
    }

    /**
     * Get all the logEvenements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LogEvenement> findAll(Pageable pageable) {
        log.debug("Request to get all LogEvenements");
        return logEvenementRepository.findAll(pageable);
    }

    /**
     * Get one logEvenement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public LogEvenement findOne(Long id) {
        log.debug("Request to get LogEvenement : {}", id);
        return logEvenementRepository.findOne(id);
    }

    /**
     * Delete the logEvenement by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LogEvenement : {}", id);
        logEvenementRepository.delete(id);
    }

    public LogEvenement createLogEvent(String entityName, User user, String action, Long idObjet){
        LogEvenement logEvenement = new LogEvenement();
        logEvenement.setCodeObjet(idObjet);
        logEvenement.setUserCreated(user);
        logEvenement.setEntityName(entityName);//ex Compte.class.getSimpleName()
        logEvenement.setEventName(action);
        logEvenement.setAdresseMac("");
        logEvenement.setAdresseIP("");
        logEvenement.setDateCreated(Instant.now());
        this.save(logEvenement);
        return logEvenement;
    }
}
