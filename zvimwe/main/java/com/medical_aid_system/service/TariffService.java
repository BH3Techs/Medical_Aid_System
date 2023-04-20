package com.medical_aid_system.service;

import com.medical_aid_system.domain.Tariff;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Tariff}.
 */
public interface TariffService {
    /**
     * Save a tariff.
     *
     * @param tariff the entity to save.
     * @return the persisted entity.
     */
    Tariff save(Tariff tariff);

    /**
     * Updates a tariff.
     *
     * @param tariff the entity to update.
     * @return the persisted entity.
     */
    Tariff update(Tariff tariff);

    /**
     * Partially updates a tariff.
     *
     * @param tariff the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Tariff> partialUpdate(Tariff tariff);

    /**
     * Get all the tariffs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Tariff> findAll(Pageable pageable);

    /**
     * Get the "id" tariff.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tariff> findOne(Long id);

    /**
     * Delete the "id" tariff.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
