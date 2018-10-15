package com.novatech.repository;

import com.novatech.domain.TypeCarte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypeCarte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeCarteRepository extends JpaRepository<TypeCarte, Long> {

    Page<TypeCarte> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
