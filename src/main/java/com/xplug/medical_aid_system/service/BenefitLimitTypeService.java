package com.xplug.medical_aid_system.service;

import com.xplug.medical_aid_system.domain.BenefitLimitType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link BenefitLimitType}.
 */
public interface BenefitLimitTypeService {
    /**
     * Save a benefitLimitType.
     *
     * @param benefitLimitType the entity to save.
     * @return the persisted entity.
     */
    BenefitLimitType save(BenefitLimitType benefitLimitType);

    /**
     * Updates a benefitLimitType.
     *
     * @param benefitLimitType the entity to update.
     * @return the persisted entity.
     */
    BenefitLimitType update(BenefitLimitType benefitLimitType);

    /**
     * Partially updates a benefitLimitType.
     *
     * @param benefitLimitType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BenefitLimitType> partialUpdate(BenefitLimitType benefitLimitType);

    /**
     * Get all the benefitLimitTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BenefitLimitType> findAll(Pageable pageable);

    /**
     * Get the "id" benefitLimitType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BenefitLimitType> findOne(Long id);

    /**
     * Delete the "id" benefitLimitType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
