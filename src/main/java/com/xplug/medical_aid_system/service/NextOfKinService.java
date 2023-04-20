package com.xplug.medical_aid_system.service;

import com.xplug.medical_aid_system.domain.NextOfKin;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link NextOfKin}.
 */
public interface NextOfKinService {
    /**
     * Save a nextOfKin.
     *
     * @param nextOfKin the entity to save.
     * @return the persisted entity.
     */
    NextOfKin save(NextOfKin nextOfKin);

    /**
     * Updates a nextOfKin.
     *
     * @param nextOfKin the entity to update.
     * @return the persisted entity.
     */
    NextOfKin update(NextOfKin nextOfKin);

    /**
     * Partially updates a nextOfKin.
     *
     * @param nextOfKin the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NextOfKin> partialUpdate(NextOfKin nextOfKin);

    /**
     * Get all the nextOfKins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NextOfKin> findAll(Pageable pageable);

    /**
     * Get the "id" nextOfKin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NextOfKin> findOne(Long id);

    /**
     * Delete the "id" nextOfKin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
