package com.xplug.medical_aid_system.service;

import com.xplug.medical_aid_system.domain.BenefitClaimTracker;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link BenefitClaimTracker}.
 */
public interface BenefitClaimTrackerService {
    /**
     * Save a benefitClaimTracker.
     *
     * @param benefitClaimTracker the entity to save.
     * @return the persisted entity.
     */
    BenefitClaimTracker save(BenefitClaimTracker benefitClaimTracker);

    /**
     * Updates a benefitClaimTracker.
     *
     * @param benefitClaimTracker the entity to update.
     * @return the persisted entity.
     */
    BenefitClaimTracker update(BenefitClaimTracker benefitClaimTracker);

    /**
     * Partially updates a benefitClaimTracker.
     *
     * @param benefitClaimTracker the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BenefitClaimTracker> partialUpdate(BenefitClaimTracker benefitClaimTracker);

    /**
     * Get all the benefitClaimTrackers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BenefitClaimTracker> findAll(Pageable pageable);

    /**
     * Get the "id" benefitClaimTracker.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BenefitClaimTracker> findOne(Long id);

    /**
     * Delete the "id" benefitClaimTracker.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
