package com.medical_aid_system.repository;

import com.medical_aid_system.domain.BenefitClaimTracker;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BenefitClaimTracker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenefitClaimTrackerRepository extends JpaRepository<BenefitClaimTracker, Long> {}
