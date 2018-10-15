package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.Compte;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.CompteService;
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
 * REST controller for managing Compte.
 */
@RestController
@RequestMapping("/api")
public class CompteResource {

    private final Logger log = LoggerFactory.getLogger(CompteResource.class);

    private static final String ENTITY_NAME = "compte";

    private final CompteService compteService;

    public CompteResource(CompteService compteService) {
        this.compteService = compteService;
    }

    /**
     * POST  /comptes : Create a new compte.
     *
     * @param compte the compte to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compte, or with status 400 (Bad Request) if the compte has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comptes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Compte> createCompte(@Valid @RequestBody Compte compte) throws URISyntaxException {
        log.debug("REST request to save Compte : {}", compte);
        if (compte.getId() != null) {
            throw new BadRequestAlertException("A new compte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Compte result = compteService.save(compte);
        return ResponseEntity.created(new URI("/api/comptes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comptes : Updates an existing compte.
     *
     * @param compte the compte to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compte,
     * or with status 400 (Bad Request) if the compte is not valid,
     * or with status 500 (Internal Server Error) if the compte couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comptes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Compte> updateCompte(@Valid @RequestBody Compte compte) throws URISyntaxException {
        log.debug("REST request to update Compte : {}", compte);
        if (compte.getId() == null) {
            return createCompte(compte);
        }
        Compte result = compteService.update(compte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compte.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comptes : get all the comptes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of comptes in body
     */
    @GetMapping("/comptes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE, AuthoritiesConstants.USER})
    public ResponseEntity<List<Compte>> getAllComptes(Pageable pageable) {
        log.debug("REST request to get a page of Comptes");
        Page<Compte> page = compteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comptes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comptes/:id : get the "id" compte.
     *
     * @param id the id of the compte to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compte, or with status 404 (Not Found)
     */
    @GetMapping("/comptes/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE, AuthoritiesConstants.USER})
    public ResponseEntity<Compte> getCompte(@PathVariable Long id) {
        log.debug("REST request to get Compte : {}", id);
        Compte compte = compteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(compte));
    }

    /**
     * DELETE  /comptes/:id : delete the "id" compte.
     *
     * @param id the id of the compte to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comptes/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        log.debug("REST request to delete Compte : {}", id);
        Compte compte = compteService.findOne(id);
        compteService.delete(compte);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
