package com.novatech.repository;

import com.novatech.domain.Devise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Devise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviseRepository extends JpaRepository<Devise, Long> {

    Page<Devise> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
