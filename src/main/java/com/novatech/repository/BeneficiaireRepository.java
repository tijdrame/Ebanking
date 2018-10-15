package com.novatech.repository;

import com.novatech.domain.Beneficiaire;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Beneficiaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeneficiaireRepository extends JpaRepository<Beneficiaire, Long> {

}
