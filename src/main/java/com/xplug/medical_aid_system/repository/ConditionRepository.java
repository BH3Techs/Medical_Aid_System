package com.xplug.medical_aid_system.repository;

import com.xplug.medical_aid_system.domain.Condition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Condition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConditionRepository extends JpaRepository<Condition, Long> {}
