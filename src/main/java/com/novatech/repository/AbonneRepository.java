package com.novatech.repository;

import com.novatech.domain.Abonne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Abonne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbonneRepository extends JpaRepository<Abonne, Long> {

    @Query("select abonne from Abonne abonne where abonne.gestionnaire.login = ?#{principal.username}")
    List<Abonne> findByGestionnaireIsCurrentUser();

    Page<Abonne> findByDeletedFalseOrderByUser_LastName(Pageable pageable);

}
