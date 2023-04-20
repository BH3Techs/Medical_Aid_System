package com.medical_aid_system.repository;

import com.medical_aid_system.domain.PlanBillingCycle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlanBillingCycle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanBillingCycleRepository extends JpaRepository<PlanBillingCycle, Long> {}
