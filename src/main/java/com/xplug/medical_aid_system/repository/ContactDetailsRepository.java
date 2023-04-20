package com.xplug.medical_aid_system.repository;

import com.xplug.medical_aid_system.domain.ContactDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ContactDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {}
