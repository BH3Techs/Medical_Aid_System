package com.xplug.medical_aid_system.service;

import com.xplug.medical_aid_system.domain.BenefitLimit;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link BenefitLimit}.
 */
public interface BenefitLimitService {
    /**
     * Save a benefitLimit.
     *
     * @param benefitLimit the entity to save.
     * @return the persisted entity.
     */
    BenefitLimit save(BenefitLimit benefitLimit);

    /**
     * Updates a benefitLimit.
     *
     * @param benefitLimit the entity to update.
     * @return the persisted entity.
     */
    BenefitLimit update(BenefitLimit benefitLimit);

    /**
     * Partially updates a benefitLimit.
     *
     * @param benefitLimit the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BenefitLimit> partialUpdate(BenefitLimit benefitLimit);

    /**
     * Get all the benefitLimits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BenefitLimit> findAll(Pageable pageable);

    /**
     * Get the "id" benefitLimit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BenefitLimit> findOne(Long id);

    /**
     * Delete the "id" benefitLimit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
