package com.xplug.medical_aid_system.repository;

import com.xplug.medical_aid_system.domain.BenefitLimit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BenefitLimit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenefitLimitRepository extends JpaRepository<BenefitLimit, Long> {}
