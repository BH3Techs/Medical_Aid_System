package com.medical_aid_system.repository;

import com.medical_aid_system.domain.BenefitType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BenefitType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenefitTypeRepository extends JpaRepository<BenefitType, Long> {}
