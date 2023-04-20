package com.xplug.medical_aid_system.repository;

import com.xplug.medical_aid_system.domain.RiskProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RiskProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiskProfileRepository extends JpaRepository<RiskProfile, Long> {}
