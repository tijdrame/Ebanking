package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.BanquesPartenaires;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.BanquesPartenairesService;
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
 * REST controller for managing BanquesPartenaires.
 */
@RestController
@RequestMapping("/api")
public class BanquesPartenairesResource {

    private final Logger log = LoggerFactory.getLogger(BanquesPartenairesResource.class);

    private static final String ENTITY_NAME = "banquesPartenaires";

    private final BanquesPartenairesService banquesPartenairesService;

    public BanquesPartenairesResource(BanquesPartenairesService banquesPartenairesService) {
        this.banquesPartenairesService = banquesPartenairesService;
    }

    /**
     * POST  /banques-partenaires : Create a new banquesPartenaires.
     *
     * @param banquesPartenaires the banquesPartenaires to create
     * @return the ResponseEntity with status 201 (Created) and with body the new banquesPartenaires, or with status 400 (Bad Request) if the banquesPartenaires has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/banques-partenaires")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<BanquesPartenaires> createBanquesPartenaires(@Valid @RequestBody BanquesPartenaires banquesPartenaires) throws URISyntaxException {
        log.debug("REST request to save BanquesPartenaires : {}", banquesPartenaires);
        if (banquesPartenaires.getId() != null) {
            throw new BadRequestAlertException("A new banquesPartenaires cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BanquesPartenaires result = banquesPartenairesService.save(banquesPartenaires);
        return ResponseEntity.created(new URI("/api/banques-partenaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /banques-partenaires : Updates an existing banquesPartenaires.
     *
     * @param banquesPartenaires the banquesPartenaires to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated banquesPartenaires,
     * or with status 400 (Bad Request) if the banquesPartenaires is not valid,
     * or with status 500 (Internal Server Error) if the banquesPartenaires couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/banques-partenaires")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<BanquesPartenaires> updateBanquesPartenaires(@Valid @RequestBody BanquesPartenaires banquesPartenaires) throws URISyntaxException {
        log.debug("REST request to update BanquesPartenaires : {}", banquesPartenaires);
        if (banquesPartenaires.getId() == null) {
            return createBanquesPartenaires(banquesPartenaires);
        }
        BanquesPartenaires result = banquesPartenairesService.save(banquesPartenaires);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, banquesPartenaires.getId().toString()))
            .body(result);
    }

    /**
     * GET  /banques-partenaires : get all the banquesPartenaires.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of banquesPartenaires in body
     */
    @GetMapping("/banques-partenaires")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<List<BanquesPartenaires>> getAllBanquesPartenaires(Pageable pageable) {
        log.debug("REST request to get a page of BanquesPartenaires");
        Page<BanquesPartenaires> page = banquesPartenairesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/banques-partenaires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /banques-partenaires/:id : get the "id" banquesPartenaires.
     *
     * @param id the id of the banquesPartenaires to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the banquesPartenaires, or with status 404 (Not Found)
     */
    @GetMapping("/banques-partenaires/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<BanquesPartenaires> getBanquesPartenaires(@PathVariable Long id) {
        log.debug("REST request to get BanquesPartenaires : {}", id);
        BanquesPartenaires banquesPartenaires = banquesPartenairesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(banquesPartenaires));
    }

    /**
     * DELETE  /banques-partenaires/:id : delete the "id" banquesPartenaires.
     *
     * @param id the id of the banquesPartenaires to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/banques-partenaires/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deleteBanquesPartenaires(@PathVariable Long id) {
        log.debug("REST request to delete BanquesPartenaires : {}", id);
        BanquesPartenaires banquesPartenaires = banquesPartenairesService.findOne(id);
        banquesPartenairesService.delete(banquesPartenaires);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
