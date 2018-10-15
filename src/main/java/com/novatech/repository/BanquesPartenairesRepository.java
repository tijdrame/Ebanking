package com.novatech.repository;

import com.novatech.domain.BanquesPartenaires;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BanquesPartenaires entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BanquesPartenairesRepository extends JpaRepository<BanquesPartenaires, Long> {

    Page<BanquesPartenaires> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
