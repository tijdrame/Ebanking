package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.NbreFeuillesChequier;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.NbreFeuillesChequierService;
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
 * REST controller for managing NbreFeuillesChequier.
 */
@RestController
@RequestMapping("/api")
public class NbreFeuillesChequierResource {

    private final Logger log = LoggerFactory.getLogger(NbreFeuillesChequierResource.class);

    private static final String ENTITY_NAME = "nbreFeuillesChequier";

    private final NbreFeuillesChequierService nbreFeuillesChequierService;

    public NbreFeuillesChequierResource(NbreFeuillesChequierService nbreFeuillesChequierService) {
        this.nbreFeuillesChequierService = nbreFeuillesChequierService;
    }

    /**
     * POST  /nbre-feuilles-chequiers : Create a new nbreFeuillesChequier.
     *
     * @param nbreFeuillesChequier the nbreFeuillesChequier to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nbreFeuillesChequier, or with status 400 (Bad Request) if the nbreFeuillesChequier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nbre-feuilles-chequiers")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<NbreFeuillesChequier> createNbreFeuillesChequier(@Valid @RequestBody NbreFeuillesChequier nbreFeuillesChequier) throws URISyntaxException {
        log.debug("REST request to save NbreFeuillesChequier : {}", nbreFeuillesChequier);
        if (nbreFeuillesChequier.getId() != null) {
            throw new BadRequestAlertException("A new nbreFeuillesChequier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NbreFeuillesChequier result = nbreFeuillesChequierService.save(nbreFeuillesChequier);
        return ResponseEntity.created(new URI("/api/nbre-feuilles-chequiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nbre-feuilles-chequiers : Updates an existing nbreFeuillesChequier.
     *
     * @param nbreFeuillesChequier the nbreFeuillesChequier to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nbreFeuillesChequier,
     * or with status 400 (Bad Request) if the nbreFeuillesChequier is not valid,
     * or with status 500 (Internal Server Error) if the nbreFeuillesChequier couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nbre-feuilles-chequiers")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<NbreFeuillesChequier> updateNbreFeuillesChequier(@Valid @RequestBody NbreFeuillesChequier nbreFeuillesChequier) throws URISyntaxException {
        log.debug("REST request to update NbreFeuillesChequier : {}", nbreFeuillesChequier);
        if (nbreFeuillesChequier.getId() == null) {
            return createNbreFeuillesChequier(nbreFeuillesChequier);
        }
        NbreFeuillesChequier result = nbreFeuillesChequierService.save(nbreFeuillesChequier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nbreFeuillesChequier.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nbre-feuilles-chequiers : get all the nbreFeuillesChequiers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nbreFeuillesChequiers in body
     */
    @GetMapping("/nbre-feuilles-chequiers")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<List<NbreFeuillesChequier>> getAllNbreFeuillesChequiers(Pageable pageable) {
        log.debug("REST request to get a page of NbreFeuillesChequiers");
        Page<NbreFeuillesChequier> page = nbreFeuillesChequierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nbre-feuilles-chequiers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /nbre-feuilles-chequiers/:id : get the "id" nbreFeuillesChequier.
     *
     * @param id the id of the nbreFeuillesChequier to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nbreFeuillesChequier, or with status 404 (Not Found)
     */
    @GetMapping("/nbre-feuilles-chequiers/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<NbreFeuillesChequier> getNbreFeuillesChequier(@PathVariable Long id) {
        log.debug("REST request to get NbreFeuillesChequier : {}", id);
        NbreFeuillesChequier nbreFeuillesChequier = nbreFeuillesChequierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nbreFeuillesChequier));
    }

    /**
     * DELETE  /nbre-feuilles-chequiers/:id : delete the "id" nbreFeuillesChequier.
     *
     * @param id the id of the nbreFeuillesChequier to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nbre-feuilles-chequiers/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deleteNbreFeuillesChequier(@PathVariable Long id) {
        log.debug("REST request to delete NbreFeuillesChequier : {}", id);
        NbreFeuillesChequier nbreFeuillesChequier = nbreFeuillesChequierService.findOne(id);
        nbreFeuillesChequierService.delete(nbreFeuillesChequier);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
