package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.TypeCarte;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.TypeCarteService;
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
 * REST controller for managing TypeCarte.
 */
@RestController
@RequestMapping("/api")
public class TypeCarteResource {

    private final Logger log = LoggerFactory.getLogger(TypeCarteResource.class);

    private static final String ENTITY_NAME = "typeCarte";

    private final TypeCarteService typeCarteService;

    public TypeCarteResource(TypeCarteService typeCarteService) {
        this.typeCarteService = typeCarteService;
    }

    /**
     * POST  /type-cartes : Create a new typeCarte.
     *
     * @param typeCarte the typeCarte to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeCarte, or with status 400 (Bad Request) if the typeCarte has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-cartes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<TypeCarte> createTypeCarte(@RequestBody TypeCarte typeCarte) throws URISyntaxException {
        log.debug("REST request to save TypeCarte : {}", typeCarte);
        if (typeCarte.getId() != null) {
            throw new BadRequestAlertException("A new typeCarte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeCarte result = typeCarteService.save(typeCarte);
        return ResponseEntity.created(new URI("/api/type-cartes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-cartes : Updates an existing typeCarte.
     *
     * @param typeCarte the typeCarte to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeCarte,
     * or with status 400 (Bad Request) if the typeCarte is not valid,
     * or with status 500 (Internal Server Error) if the typeCarte couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-cartes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<TypeCarte> updateTypeCarte(@RequestBody TypeCarte typeCarte) throws URISyntaxException {
        log.debug("REST request to update TypeCarte : {}", typeCarte);
        if (typeCarte.getId() == null) {
            return createTypeCarte(typeCarte);
        }
        TypeCarte result = typeCarteService.save(typeCarte);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeCarte.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-cartes : get all the typeCartes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeCartes in body
     */
    @GetMapping("/type-cartes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<List<TypeCarte>> getAllTypeCartes(Pageable pageable) {
        log.debug("REST request to get a page of TypeCartes");
        Page<TypeCarte> page = typeCarteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-cartes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-cartes/:id : get the "id" typeCarte.
     *
     * @param id the id of the typeCarte to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeCarte, or with status 404 (Not Found)
     */
    @GetMapping("/type-cartes/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<TypeCarte> getTypeCarte(@PathVariable Long id) {
        log.debug("REST request to get TypeCarte : {}", id);
        TypeCarte typeCarte = typeCarteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeCarte));
    }

    /**
     * DELETE  /type-cartes/:id : delete the "id" typeCarte.
     *
     * @param id the id of the typeCarte to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-cartes/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deleteTypeCarte(@PathVariable Long id) {
        log.debug("REST request to delete TypeCarte : {}", id);
        TypeCarte typeCarte = typeCarteService.findOne(id);
        typeCarteService.delete(typeCarte);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
