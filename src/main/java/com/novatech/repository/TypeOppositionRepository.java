package com.novatech.repository;

import com.novatech.domain.TypeOpposition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypeOpposition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeOppositionRepository extends JpaRepository<TypeOpposition, Long> {

    Page<TypeOpposition> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
