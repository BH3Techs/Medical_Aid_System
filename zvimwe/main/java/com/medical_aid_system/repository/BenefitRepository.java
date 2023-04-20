package com.medical_aid_system.repository;

import com.medical_aid_system.domain.Benefit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Benefit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {}
