package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.OperationsVirement;
import com.novatech.service.OperationsVirementService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OperationsVirement.
 */
@RestController
@RequestMapping("/api")
public class OperationsVirementResource {

    private final Logger log = LoggerFactory.getLogger(OperationsVirementResource.class);

    private static final String ENTITY_NAME = "operationsVirement";

    private final OperationsVirementService operationsVirementService;

    public OperationsVirementResource(OperationsVirementService operationsVirementService) {
        this.operationsVirementService = operationsVirementService;
    }

    /**
     * POST  /operations-virements : Create a new operationsVirement.
     *
     * @param operationsVirement the operationsVirement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operationsVirement, or with status 400 (Bad Request) if the operationsVirement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operations-virements")
    @Timed
    public ResponseEntity<OperationsVirement> createOperationsVirement(@Valid @RequestBody OperationsVirement operationsVirement) throws URISyntaxException {
        log.debug("REST request to save OperationsVirement : {}", operationsVirement);
        if (operationsVirement.getId() != null) {
            throw new BadRequestAlertException("A new operationsVirement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperationsVirement result = operationsVirementService.save(operationsVirement);
        return ResponseEntity.created(new URI("/api/operations-virements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operations-virements : Updates an existing operationsVirement.
     *
     * @param operationsVirement the operationsVirement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operationsVirement,
     * or with status 400 (Bad Request) if the operationsVirement is not valid,
     * or with status 500 (Internal Server Error) if the operationsVirement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operations-virements")
    @Timed
    public ResponseEntity<OperationsVirement> updateOperationsVirement(@Valid @RequestBody OperationsVirement operationsVirement) throws URISyntaxException {
        log.debug("REST request to update OperationsVirement : {}", operationsVirement);
        if (operationsVirement.getId() == null) {
            return createOperationsVirement(operationsVirement);
        }
        OperationsVirement result = operationsVirementService.save(operationsVirement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operationsVirement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operations-virements : get all the operationsVirements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of operationsVirements in body
     */
    @GetMapping("/operations-virements")
    @Timed
    public ResponseEntity<List<OperationsVirement>> getAllOperationsVirements(Pageable pageable) {
        log.debug("REST request to get a page of OperationsVirements");
        Page<OperationsVirement> page = operationsVirementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operations-virements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /operations-virements/:id : get the "id" operationsVirement.
     *
     * @param id the id of the operationsVirement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operationsVirement, or with status 404 (Not Found)
     */
    @GetMapping("/operations-virements/{id}")
    @Timed
    public ResponseEntity<OperationsVirement> getOperationsVirement(@PathVariable Long id) {
        log.debug("REST request to get OperationsVirement : {}", id);
        OperationsVirement operationsVirement = operationsVirementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(operationsVirement));
    }

    /**
     * DELETE  /operations-virements/:id : delete the "id" operationsVirement.
     *
     * @param id the id of the operationsVirement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operations-virements/{id}")
    @Timed
    public ResponseEntity<Void> deleteOperationsVirement(@PathVariable Long id) {
        log.debug("REST request to delete OperationsVirement : {}", id);
        operationsVirementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
