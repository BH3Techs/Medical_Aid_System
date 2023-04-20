package com.medical_aid_system.service;

import com.medical_aid_system.domain.BankingDetails;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link BankingDetails}.
 */
public interface BankingDetailsService {
    /**
     * Save a bankingDetails.
     *
     * @param bankingDetails the entity to save.
     * @return the persisted entity.
     */
    BankingDetails save(BankingDetails bankingDetails);

    /**
     * Updates a bankingDetails.
     *
     * @param bankingDetails the entity to update.
     * @return the persisted entity.
     */
    BankingDetails update(BankingDetails bankingDetails);

    /**
     * Partially updates a bankingDetails.
     *
     * @param bankingDetails the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankingDetails> partialUpdate(BankingDetails bankingDetails);

    /**
     * Get all the bankingDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankingDetails> findAll(Pageable pageable);

    /**
     * Get the "id" bankingDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankingDetails> findOne(Long id);

    /**
     * Delete the "id" bankingDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
