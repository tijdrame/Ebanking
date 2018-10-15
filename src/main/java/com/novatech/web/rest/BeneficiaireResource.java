package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.Beneficiaire;
import com.novatech.service.BeneficiaireService;
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
 * REST controller for managing Beneficiaire.
 */
@RestController
@RequestMapping("/api")
public class BeneficiaireResource {

    private final Logger log = LoggerFactory.getLogger(BeneficiaireResource.class);

    private static final String ENTITY_NAME = "beneficiaire";

    private final BeneficiaireService beneficiaireService;

    public BeneficiaireResource(BeneficiaireService beneficiaireService) {
        this.beneficiaireService = beneficiaireService;
    }

    /**
     * POST  /beneficiaires : Create a new beneficiaire.
     *
     * @param beneficiaire the beneficiaire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new beneficiaire, or with status 400 (Bad Request) if the beneficiaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/beneficiaires")
    @Timed
    public ResponseEntity<Beneficiaire> createBeneficiaire(@Valid @RequestBody Beneficiaire beneficiaire) throws URISyntaxException {
        log.debug("REST request to save Beneficiaire : {}", beneficiaire);
        if (beneficiaire.getId() != null) {
            throw new BadRequestAlertException("A new beneficiaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Beneficiaire result = beneficiaireService.save(beneficiaire);
        return ResponseEntity.created(new URI("/api/beneficiaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /beneficiaires : Updates an existing beneficiaire.
     *
     * @param beneficiaire the beneficiaire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated beneficiaire,
     * or with status 400 (Bad Request) if the beneficiaire is not valid,
     * or with status 500 (Internal Server Error) if the beneficiaire couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/beneficiaires")
    @Timed
    public ResponseEntity<Beneficiaire> updateBeneficiaire(@Valid @RequestBody Beneficiaire beneficiaire) throws URISyntaxException {
        log.debug("REST request to update Beneficiaire : {}", beneficiaire);
        if (beneficiaire.getId() == null) {
            return createBeneficiaire(beneficiaire);
        }
        Beneficiaire result = beneficiaireService.save(beneficiaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, beneficiaire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /beneficiaires : get all the beneficiaires.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of beneficiaires in body
     */
    @GetMapping("/beneficiaires")
    @Timed
    public ResponseEntity<List<Beneficiaire>> getAllBeneficiaires(Pageable pageable) {
        log.debug("REST request to get a page of Beneficiaires");
        Page<Beneficiaire> page = beneficiaireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/beneficiaires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /beneficiaires/:id : get the "id" beneficiaire.
     *
     * @param id the id of the beneficiaire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the beneficiaire, or with status 404 (Not Found)
     */
    @GetMapping("/beneficiaires/{id}")
    @Timed
    public ResponseEntity<Beneficiaire> getBeneficiaire(@PathVariable Long id) {
        log.debug("REST request to get Beneficiaire : {}", id);
        Beneficiaire beneficiaire = beneficiaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(beneficiaire));
    }

    /**
     * DELETE  /beneficiaires/:id : delete the "id" beneficiaire.
     *
     * @param id the id of the beneficiaire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/beneficiaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteBeneficiaire(@PathVariable Long id) {
        log.debug("REST request to delete Beneficiaire : {}", id);
        beneficiaireService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
