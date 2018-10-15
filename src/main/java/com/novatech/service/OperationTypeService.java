package com.novatech.service;

import com.novatech.domain.OperationType;
import com.novatech.repository.OperationTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing OperationType.
 */
@Service
@Transactional
public class OperationTypeService {

    private final Logger log = LoggerFactory.getLogger(OperationTypeService.class);

    private final OperationTypeRepository operationTypeRepository;

    public OperationTypeService(OperationTypeRepository operationTypeRepository) {
        this.operationTypeRepository = operationTypeRepository;
    }

    /**
     * Save a operationType.
     *
     * @param operationType the entity to save
     * @return the persisted entity
     */
    public OperationType save(OperationType operationType) {
        log.debug("Request to save OperationType : {}", operationType);
        operationType.deleted(false);
        return operationTypeRepository.save(operationType);
    }

    /**
     * Get all the operationTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OperationType> findAll(Pageable pageable) {
        log.debug("Request to get all OperationTypes");
        return operationTypeRepository.findByDeletedFalseOrderByLibelle(pageable);
    }

    /**
     * Get one operationType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OperationType findOne(Long id) {
        log.debug("Request to get OperationType : {}", id);
        return operationTypeRepository.findOne(id);
    }

    /**
     * Delete the operationType by id.
     *
     * @param id the id of the entity
     */
    public void delete(OperationType id) {
        log.debug("Request to delete OperationType : {}", id);
        id.deleted(true);
        operationTypeRepository.save(id);
    }
}
