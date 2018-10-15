package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.TypeAbonne;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.TypeAbonneService;
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
 * REST controller for managing TypeAbonne.
 */
@RestController
@RequestMapping("/api")
public class TypeAbonneResource {

    private final Logger log = LoggerFactory.getLogger(TypeAbonneResource.class);

    private static final String ENTITY_NAME = "typeAbonne";

    private final TypeAbonneService typeAbonneService;

    public TypeAbonneResource(TypeAbonneService typeAbonneService) {
        this.typeAbonneService = typeAbonneService;
    }

    /**
     * POST  /type-abonnes : Create a new typeAbonne.
     *
     * @param typeAbonne the typeAbonne to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeAbonne, or with status 400 (Bad Request) if the typeAbonne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-abonnes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<TypeAbonne> createTypeAbonne(@RequestBody TypeAbonne typeAbonne) throws URISyntaxException {
        log.debug("REST request to save TypeAbonne : {}", typeAbonne);
        if (typeAbonne.getId() != null) {
            throw new BadRequestAlertException("A new typeAbonne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeAbonne result = typeAbonneService.save(typeAbonne);
        return ResponseEntity.created(new URI("/api/type-abonnes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-abonnes : Updates an existing typeAbonne.
     *
     * @param typeAbonne the typeAbonne to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeAbonne,
     * or with status 400 (Bad Request) if the typeAbonne is not valid,
     * or with status 500 (Internal Server Error) if the typeAbonne couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-abonnes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<TypeAbonne> updateTypeAbonne(@RequestBody TypeAbonne typeAbonne) throws URISyntaxException {
        log.debug("REST request to update TypeAbonne : {}", typeAbonne);
        if (typeAbonne.getId() == null) {
            return createTypeAbonne(typeAbonne);
        }
        TypeAbonne result = typeAbonneService.save(typeAbonne);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeAbonne.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-abonnes : get all the typeAbonnes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeAbonnes in body
     */
    @GetMapping("/type-abonnes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<List<TypeAbonne>> getAllTypeAbonnes(Pageable pageable) {
        log.debug("REST request to get a page of TypeAbonnes");
        Page<TypeAbonne> page = typeAbonneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-abonnes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-abonnes/:id : get the "id" typeAbonne.
     *
     * @param id the id of the typeAbonne to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeAbonne, or with status 404 (Not Found)
     */
    @GetMapping("/type-abonnes/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<TypeAbonne> getTypeAbonne(@PathVariable Long id) {
        log.debug("REST request to get TypeAbonne : {}", id);
        TypeAbonne typeAbonne = typeAbonneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeAbonne));
    }

    /**
     * DELETE  /type-abonnes/:id : delete the "id" typeAbonne.
     *
     * @param id the id of the typeAbonne to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-abonnes/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deleteTypeAbonne(@PathVariable Long id) {
        log.debug("REST request to delete TypeAbonne : {}", id);
        TypeAbonne typeAbonne = typeAbonneService.findOne(id);
        typeAbonneService.delete(typeAbonne);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
