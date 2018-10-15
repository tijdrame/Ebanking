package com.novatech.repository;

import com.novatech.domain.PriseEnCharge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PriseEnCharge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriseEnChargeRepository extends JpaRepository<PriseEnCharge, Long> {

    Page<PriseEnCharge> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
