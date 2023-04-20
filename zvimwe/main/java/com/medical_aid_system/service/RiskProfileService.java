package com.medical_aid_system.service;

import com.medical_aid_system.domain.RiskProfile;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link RiskProfile}.
 */
public interface RiskProfileService {
    /**
     * Save a riskProfile.
     *
     * @param riskProfile the entity to save.
     * @return the persisted entity.
     */
    RiskProfile save(RiskProfile riskProfile);

    /**
     * Updates a riskProfile.
     *
     * @param riskProfile the entity to update.
     * @return the persisted entity.
     */
    RiskProfile update(RiskProfile riskProfile);

    /**
     * Partially updates a riskProfile.
     *
     * @param riskProfile the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RiskProfile> partialUpdate(RiskProfile riskProfile);

    /**
     * Get all the riskProfiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RiskProfile> findAll(Pageable pageable);

    /**
     * Get the "id" riskProfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RiskProfile> findOne(Long id);

    /**
     * Delete the "id" riskProfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
