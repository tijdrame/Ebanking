package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.Parametres;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.ParametresService;
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
 * REST controller for managing Parametres.
 */
@RestController
@RequestMapping("/api")
public class ParametresResource {

    private final Logger log = LoggerFactory.getLogger(ParametresResource.class);

    private static final String ENTITY_NAME = "parametres";

    private final ParametresService parametresService;

    public ParametresResource(ParametresService parametresService) {
        this.parametresService = parametresService;
    }

    /**
     * POST  /parametres : Create a new parametres.
     *
     * @param parametres the parametres to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parametres, or with status 400 (Bad Request) if the parametres has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parametres")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Parametres> createParametres(@Valid @RequestBody Parametres parametres) throws URISyntaxException {
        log.debug("REST request to save Parametres : {}", parametres);
        if (parametres.getId() != null) {
            throw new BadRequestAlertException("A new parametres cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Parametres result = parametresService.save(parametres);
        return ResponseEntity.created(new URI("/api/parametres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parametres : Updates an existing parametres.
     *
     * @param parametres the parametres to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parametres,
     * or with status 400 (Bad Request) if the parametres is not valid,
     * or with status 500 (Internal Server Error) if the parametres couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parametres")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Parametres> updateParametres(@Valid @RequestBody Parametres parametres) throws URISyntaxException {
        log.debug("REST request to update Parametres : {}", parametres);
        if (parametres.getId() == null) {
            return createParametres(parametres);
        }
        Parametres result = parametresService.save(parametres);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parametres.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parametres : get all the parametres.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of parametres in body
     */
    @GetMapping("/parametres")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<List<Parametres>> getAllParametres(Pageable pageable) {
        log.debug("REST request to get a page of Parametres");
        Page<Parametres> page = parametresService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parametres");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /parametres/:id : get the "id" parametres.
     *
     * @param id the id of the parametres to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parametres, or with status 404 (Not Found)
     */
    @GetMapping("/parametres/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Parametres> getParametres(@PathVariable Long id) {
        log.debug("REST request to get Parametres : {}", id);
        Parametres parametres = parametresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(parametres));
    }

    /**
     * DELETE  /parametres/:id : delete the "id" parametres.
     *
     * @param id the id of the parametres to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parametres/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deleteParametres(@PathVariable Long id) {
        log.debug("REST request to delete Parametres : {}", id);
        Parametres parametres = parametresService.findOne(id);
        parametresService.delete(parametres);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
