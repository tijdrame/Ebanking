package com.novatech.repository;

import com.novatech.domain.Abonne;
import com.novatech.domain.Compte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Compte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {

    Page<Compte> findByAbonneAndDeletedFalseOrderByNumero(Abonne abonne, Pageable pageable);
    Page<Compte> findByDeletedFalseOrderByNumero(Pageable pageable);
}
