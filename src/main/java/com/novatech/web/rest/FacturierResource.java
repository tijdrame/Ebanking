package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.Facturier;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.FacturierService;
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
 * REST controller for managing Facturier.
 */
@RestController
@RequestMapping("/api")
public class FacturierResource {

    private final Logger log = LoggerFactory.getLogger(FacturierResource.class);

    private static final String ENTITY_NAME = "facturier";

    private final FacturierService facturierService;

    public FacturierResource(FacturierService facturierService) {
        this.facturierService = facturierService;
    }

    /**
     * POST  /facturiers : Create a new facturier.
     *
     * @param facturier the facturier to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facturier, or with status 400 (Bad Request) if the facturier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/facturiers")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Facturier> createFacturier(@Valid @RequestBody Facturier facturier) throws URISyntaxException {
        log.debug("REST request to save Facturier : {}", facturier);
        if (facturier.getId() != null) {
            throw new BadRequestAlertException("A new facturier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Facturier result = facturierService.save(facturier);
        return ResponseEntity.created(new URI("/api/facturiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /facturiers : Updates an existing facturier.
     *
     * @param facturier the facturier to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facturier,
     * or with status 400 (Bad Request) if the facturier is not valid,
     * or with status 500 (Internal Server Error) if the facturier couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/facturiers")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Facturier> updateFacturier(@Valid @RequestBody Facturier facturier) throws URISyntaxException {
        log.debug("REST request to update Facturier : {}", facturier);
        if (facturier.getId() == null) {
            return createFacturier(facturier);
        }
        Facturier result = facturierService.save(facturier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facturier.getId().toString()))
            .body(result);
    }

    /**
     * GET  /facturiers : get all the facturiers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facturiers in body
     */
    @GetMapping("/facturiers")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<List<Facturier>> getAllFacturiers(Pageable pageable) {
        log.debug("REST request to get a page of Facturiers");
        Page<Facturier> page = facturierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/facturiers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /facturiers/:id : get the "id" facturier.
     *
     * @param id the id of the facturier to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facturier, or with status 404 (Not Found)
     */
    @GetMapping("/facturiers/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Facturier> getFacturier(@PathVariable Long id) {
        log.debug("REST request to get Facturier : {}", id);
        Facturier facturier = facturierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(facturier));
    }

    /**
     * DELETE  /facturiers/:id : delete the "id" facturier.
     *
     * @param id the id of the facturier to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/facturiers/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deleteFacturier(@PathVariable Long id) {
        log.debug("REST request to delete Facturier : {}", id);
        Facturier facturier = facturierService.findOne(id);
        facturierService.delete(facturier);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
