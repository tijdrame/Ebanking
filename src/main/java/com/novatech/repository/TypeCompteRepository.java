package com.novatech.repository;

import com.novatech.domain.TypeCompte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TypeCompte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeCompteRepository extends JpaRepository<TypeCompte, Long> {

    Page<TypeCompte> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
