package com.novatech.repository;

import com.novatech.domain.OperationsVirement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OperationsVirement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationsVirementRepository extends JpaRepository<OperationsVirement, Long> {

}
