package com.novatech.service;

import com.novatech.domain.Operation;
import com.novatech.repository.OperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Operation.
 */
@Service
@Transactional
public class OperationService {

    private final Logger log = LoggerFactory.getLogger(OperationService.class);

    private final OperationRepository operationRepository;

    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    /**
     * Save a operation.
     *
     * @param operation the entity to save
     * @return the persisted entity
     */
    public Operation save(Operation operation) {
        log.debug("Request to save Operation : {}", operation);
        return operationRepository.save(operation);
    }

    /**
     * Get all the operations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Operation> findAll(Pageable pageable) {
        log.debug("Request to get all Operations");
        return operationRepository.findAll(pageable);
    }

    /**
     * Get one operation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Operation findOne(Long id) {
        log.debug("Request to get Operation : {}", id);
        return operationRepository.findOne(id);
    }

    /**
     * Delete the operation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Operation : {}", id);
        operationRepository.delete(id);
    }
}
