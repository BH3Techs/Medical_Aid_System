package com.medical_aid_system.service;

import com.medical_aid_system.domain.Wallet;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Wallet}.
 */
public interface WalletService {
    /**
     * Save a wallet.
     *
     * @param wallet the entity to save.
     * @return the persisted entity.
     */
    Wallet save(Wallet wallet);

    /**
     * Updates a wallet.
     *
     * @param wallet the entity to update.
     * @return the persisted entity.
     */
    Wallet update(Wallet wallet);

    /**
     * Partially updates a wallet.
     *
     * @param wallet the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Wallet> partialUpdate(Wallet wallet);

    /**
     * Get all the wallets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Wallet> findAll(Pageable pageable);

    /**
     * Get the "id" wallet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Wallet> findOne(Long id);

    /**
     * Delete the "id" wallet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
