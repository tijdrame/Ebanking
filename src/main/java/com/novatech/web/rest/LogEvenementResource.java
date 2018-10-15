package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.LogEvenement;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.LogEvenementService;
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
 * REST controller for managing LogEvenement.
 */
@RestController
@RequestMapping("/api")
public class LogEvenementResource {

    private final Logger log = LoggerFactory.getLogger(LogEvenementResource.class);

    private static final String ENTITY_NAME = "logEvenement";

    private final LogEvenementService logEvenementService;

    public LogEvenementResource(LogEvenementService logEvenementService) {
        this.logEvenementService = logEvenementService;
    }

    /**
     * POST  /log-evenements : Create a new logEvenement.
     *
     * @param logEvenement the logEvenement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new logEvenement, or with status 400 (Bad Request) if the logEvenement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/log-evenements")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<LogEvenement> createLogEvenement(@RequestBody LogEvenement logEvenement) throws URISyntaxException {
        log.debug("REST request to save LogEvenement : {}", logEvenement);
        if (logEvenement.getId() != null) {
            throw new BadRequestAlertException("A new logEvenement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogEvenement result = logEvenementService.save(logEvenement);
        return ResponseEntity.created(new URI("/api/log-evenements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /log-evenements : Updates an existing logEvenement.
     *
     * @param logEvenement the logEvenement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logEvenement,
     * or with status 400 (Bad Request) if the logEvenement is not valid,
     * or with status 500 (Internal Server Error) if the logEvenement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/log-evenements")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<LogEvenement> updateLogEvenement(@RequestBody LogEvenement logEvenement) throws URISyntaxException {
        log.debug("REST request to update LogEvenement : {}", logEvenement);
        if (logEvenement.getId() == null) {
            return createLogEvenement(logEvenement);
        }
        LogEvenement result = logEvenementService.save(logEvenement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, logEvenement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /log-evenements : get all the logEvenements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of logEvenements in body
     */
    @GetMapping("/log-evenements")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<List<LogEvenement>> getAllLogEvenements(Pageable pageable) {
        log.debug("REST request to get a page of LogEvenements");
        Page<LogEvenement> page = logEvenementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/log-evenements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /log-evenements/:id : get the "id" logEvenement.
     *
     * @param id the id of the logEvenement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the logEvenement, or with status 404 (Not Found)
     */
    @GetMapping("/log-evenements/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<LogEvenement> getLogEvenement(@PathVariable Long id) {
        log.debug("REST request to get LogEvenement : {}", id);
        LogEvenement logEvenement = logEvenementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(logEvenement));
    }

    /**
     * DELETE  /log-evenements/:id : delete the "id" logEvenement.
     *
     * @param id the id of the logEvenement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/log-evenements/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deleteLogEvenement(@PathVariable Long id) {
        log.debug("REST request to delete LogEvenement : {}", id);
        logEvenementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
