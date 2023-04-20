package com.xplug.medical_aid_system.service;

import com.xplug.medical_aid_system.domain.BenefitType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link BenefitType}.
 */
public interface BenefitTypeService {
    /**
     * Save a benefitType.
     *
     * @param benefitType the entity to save.
     * @return the persisted entity.
     */
    BenefitType save(BenefitType benefitType);

    /**
     * Updates a benefitType.
     *
     * @param benefitType the entity to update.
     * @return the persisted entity.
     */
    BenefitType update(BenefitType benefitType);

    /**
     * Partially updates a benefitType.
     *
     * @param benefitType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BenefitType> partialUpdate(BenefitType benefitType);

    /**
     * Get all the benefitTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BenefitType> findAll(Pageable pageable);

    /**
     * Get the "id" benefitType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BenefitType> findOne(Long id);

    /**
     * Delete the "id" benefitType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
