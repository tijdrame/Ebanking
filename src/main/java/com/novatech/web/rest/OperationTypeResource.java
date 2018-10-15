package com.novatech.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.novatech.domain.OperationType;
import com.novatech.security.AuthoritiesConstants;
import com.novatech.service.OperationTypeService;
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
 * REST controller for managing OperationType.
 */
@RestController
@RequestMapping("/api")
public class OperationTypeResource {

    private final Logger log = LoggerFactory.getLogger(OperationTypeResource.class);

    private static final String ENTITY_NAME = "operationType";

    private final OperationTypeService operationTypeService;

    public OperationTypeResource(OperationTypeService operationTypeService) {
        this.operationTypeService = operationTypeService;
    }

    /**
     * POST  /operation-types : Create a new operationType.
     *
     * @param operationType the operationType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operationType, or with status 400 (Bad Request) if the operationType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operation-types")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<OperationType> createOperationType(@Valid @RequestBody OperationType operationType) throws URISyntaxException {
        log.debug("REST request to save OperationType : {}", operationType);
        if (operationType.getId() != null) {
            throw new BadRequestAlertException("A new operationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperationType result = operationTypeService.save(operationType);
        return ResponseEntity.created(new URI("/api/operation-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operation-types : Updates an existing operationType.
     *
     * @param operationType the operationType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operationType,
     * or with status 400 (Bad Request) if the operationType is not valid,
     * or with status 500 (Internal Server Error) if the operationType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operation-types")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<OperationType> updateOperationType(@Valid @RequestBody OperationType operationType) throws URISyntaxException {
        log.debug("REST request to update OperationType : {}", operationType);
        if (operationType.getId() == null) {
            return createOperationType(operationType);
        }
        OperationType result = operationTypeService.save(operationType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operationType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operation-types : get all the operationTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of operationTypes in body
     */
    @GetMapping("/operation-types")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<List<OperationType>> getAllOperationTypes(Pageable pageable) {
        log.debug("REST request to get a page of OperationTypes");
        Page<OperationType> page = operationTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operation-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /operation-types/:id : get the "id" operationType.
     *
     * @param id the id of the operationType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operationType, or with status 404 (Not Found)
     */
    @GetMapping("/operation-types/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<OperationType> getOperationType(@PathVariable Long id) {
        log.debug("REST request to get OperationType : {}", id);
        OperationType operationType = operationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(operationType));
    }

    /**
     * DELETE  /operation-types/:id : delete the "id" operationType.
     *
     * @param id the id of the operationType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operation-types/{id}")
    @Timed
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.GESTIONNAIRE})
    public ResponseEntity<Void> deleteOperationType(@PathVariable Long id) {
        log.debug("REST request to delete OperationType : {}", id);
        OperationType operationType = operationTypeService.findOne(id);
        operationTypeService.delete(operationType);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
