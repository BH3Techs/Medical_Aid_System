package com.xplug.medical_aid_system.repository;

import com.xplug.medical_aid_system.domain.PlanCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlanCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanCategoryRepository extends JpaRepository<PlanCategory, Long> {}
