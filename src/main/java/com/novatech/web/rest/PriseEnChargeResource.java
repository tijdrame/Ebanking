package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.PriseEnCharge;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.PriseEnChargeService;
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
 * REST controller for managing PriseEnCharge.
 */
@RestController
@RequestMapping("/api")
public class PriseEnChargeResource {

    private final Logger log = LoggerFactory.getLogger(PriseEnChargeResource.class);

    private static final String ENTITY_NAME = "priseEnCharge";

    private final PriseEnChargeService priseEnChargeService;

    public PriseEnChargeResource(PriseEnChargeService priseEnChargeService) {
        this.priseEnChargeService = priseEnChargeService;
    }

    /**
     * POST  /prise-en-charges : Create a new priseEnCharge.
     *
     * @param priseEnCharge the priseEnCharge to create
     * @return the ResponseEntity with status 201 (Created) and with body the new priseEnCharge, or with status 400 (Bad Request) if the priseEnCharge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prise-en-charges")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<PriseEnCharge> createPriseEnCharge(@Valid @RequestBody PriseEnCharge priseEnCharge) throws URISyntaxException {
        log.debug("REST request to save PriseEnCharge : {}", priseEnCharge);
        if (priseEnCharge.getId() != null) {
            throw new BadRequestAlertException("A new priseEnCharge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PriseEnCharge result = priseEnChargeService.save(priseEnCharge);
        return ResponseEntity.created(new URI("/api/prise-en-charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prise-en-charges : Updates an existing priseEnCharge.
     *
     * @param priseEnCharge the priseEnCharge to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated priseEnCharge,
     * or with status 400 (Bad Request) if the priseEnCharge is not valid,
     * or with status 500 (Internal Server Error) if the priseEnCharge couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prise-en-charges")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<PriseEnCharge> updatePriseEnCharge(@Valid @RequestBody PriseEnCharge priseEnCharge) throws URISyntaxException {
        log.debug("REST request to update PriseEnCharge : {}", priseEnCharge);
        if (priseEnCharge.getId() == null) {
            return createPriseEnCharge(priseEnCharge);
        }
        PriseEnCharge result = priseEnChargeService.save(priseEnCharge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, priseEnCharge.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prise-en-charges : get all the priseEnCharges.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of priseEnCharges in body
     */
    @GetMapping("/prise-en-charges")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<List<PriseEnCharge>> getAllPriseEnCharges(Pageable pageable) {
        log.debug("REST request to get a page of PriseEnCharges");
        Page<PriseEnCharge> page = priseEnChargeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prise-en-charges");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prise-en-charges/:id : get the "id" priseEnCharge.
     *
     * @param id the id of the priseEnCharge to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the priseEnCharge, or with status 404 (Not Found)
     */
    @GetMapping("/prise-en-charges/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<PriseEnCharge> getPriseEnCharge(@PathVariable Long id) {
        log.debug("REST request to get PriseEnCharge : {}", id);
        PriseEnCharge priseEnCharge = priseEnChargeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(priseEnCharge));
    }

    /**
     * DELETE  /prise-en-charges/:id : delete the "id" priseEnCharge.
     *
     * @param id the id of the priseEnCharge to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prise-en-charges/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deletePriseEnCharge(@PathVariable Long id) {
        log.debug("REST request to delete PriseEnCharge : {}", id);
        PriseEnCharge priseEnCharge = priseEnChargeService.findOne(id);
        priseEnChargeService.delete(priseEnCharge);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
