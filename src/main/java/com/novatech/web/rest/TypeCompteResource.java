package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.TypeCompte;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.TypeCompteService;
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
 * REST controller for managing TypeCompte.
 */
@RestController
@RequestMapping("/api")
public class TypeCompteResource {

    private final Logger log = LoggerFactory.getLogger(TypeCompteResource.class);

    private static final String ENTITY_NAME = "typeCompte";

    private final TypeCompteService typeCompteService;

    public TypeCompteResource(TypeCompteService typeCompteService) {
        this.typeCompteService = typeCompteService;
    }

    /**
     * POST  /type-comptes : Create a new typeCompte.
     *
     * @param typeCompte the typeCompte to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeCompte, or with status 400 (Bad Request) if the typeCompte has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-comptes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<TypeCompte> createTypeCompte(@RequestBody TypeCompte typeCompte) throws URISyntaxException {
        log.debug("REST request to save TypeCompte : {}", typeCompte);
        if (typeCompte.getId() != null) {
            throw new BadRequestAlertException("A new typeCompte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeCompte result = typeCompteService.save(typeCompte);
        return ResponseEntity.created(new URI("/api/type-comptes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-comptes : Updates an existing typeCompte.
     *
     * @param typeCompte the typeCompte to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeCompte,
     * or with status 400 (Bad Request) if the typeCompte is not valid,
     * or with status 500 (Internal Server Error) if the typeCompte couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-comptes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<TypeCompte> updateTypeCompte(@RequestBody TypeCompte typeCompte) throws URISyntaxException {
        log.debug("REST request to update TypeCompte : {}", typeCompte);
        if (typeCompte.getId() == null) {
            return createTypeCompte(typeCompte);
        }
        TypeCompte result = typeCompteService.save(typeCompte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeCompte.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-comptes : get all the typeComptes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeComptes in body
     */
    @GetMapping("/type-comptes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<List<TypeCompte>> getAllTypeComptes(Pageable pageable) {
        log.debug("REST request to get a page of TypeComptes");
        Page<TypeCompte> page = typeCompteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-comptes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-comptes/:id : get the "id" typeCompte.
     *
     * @param id the id of the typeCompte to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeCompte, or with status 404 (Not Found)
     */
    @GetMapping("/type-comptes/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<TypeCompte> getTypeCompte(@PathVariable Long id) {
        log.debug("REST request to get TypeCompte : {}", id);
        TypeCompte typeCompte = typeCompteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeCompte));
    }

    /**
     * DELETE  /type-comptes/:id : delete the "id" typeCompte.
     *
     * @param id the id of the typeCompte to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-comptes/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deleteTypeCompte(@PathVariable Long id) {
        log.debug("REST request to delete TypeCompte : {}", id);
        TypeCompte typeCompte = typeCompteService.findOne(id);
        typeCompteService.delete(typeCompte);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
