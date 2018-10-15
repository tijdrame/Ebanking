package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.TypeChequier;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.TypeChequierService;
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
 * REST controller for managing TypeChequier.
 */
@RestController
@RequestMapping("/api")
public class TypeChequierResource {

    private final Logger log = LoggerFactory.getLogger(TypeChequierResource.class);

    private static final String ENTITY_NAME = "typeChequier";

    private final TypeChequierService typeChequierService;

    public TypeChequierResource(TypeChequierService typeChequierService) {
        this.typeChequierService = typeChequierService;
    }

    /**
     * POST  /type-chequiers : Create a new typeChequier.
     *
     * @param typeChequier the typeChequier to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeChequier, or with status 400 (Bad Request) if the typeChequier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-chequiers")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<TypeChequier> createTypeChequier(@RequestBody TypeChequier typeChequier) throws URISyntaxException {
        log.debug("REST request to save TypeChequier : {}", typeChequier);
        if (typeChequier.getId() != null) {
            throw new BadRequestAlertException("A new typeChequier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeChequier result = typeChequierService.save(typeChequier);
        return ResponseEntity.created(new URI("/api/type-chequiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-chequiers : Updates an existing typeChequier.
     *
     * @param typeChequier the typeChequier to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeChequier,
     * or with status 400 (Bad Request) if the typeChequier is not valid,
     * or with status 500 (Internal Server Error) if the typeChequier couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-chequiers")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<TypeChequier> updateTypeChequier(@RequestBody TypeChequier typeChequier) throws URISyntaxException {
        log.debug("REST request to update TypeChequier : {}", typeChequier);
        if (typeChequier.getId() == null) {
            return createTypeChequier(typeChequier);
        }
        TypeChequier result = typeChequierService.save(typeChequier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeChequier.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-chequiers : get all the typeChequiers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeChequiers in body
     */
    @GetMapping("/type-chequiers")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<List<TypeChequier>> getAllTypeChequiers(Pageable pageable) {
        log.debug("REST request to get a page of TypeChequiers");
        Page<TypeChequier> page = typeChequierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-chequiers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-chequiers/:id : get the "id" typeChequier.
     *
     * @param id the id of the typeChequier to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeChequier, or with status 404 (Not Found)
     */
    @GetMapping("/type-chequiers/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<TypeChequier> getTypeChequier(@PathVariable Long id) {
        log.debug("REST request to get TypeChequier : {}", id);
        TypeChequier typeChequier = typeChequierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeChequier));
    }

    /**
     * DELETE  /type-chequiers/:id : delete the "id" typeChequier.
     *
     * @param id the id of the typeChequier to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-chequiers/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deleteTypeChequier(@PathVariable Long id) {
        log.debug("REST request to delete TypeChequier : {}", id);
        TypeChequier typeChequier = typeChequierService.findOne(id);
        typeChequierService.delete(typeChequier);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
