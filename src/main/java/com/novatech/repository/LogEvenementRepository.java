package com.novatech.repository;

import com.novatech.domain.LogEvenement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the LogEvenement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogEvenementRepository extends JpaRepository<LogEvenement, Long> {

    @Query("select log_evenement from LogEvenement log_evenement where log_evenement.userCreated.login = ?#{principal.username}")
    List<LogEvenement> findByUserCreatedIsCurrentUser();

}
