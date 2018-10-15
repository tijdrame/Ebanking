package com.novatech.service;

import com.novatech.domain.OperationsVirement;
import com.novatech.repository.OperationsVirementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing OperationsVirement.
 */
@Service
@Transactional
public class OperationsVirementService {

    private final Logger log = LoggerFactory.getLogger(OperationsVirementService.class);

    private final OperationsVirementRepository operationsVirementRepository;

    public OperationsVirementService(OperationsVirementRepository operationsVirementRepository) {
        this.operationsVirementRepository = operationsVirementRepository;
    }

    /**
     * Save a operationsVirement.
     *
     * @param operationsVirement the entity to save
     * @return the persisted entity
     */
    public OperationsVirement save(OperationsVirement operationsVirement) {
        log.debug("Request to save OperationsVirement : {}", operationsVirement);
        return operationsVirementRepository.save(operationsVirement);
    }

    /**
     * Get all the operationsVirements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OperationsVirement> findAll(Pageable pageable) {
        log.debug("Request to get all OperationsVirements");
        return operationsVirementRepository.findAll(pageable);
    }

    /**
     * Get one operationsVirement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OperationsVirement findOne(Long id) {
        log.debug("Request to get OperationsVirement : {}", id);
        return operationsVirementRepository.findOne(id);
    }

    /**
     * Delete the operationsVirement by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OperationsVirement : {}", id);
        operationsVirementRepository.delete(id);
    }
}
