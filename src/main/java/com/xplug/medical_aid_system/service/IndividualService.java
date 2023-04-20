package com.xplug.medical_aid_system.service;

import com.xplug.medical_aid_system.domain.Individual;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Individual}.
 */
public interface IndividualService {
    /**
     * Save a individual.
     *
     * @param individual the entity to save.
     * @return the persisted entity.
     */
    Individual save(Individual individual);

    /**
     * Updates a individual.
     *
     * @param individual the entity to update.
     * @return the persisted entity.
     */
    Individual update(Individual individual);

    /**
     * Partially updates a individual.
     *
     * @param individual the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Individual> partialUpdate(Individual individual);

    /**
     * Get all the individuals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Individual> findAll(Pageable pageable);

    /**
     * Get the "id" individual.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Individual> findOne(Long id);

    /**
     * Delete the "id" individual.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
