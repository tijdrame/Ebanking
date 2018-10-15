package com.novatech.service;

import com.novatech.domain.Abonne;
import com.novatech.domain.Compte;
import com.novatech.domain.User;
import com.novatech.repository.AbonneRepository;
import com.novatech.repository.CompteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Compte.
 */
@Service
@Transactional
public class CompteService {

    private final Logger log = LoggerFactory.getLogger(CompteService.class);

    private final CompteRepository compteRepository;

    private final AbonneRepository abonneRepository;
    private final LogEvenementService logEvenementService;
    private final UserService userService;

    public CompteService(CompteRepository compteRepository, AbonneRepository abonneRepository,
                         LogEvenementService logEvenementService, UserService userService) {
        this.compteRepository = compteRepository;
        this.abonneRepository = abonneRepository;
        this.logEvenementService = logEvenementService;
        this.userService = userService;
    }

    /**
     * Save a compte.
     *
     * @param compte the entity to save
     * @return the persisted entity
     */
    public Compte save(Compte compte) {
        log.debug("Request to save Compte : {}", compte);
        Abonne abonne = abonneRepository.findOne(compte.getAbonne().getId());
        compte.abonne(abonne).deleted(false);

        compte = compteRepository.save(compte);
        logEvenementService.createLogEvent(Abonne.class.getSimpleName(), compte.getAbonne().getUser(),
            "CREATION", compte.getId());
        return compte;
    }

    public Compte update(Compte compte) {
        log.debug("Request to save Compte : {}", compte);
        compte = compteRepository.save(compte);
        logEvenementService.createLogEvent(Abonne.class.getSimpleName(), compte.getAbonne().getUser(),
            "MODIFICATION", compte.getId());
        return compte;
    }

    /**
     * Get all the comptes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Compte> findAll(Pageable pageable) {
        log.debug("Request to get all Comptes");
        Abonne abonne = abonneRepository.findOne(userService.getUserWithAuthorities().get().getId());
        log.debug("abonne==="+abonne);
        log.debug("user==="+userService.getUserWithAuthorities().get().getFirstName());
        if(abonne!=null)
            return compteRepository.findByAbonneAndDeletedFalseOrderByNumero(abonne,pageable);
        else
            return compteRepository.findByDeletedFalseOrderByNumero(pageable);
    }

    /**
     * Get one compte by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Compte findOne(Long id) {
        log.debug("Request to get Compte : {}", id);
        return compteRepository.findOne(id);
    }

    /**
     * Delete the compte by id.
     *
     * @param id the id of the entity
     */
    public void delete(Compte id) {
        log.debug("Request to delete Compte : {}", id);
        id.deleted(true);
        compteRepository.delete(id);
        logEvenementService.createLogEvent(Abonne.class.getSimpleName(), id.getAbonne().getUser(),
            "SUPPRESSION", id.getId());
    }
}
