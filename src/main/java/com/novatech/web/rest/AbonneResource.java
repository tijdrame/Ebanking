package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.Abonne;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.AbonneService;
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
 * REST controller for managing Abonne.
 */
@RestController
@RequestMapping("/api")
public class AbonneResource {

    private final Logger log = LoggerFactory.getLogger(AbonneResource.class);

    private static final String ENTITY_NAME = "abonne";

    private final AbonneService abonneService;

    public AbonneResource(AbonneService abonneService) {
        this.abonneService = abonneService;
    }

    /**
     * POST  /abonnes : Create a new abonne.
     *
     * @param abonne the abonne to create
     * @return the ResponseEntity with status 201 (Created) and with body the new abonne, or with status 400 (Bad Request) if the abonne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/abonnes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Abonne> createAbonne(@RequestBody Abonne abonne) throws URISyntaxException {
        log.debug("REST request to save Abonne : {}", abonne);
        if (abonne.getId() != null) {
            throw new BadRequestAlertException("A new abonne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Abonne result = abonneService.save(abonne);
        return ResponseEntity.created(new URI("/api/abonnes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /abonnes : Updates an existing abonne.
     *
     * @param abonne the abonne to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated abonne,
     * or with status 400 (Bad Request) if the abonne is not valid,
     * or with status 500 (Internal Server Error) if the abonne couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/abonnes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Abonne> updateAbonne(@RequestBody Abonne abonne) throws URISyntaxException {
        log.debug("REST request to update Abonne : {}", abonne);
        if (abonne.getId() == null) {
            return createAbonne(abonne);
        }
        Abonne result = abonneService.update(abonne);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, abonne.getId().toString()))
            .body(result);
    }

    /**
     * GET  /abonnes : get all the abonnes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of abonnes in body
     */
    @GetMapping("/abonnes")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<List<Abonne>> getAllAbonnes(Pageable pageable) {
        log.debug("REST request to get a page of Abonnes");
        Page<Abonne> page = abonneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/abonnes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /abonnes/:id : get the "id" abonne.
     *
     * @param id the id of the abonne to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the abonne, or with status 404 (Not Found)
     */
    @GetMapping("/abonnes/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Abonne> getAbonne(@PathVariable Long id) {
        log.debug("REST request to get Abonne : {}", id);
        Abonne abonne = abonneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(abonne));
    }

    /**
     * DELETE  /abonnes/:id : delete the "id" abonne.
     *
     * @param id the id of the abonne to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/abonnes/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deleteAbonne(@PathVariable Long id) {
        log.debug("REST request to delete Abonne : {}", id);
        Abonne abonne = abonneService.findOne(id);
        abonneService.delete(abonne);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
