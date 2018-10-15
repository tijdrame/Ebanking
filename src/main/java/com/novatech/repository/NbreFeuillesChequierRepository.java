package com.novatech.repository;

import com.novatech.domain.NbreFeuillesChequier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NbreFeuillesChequier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NbreFeuillesChequierRepository extends JpaRepository<NbreFeuillesChequier, Long> {

    Page<NbreFeuillesChequier> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
