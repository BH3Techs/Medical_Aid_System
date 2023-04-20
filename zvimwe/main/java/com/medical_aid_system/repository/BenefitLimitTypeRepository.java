package com.medical_aid_system.repository;

import com.medical_aid_system.domain.BenefitLimitType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BenefitLimitType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenefitLimitTypeRepository extends JpaRepository<BenefitLimitType, Long> {}
