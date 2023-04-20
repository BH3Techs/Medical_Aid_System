package com.medical_aid_system.repository;

import com.medical_aid_system.domain.Tariff;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tariff entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {}
