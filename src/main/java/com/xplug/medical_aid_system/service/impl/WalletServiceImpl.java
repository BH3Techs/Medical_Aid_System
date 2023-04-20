package com.xplug.medical_aid_system.service.impl;

import com.xplug.medical_aid_system.domain.Wallet;
import com.xplug.medical_aid_system.repository.WalletRepository;
import com.xplug.medical_aid_system.service.WalletService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Wallet}.
 */
@Service
@Transactional
public class WalletServiceImpl implements WalletService {

    private final Logger log = LoggerFactory.getLogger(WalletServiceImpl.class);

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet save(Wallet wallet) {
        log.debug("Request to save Wallet : {}", wallet);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet update(Wallet wallet) {
        log.debug("Request to update Wallet : {}", wallet);
        return walletRepository.save(wallet);
    }

    @Override
    public Optional<Wallet> partialUpdate(Wallet wallet) {
        log.debug("Request to partially update Wallet : {}", wallet);

        return walletRepository
            .findById(wallet.getId())
            .map(existingWallet -> {
                if (wallet.getName() != null) {
                    existingWallet.setName(wallet.getName());
                }
                if (wallet.getBalance() != null) {
                    existingWallet.setBalance(wallet.getBalance());
                }
                if (wallet.getOwnerIdentifier() != null) {
                    existingWallet.setOwnerIdentifier(wallet.getOwnerIdentifier());
                }
                if (wallet.getOwnerType() != null) {
                    existingWallet.setOwnerType(wallet.getOwnerType());
                }
                if (wallet.getDescription() != null) {
                    existingWallet.setDescription(wallet.getDescription());
                }
                if (wallet.getActive() != null) {
                    existingWallet.setActive(wallet.getActive());
                }

                return existingWallet;
            })
            .map(walletRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Wallet> findAll(Pageable pageable) {
        log.debug("Request to get all Wallets");
        return walletRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Wallet> findOne(Long id) {
        log.debug("Request to get Wallet : {}", id);
        return walletRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Wallet : {}", id);
        walletRepository.deleteById(id);
    }
}
