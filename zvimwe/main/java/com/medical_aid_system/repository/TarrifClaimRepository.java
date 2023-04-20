package com.medical_aid_system.repository;

import com.medical_aid_system.domain.TarrifClaim;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TarrifClaim entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarrifClaimRepository extends JpaRepository<TarrifClaim, Long> {}
