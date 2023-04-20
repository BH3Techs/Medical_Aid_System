package com.medical_aid_system.repository;

import com.medical_aid_system.domain.Individual;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Individual entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndividualRepository extends JpaRepository<Individual, Long> {}
