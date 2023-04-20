package com.medical_aid_system.repository;

import com.medical_aid_system.domain.PlanBenefit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlanBenefit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanBenefitRepository extends JpaRepository<PlanBenefit, Long> {}
