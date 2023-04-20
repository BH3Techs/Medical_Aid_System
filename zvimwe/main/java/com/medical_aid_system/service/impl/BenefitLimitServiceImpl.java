package com.medical_aid_system.service.impl;

import com.medical_aid_system.domain.BenefitLimit;
import com.medical_aid_system.repository.BenefitLimitRepository;
import com.medical_aid_system.service.BenefitLimitService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BenefitLimit}.
 */
@Service
@Transactional
public class BenefitLimitServiceImpl implements BenefitLimitService {

    private final Logger log = LoggerFactory.getLogger(BenefitLimitServiceImpl.class);

    private final BenefitLimitRepository benefitLimitRepository;

    public BenefitLimitServiceImpl(BenefitLimitRepository benefitLimitRepository) {
        this.benefitLimitRepository = benefitLimitRepository;
    }

    @Override
    public BenefitLimit save(BenefitLimit benefitLimit) {
        log.debug("Request to save BenefitLimit : {}", benefitLimit);
        return benefitLimitRepository.save(benefitLimit);
    }

    @Override
    public BenefitLimit update(BenefitLimit benefitLimit) {
        log.debug("Request to update BenefitLimit : {}", benefitLimit);
        return benefitLimitRepository.save(benefitLimit);
    }

    @Override
    public Optional<BenefitLimit> partialUpdate(BenefitLimit benefitLimit) {
        log.debug("Request to partially update BenefitLimit : {}", benefitLimit);

        return benefitLimitRepository
            .findById(benefitLimit.getId())
            .map(existingBenefitLimit -> {
                if (benefitLimit.getLimitValue() != null) {
                    existingBenefitLimit.setLimitValue(benefitLimit.getLimitValue());
                }
                if (benefitLimit.getLimitPeriodUnit() != null) {
                    existingBenefitLimit.setLimitPeriodUnit(benefitLimit.getLimitPeriodUnit());
                }
                if (benefitLimit.getLimitPeriodValue() != null) {
                    existingBenefitLimit.setLimitPeriodValue(benefitLimit.getLimitPeriodValue());
                }
                if (benefitLimit.getActive() != null) {
                    existingBenefitLimit.setActive(benefitLimit.getActive());
                }

                return existingBenefitLimit;
            })
            .map(benefitLimitRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BenefitLimit> findAll(Pageable pageable) {
        log.debug("Request to get all BenefitLimits");
        return benefitLimitRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BenefitLimit> findOne(Long id) {
        log.debug("Request to get BenefitLimit : {}", id);
        return benefitLimitRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BenefitLimit : {}", id);
        benefitLimitRepository.deleteById(id);
    }
}
