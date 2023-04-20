package com.medical_aid_system.service;

import com.medical_aid_system.domain.PlanBenefit;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PlanBenefit}.
 */
public interface PlanBenefitService {
    /**
     * Save a planBenefit.
     *
     * @param planBenefit the entity to save.
     * @return the persisted entity.
     */
    PlanBenefit save(PlanBenefit planBenefit);

    /**
     * Updates a planBenefit.
     *
     * @param planBenefit the entity to update.
     * @return the persisted entity.
     */
    PlanBenefit update(PlanBenefit planBenefit);

    /**
     * Partially updates a planBenefit.
     *
     * @param planBenefit the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlanBenefit> partialUpdate(PlanBenefit planBenefit);

    /**
     * Get all the planBenefits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanBenefit> findAll(Pageable pageable);

    /**
     * Get the "id" planBenefit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanBenefit> findOne(Long id);

    /**
     * Delete the "id" planBenefit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
