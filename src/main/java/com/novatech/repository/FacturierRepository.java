package com.novatech.repository;

import com.novatech.domain.Facturier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Facturier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacturierRepository extends JpaRepository<Facturier, Long> {

    Page<Facturier> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
