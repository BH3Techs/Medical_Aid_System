package com.xplug.medical_aid_system.service.impl;

import com.xplug.medical_aid_system.domain.BenefitClaimTracker;
import com.xplug.medical_aid_system.repository.BenefitClaimTrackerRepository;
import com.xplug.medical_aid_system.service.BenefitClaimTrackerService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BenefitClaimTracker}.
 */
@Service
@Transactional
public class BenefitClaimTrackerServiceImpl implements BenefitClaimTrackerService {

    private final Logger log = LoggerFactory.getLogger(BenefitClaimTrackerServiceImpl.class);

    private final BenefitClaimTrackerRepository benefitClaimTrackerRepository;

    public BenefitClaimTrackerServiceImpl(BenefitClaimTrackerRepository benefitClaimTrackerRepository) {
        this.benefitClaimTrackerRepository = benefitClaimTrackerRepository;
    }

    @Override
    public BenefitClaimTracker save(BenefitClaimTracker benefitClaimTracker) {
        log.debug("Request to save BenefitClaimTracker : {}", benefitClaimTracker);
        return benefitClaimTrackerRepository.save(benefitClaimTracker);
    }

    @Override
    public BenefitClaimTracker update(BenefitClaimTracker benefitClaimTracker) {
        log.debug("Request to update BenefitClaimTracker : {}", benefitClaimTracker);
        return benefitClaimTrackerRepository.save(benefitClaimTracker);
    }

    @Override
    public Optional<BenefitClaimTracker> partialUpdate(BenefitClaimTracker benefitClaimTracker) {
        log.debug("Request to partially update BenefitClaimTracker : {}", benefitClaimTracker);

        return benefitClaimTrackerRepository
            .findById(benefitClaimTracker.getId())
            .map(existingBenefitClaimTracker -> {
                if (benefitClaimTracker.getResetDate() != null) {
                    existingBenefitClaimTracker.setResetDate(benefitClaimTracker.getResetDate());
                }
                if (benefitClaimTracker.getNextPossibleClaimDate() != null) {
                    existingBenefitClaimTracker.setNextPossibleClaimDate(benefitClaimTracker.getNextPossibleClaimDate());
                }
                if (benefitClaimTracker.getCurrentLimitValue() != null) {
                    existingBenefitClaimTracker.setCurrentLimitValue(benefitClaimTracker.getCurrentLimitValue());
                }
                if (benefitClaimTracker.getCurrentLimitPeriod() != null) {
                    existingBenefitClaimTracker.setCurrentLimitPeriod(benefitClaimTracker.getCurrentLimitPeriod());
                }

                return existingBenefitClaimTracker;
            })
            .map(benefitClaimTrackerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BenefitClaimTracker> findAll(Pageable pageable) {
        log.debug("Request to get all BenefitClaimTrackers");
        return benefitClaimTrackerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BenefitClaimTracker> findOne(Long id) {
        log.debug("Request to get BenefitClaimTracker : {}", id);
        return benefitClaimTrackerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BenefitClaimTracker : {}", id);
        benefitClaimTrackerRepository.deleteById(id);
    }
}
