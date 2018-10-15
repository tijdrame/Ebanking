package com.novatech.repository;

import com.novatech.domain.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OperationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {

    Page<OperationType> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
