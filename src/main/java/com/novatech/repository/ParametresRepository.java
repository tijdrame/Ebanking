package com.novatech.repository;

import com.novatech.domain.Parametres;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Parametres entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametresRepository extends JpaRepository<Parametres, Long> {

    Page<Parametres> findByDeletedFalseOrderByCle(Pageable pageable);
}
