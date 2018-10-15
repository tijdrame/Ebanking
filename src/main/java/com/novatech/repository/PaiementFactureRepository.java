package com.novatech.repository;

import com.novatech.domain.PaiementFacture;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PaiementFacture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaiementFactureRepository extends JpaRepository<PaiementFacture, Long> {

}
