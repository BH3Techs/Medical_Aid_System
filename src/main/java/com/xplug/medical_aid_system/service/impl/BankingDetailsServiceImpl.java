package com.xplug.medical_aid_system.service.impl;

import com.xplug.medical_aid_system.domain.BankingDetails;
import com.xplug.medical_aid_system.repository.BankingDetailsRepository;
import com.xplug.medical_aid_system.service.BankingDetailsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankingDetails}.
 */
@Service
@Transactional
public class BankingDetailsServiceImpl implements BankingDetailsService {

    private final Logger log = LoggerFactory.getLogger(BankingDetailsServiceImpl.class);

    private final BankingDetailsRepository bankingDetailsRepository;

    public BankingDetailsServiceImpl(BankingDetailsRepository bankingDetailsRepository) {
        this.bankingDetailsRepository = bankingDetailsRepository;
    }

    @Override
    public BankingDetails save(BankingDetails bankingDetails) {
        log.debug("Request to save BankingDetails : {}", bankingDetails);
        return bankingDetailsRepository.save(bankingDetails);
    }

    @Override
    public BankingDetails update(BankingDetails bankingDetails) {
        log.debug("Request to update BankingDetails : {}", bankingDetails);
        return bankingDetailsRepository.save(bankingDetails);
    }

    @Override
    public Optional<BankingDetails> partialUpdate(BankingDetails bankingDetails) {
        log.debug("Request to partially update BankingDetails : {}", bankingDetails);

        return bankingDetailsRepository
            .findById(bankingDetails.getId())
            .map(existingBankingDetails -> {
                if (bankingDetails.getAccountName() != null) {
                    existingBankingDetails.setAccountName(bankingDetails.getAccountName());
                }
                if (bankingDetails.getAccountNumber() != null) {
                    existingBankingDetails.setAccountNumber(bankingDetails.getAccountNumber());
                }
                if (bankingDetails.getSwiftCode() != null) {
                    existingBankingDetails.setSwiftCode(bankingDetails.getSwiftCode());
                }
                if (bankingDetails.getBankName() != null) {
                    existingBankingDetails.setBankName(bankingDetails.getBankName());
                }

                return existingBankingDetails;
            })
            .map(bankingDetailsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BankingDetails> findAll(Pageable pageable) {
        log.debug("Request to get all BankingDetails");
        return bankingDetailsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankingDetails> findOne(Long id) {
        log.debug("Request to get BankingDetails : {}", id);
        return bankingDetailsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BankingDetails : {}", id);
        bankingDetailsRepository.deleteById(id);
    }
}
