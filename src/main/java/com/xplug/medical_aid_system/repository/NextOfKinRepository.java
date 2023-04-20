package com.xplug.medical_aid_system.repository;

import com.xplug.medical_aid_system.domain.NextOfKin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NextOfKin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NextOfKinRepository extends JpaRepository<NextOfKin, Long> {}
