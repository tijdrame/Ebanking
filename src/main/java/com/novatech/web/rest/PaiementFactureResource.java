package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.PaiementFacture;
import com.novatech.service.PaiementFactureService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PaiementFacture.
 */
@RestController
@RequestMapping("/api")
public class PaiementFactureResource {

    private final Logger log = LoggerFactory.getLogger(PaiementFactureResource.class);

    private static final String ENTITY_NAME = "paiementFacture";

    private final PaiementFactureService paiementFactureService;

    public PaiementFactureResource(PaiementFactureService paiementFactureService) {
        this.paiementFactureService = paiementFactureService;
    }

    /**
     * POST  /paiement-factures : Create a new paiementFacture.
     *
     * @param paiementFacture the paiementFacture to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paiementFacture, or with status 400 (Bad Request) if the paiementFacture has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/paiement-factures")
    @Timed
    public ResponseEntity<PaiementFacture> createPaiementFacture(@Valid @RequestBody PaiementFacture paiementFacture) throws URISyntaxException {
        log.debug("REST request to save PaiementFacture : {}", paiementFacture);
        if (paiementFacture.getId() != null) {
            throw new BadRequestAlertException("A new paiementFacture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaiementFacture result = paiementFactureService.save(paiementFacture);
        return ResponseEntity.created(new URI("/api/paiement-factures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /paiement-factures : Updates an existing paiementFacture.
     *
     * @param paiementFacture the paiementFacture to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paiementFacture,
     * or with status 400 (Bad Request) if the paiementFacture is not valid,
     * or with status 500 (Internal Server Error) if the paiementFacture couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/paiement-factures")
    @Timed
    public ResponseEntity<PaiementFacture> updatePaiementFacture(@Valid @RequestBody PaiementFacture paiementFacture) throws URISyntaxException {
        log.debug("REST request to update PaiementFacture : {}", paiementFacture);
        if (paiementFacture.getId() == null) {
            return createPaiementFacture(paiementFacture);
        }
        PaiementFacture result = paiementFactureService.save(paiementFacture);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paiementFacture.getId().toString()))
            .body(result);
    }

    /**
     * GET  /paiement-factures : get all the paiementFactures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of paiementFactures in body
     */
    @GetMapping("/paiement-factures")
    @Timed
    public ResponseEntity<List<PaiementFacture>> getAllPaiementFactures(Pageable pageable) {
        log.debug("REST request to get a page of PaiementFactures");
        Page<PaiementFacture> page = paiementFactureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/paiement-factures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /paiement-factures/:id : get the "id" paiementFacture.
     *
     * @param id the id of the paiementFacture to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paiementFacture, or with status 404 (Not Found)
     */
    @GetMapping("/paiement-factures/{id}")
    @Timed
    public ResponseEntity<PaiementFacture> getPaiementFacture(@PathVariable Long id) {
        log.debug("REST request to get PaiementFacture : {}", id);
        PaiementFacture paiementFacture = paiementFactureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paiementFacture));
    }

    /**
     * DELETE  /paiement-factures/:id : delete the "id" paiementFacture.
     *
     * @param id the id of the paiementFacture to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/paiement-factures/{id}")
    @Timed
    public ResponseEntity<Void> deletePaiementFacture(@PathVariable Long id) {
        log.debug("REST request to delete PaiementFacture : {}", id);
        paiementFactureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
