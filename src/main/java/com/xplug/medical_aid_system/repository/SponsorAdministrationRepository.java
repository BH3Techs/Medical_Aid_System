package com.xplug.medical_aid_system.repository;

import com.xplug.medical_aid_system.domain.SponsorAdministration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SponsorAdministration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SponsorAdministrationRepository extends JpaRepository<SponsorAdministration, Long> {}
