package com.xplug.medical_aid_system.service;

import com.xplug.medical_aid_system.domain.PlanCategory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PlanCategory}.
 */
public interface PlanCategoryService {
    /**
     * Save a planCategory.
     *
     * @param planCategory the entity to save.
     * @return the persisted entity.
     */
    PlanCategory save(PlanCategory planCategory);

    /**
     * Updates a planCategory.
     *
     * @param planCategory the entity to update.
     * @return the persisted entity.
     */
    PlanCategory update(PlanCategory planCategory);

    /**
     * Partially updates a planCategory.
     *
     * @param planCategory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlanCategory> partialUpdate(PlanCategory planCategory);

    /**
     * Get all the planCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanCategory> findAll(Pageable pageable);

    /**
     * Get the "id" planCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanCategory> findOne(Long id);

    /**
     * Delete the "id" planCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
