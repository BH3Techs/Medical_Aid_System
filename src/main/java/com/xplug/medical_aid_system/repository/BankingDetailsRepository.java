package com.xplug.medical_aid_system.repository;

import com.xplug.medical_aid_system.domain.BankingDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BankingDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankingDetailsRepository extends JpaRepository<BankingDetails, Long> {}
