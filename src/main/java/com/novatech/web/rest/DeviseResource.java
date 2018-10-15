package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.Devise;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.DeviseService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Devise.
 */
@RestController
@RequestMapping("/api")
public class DeviseResource {

    private final Logger log = LoggerFactory.getLogger(DeviseResource.class);

    private static final String ENTITY_NAME = "devise";

    private final DeviseService deviseService;

    public DeviseResource(DeviseService deviseService) {
        this.deviseService = deviseService;
    }

    /**
     * POST  /devises : Create a new devise.
     *
     * @param devise the devise to create
     * @return the ResponseEntity with status 201 (Created) and with body the new devise, or with status 400 (Bad Request) if the devise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/devises")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Devise> createDevise(@Valid @RequestBody Devise devise) throws URISyntaxException {
        log.debug("REST request to save Devise : {}", devise);
        if (devise.getId() != null) {
            throw new BadRequestAlertException("A new devise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Devise result = deviseService.save(devise);
        return ResponseEntity.created(new URI("/api/devises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /devises : Updates an existing devise.
     *
     * @param devise the devise to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated devise,
     * or with status 400 (Bad Request) if the devise is not valid,
     * or with status 500 (Internal Server Error) if the devise couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/devises")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Devise> updateDevise(@Valid @RequestBody Devise devise) throws URISyntaxException {
        log.debug("REST request to update Devise : {}", devise);
        if (devise.getId() == null) {
            return createDevise(devise);
        }
        Devise result = deviseService.save(devise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, devise.getId().toString()))
            .body(result);
    }

    /**
     * GET  /devises : get all the devises.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of devises in body
     */
    @GetMapping("/devises")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<List<Devise>> getAllDevises(Pageable pageable) {
        log.debug("REST request to get a page of Devises");
        Page<Devise> page = deviseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/devises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /devises/:id : get the "id" devise.
     *
     * @param id the id of the devise to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the devise, or with status 404 (Not Found)
     */
    @GetMapping("/devises/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Devise> getDevise(@PathVariable Long id) {
        log.debug("REST request to get Devise : {}", id);
        Devise devise = deviseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(devise));
    }

    /**
     * DELETE  /devises/:id : delete the "id" devise.
     *
     * @param id the id of the devise to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/devises/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deleteDevise(@PathVariable Long id) {
        log.debug("REST request to delete Devise : {}", id);
        Devise devise = deviseService.findOne(id);
        deviseService.delete(devise);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
