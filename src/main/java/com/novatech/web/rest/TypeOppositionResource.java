package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.TypeOpposition;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.TypeOppositionService;
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
 * REST controller for managing TypeOpposition.
 */
@RestController
@RequestMapping("/api")
public class TypeOppositionResource {

    private final Logger log = LoggerFactory.getLogger(TypeOppositionResource.class);

    private static final String ENTITY_NAME = "typeOpposition";

    private final TypeOppositionService typeOppositionService;

    public TypeOppositionResource(TypeOppositionService typeOppositionService) {
        this.typeOppositionService = typeOppositionService;
    }

    /**
     * POST  /type-oppositions : Create a new typeOpposition.
     *
     * @param typeOpposition the typeOpposition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeOpposition, or with status 400 (Bad Request) if the typeOpposition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-oppositions")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<TypeOpposition> createTypeOpposition(@RequestBody TypeOpposition typeOpposition) throws URISyntaxException {
        log.debug("REST request to save TypeOpposition : {}", typeOpposition);
        if (typeOpposition.getId() != null) {
            throw new BadRequestAlertException("A new typeOpposition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeOpposition result = typeOppositionService.save(typeOpposition);
        return ResponseEntity.created(new URI("/api/type-oppositions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-oppositions : Updates an existing typeOpposition.
     *
     * @param typeOpposition the typeOpposition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeOpposition,
     * or with status 400 (Bad Request) if the typeOpposition is not valid,
     * or with status 500 (Internal Server Error) if the typeOpposition couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-oppositions")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<TypeOpposition> updateTypeOpposition(@RequestBody TypeOpposition typeOpposition) throws URISyntaxException {
        log.debug("REST request to update TypeOpposition : {}", typeOpposition);
        if (typeOpposition.getId() == null) {
            return createTypeOpposition(typeOpposition);
        }
        TypeOpposition result = typeOppositionService.save(typeOpposition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeOpposition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-oppositions : get all the typeOppositions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeOppositions in body
     */
    @GetMapping("/type-oppositions")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<List<TypeOpposition>> getAllTypeOppositions(Pageable pageable) {
        log.debug("REST request to get a page of TypeOppositions");
        Page<TypeOpposition> page = typeOppositionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-oppositions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-oppositions/:id : get the "id" typeOpposition.
     *
     * @param id the id of the typeOpposition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeOpposition, or with status 404 (Not Found)
     */
    @GetMapping("/type-oppositions/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<TypeOpposition> getTypeOpposition(@PathVariable Long id) {
        log.debug("REST request to get TypeOpposition : {}", id);
        TypeOpposition typeOpposition = typeOppositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeOpposition));
    }

    /**
     * DELETE  /type-oppositions/:id : delete the "id" typeOpposition.
     *
     * @param id the id of the typeOpposition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-oppositions/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deleteTypeOpposition(@PathVariable Long id) {
        log.debug("REST request to delete TypeOpposition : {}", id);
        TypeOpposition typeOpposition = typeOppositionService.findOne(id);
        typeOppositionService.delete(typeOpposition);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
