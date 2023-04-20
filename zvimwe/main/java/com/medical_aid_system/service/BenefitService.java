package com.medical_aid_system.service;

import com.medical_aid_system.domain.Benefit;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Benefit}.
 */
public interface BenefitService {
    /**
     * Save a benefit.
     *
     * @param benefit the entity to save.
     * @return the persisted entity.
     */
    Benefit save(Benefit benefit);

    /**
     * Updates a benefit.
     *
     * @param benefit the entity to update.
     * @return the persisted entity.
     */
    Benefit update(Benefit benefit);

    /**
     * Partially updates a benefit.
     *
     * @param benefit the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Benefit> partialUpdate(Benefit benefit);

    /**
     * Get all the benefits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Benefit> findAll(Pageable pageable);

    /**
     * Get the "id" benefit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Benefit> findOne(Long id);

    /**
     * Delete the "id" benefit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
