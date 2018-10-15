package com.novatech.repository;

import com.novatech.domain.TypeChequier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypeChequier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeChequierRepository extends JpaRepository<TypeChequier, Long> {

    Page<TypeChequier> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
