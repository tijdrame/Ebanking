package com.novatech.service;

import com.novatech.domain.Abonne;
import com.novatech.repository.AbonneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


/**
 * Service Implementation for managing Abonne.
 */
@Service
@Transactional
public class AbonneService {

    private final Logger log = LoggerFactory.getLogger(AbonneService.class);

    private final AbonneRepository abonneRepository;
    private final UserService userService;
    private final LogEvenementService logEvenementService;


    public AbonneService(AbonneRepository abonneRepository, UserService userService,
                         LogEvenementService logEvenementService) {
        this.abonneRepository = abonneRepository;
        this.userService = userService;
        this.logEvenementService = logEvenementService;
    }

    /**
     * Save a abonne.
     *
     * @param abonne the entity to save
     * @return the persisted entity
     */
    public Abonne save(Abonne abonne) {
        log.debug("Request to save Abonne : {}", abonne);
        abonne.deleted(false).dateCreated(LocalDate.now()).userCreated(userService.getUserWithAuthorities().get());
        abonne = abonneRepository.save(abonne);
        logEvenementService.createLogEvent(Abonne.class.getSimpleName(), userService.getUserWithAuthorities().get(),
            "CREATION", abonne.getId());
        return abonne;
    }

    public Abonne update(Abonne abonne) {
        log.debug("Request to update Abonne : {}", abonne);
        abonne.deleted(false).dateUpdated(LocalDate.now()).userUpdated(userService.getUserWithAuthorities().get());
        abonne = abonneRepository.save(abonne);
        logEvenementService.createLogEvent(Abonne.class.getSimpleName(), userService.getUserWithAuthorities().get(),
            "MODIFICATION", abonne.getId());
        return abonne;
    }

    /**
     * Get all the abonnes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Abonne> findAll(Pageable pageable) {
        log.debug("Request to get all Abonnes");
        return abonneRepository.findByDeletedFalseOrderByUser_LastName(pageable);
    }

    /**
     * Get one abonne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Abonne findOne(Long id) {
        log.debug("Request to get Abonne : {}", id);
        return abonneRepository.findOne(id);
    }

    /**
     * Delete the abonne by id.
     *
     * @param abonne the id of the entity
     */
    public void delete(Abonne abonne) {
        log.debug("Request to delete Abonne : {}", abonne);
        abonne.deleted(true).dateDeleted(LocalDate.now()).userDeleted(userService.getUserWithAuthorities().get());
        abonne.getUser().setActivated(false);
        userService.updateUs(abonne.getUser());
        logEvenementService.createLogEvent(Abonne.class.getSimpleName(), userService.getUserWithAuthorities().get(),
            "SUPPRESSION", abonne.getId());
        abonneRepository.save(abonne);
    }
}
