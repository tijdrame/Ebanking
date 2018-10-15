package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.Agence;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.AgenceService;
import com.novatech.web.rest.errors.BadRequestAlertException;
import com.novatech.web.rest.util.HeaderUtil;
import com.novatech.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Agence.
 */
@RestController
@RequestMapping("/api")
public class AgenceResource {

    private final Logger log = LoggerFactory.getLogger(AgenceResource.class);

    private static final String ENTITY_NAME = "agence";

    private final AgenceService agenceService;

    public AgenceResource(AgenceService agenceService) {
        this.agenceService = agenceService;
    }

    /**
     * POST  /agences : Create a new agence.
     *
     * @param agence the agence to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agence, or with status 400 (Bad Request) if the agence has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agences")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Agence> createAgence(@RequestBody Agence agence) throws URISyntaxException {
        log.debug("REST request to save Agence : {}", agence);
        if (agence.getId() != null) {
            throw new BadRequestAlertException("A new agence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Agence result = agenceService.save(agence);
        return ResponseEntity.created(new URI("/api/agences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agences : Updates an existing agence.
     *
     * @param agence the agence to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agence,
     * or with status 400 (Bad Request) if the agence is not valid,
     * or with status 500 (Internal Server Error) if the agence couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agences")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Agence> updateAgence(@RequestBody Agence agence) throws URISyntaxException {
        log.debug("REST request to update Agence : {}", agence);
        if (agence.getId() == null) {
            return createAgence(agence);
        }
        Agence result = agenceService.update(agence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agence.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agences : get all the agences.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of agences in body
     */
    @GetMapping("/agences")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<List<Agence>> getAllAgences(Pageable pageable) {
        log.debug("REST request to get a page of Agences");
        Page<Agence> page = agenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/agences");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /agences/:id : get the "id" agence.
     *
     * @param id the id of the agence to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agence, or with status 404 (Not Found)
     */
    @GetMapping("/agences/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Agence> getAgence(@PathVariable Long id) {
        log.debug("REST request to get Agence : {}", id);
        Agence agence = agenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(agence));
    }

    /**
     * DELETE  /agences/:id : delete the "id" agence.
     *
     * @param id the id of the agence to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agences/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deleteAgence(@PathVariable Long id) {
        log.debug("REST request to delete Agence : {}", id);
        Agence agence = agenceService.findOne(id);
        agenceService.delete(agence);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
