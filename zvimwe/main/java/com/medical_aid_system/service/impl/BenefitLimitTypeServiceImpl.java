package com.medical_aid_system.service.impl;

import com.medical_aid_system.domain.BenefitLimitType;
import com.medical_aid_system.repository.BenefitLimitTypeRepository;
import com.medical_aid_system.service.BenefitLimitTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BenefitLimitType}.
 */
@Service
@Transactional
public class BenefitLimitTypeServiceImpl implements BenefitLimitTypeService {

    private final Logger log = LoggerFactory.getLogger(BenefitLimitTypeServiceImpl.class);

    private final BenefitLimitTypeRepository benefitLimitTypeRepository;

    public BenefitLimitTypeServiceImpl(BenefitLimitTypeRepository benefitLimitTypeRepository) {
        this.benefitLimitTypeRepository = benefitLimitTypeRepository;
    }

    @Override
    public BenefitLimitType save(BenefitLimitType benefitLimitType) {
        log.debug("Request to save BenefitLimitType : {}", benefitLimitType);
        return benefitLimitTypeRepository.save(benefitLimitType);
    }

    @Override
    public BenefitLimitType update(BenefitLimitType benefitLimitType) {
        log.debug("Request to update BenefitLimitType : {}", benefitLimitType);
        return benefitLimitTypeRepository.save(benefitLimitType);
    }

    @Override
    public Optional<BenefitLimitType> partialUpdate(BenefitLimitType benefitLimitType) {
        log.debug("Request to partially update BenefitLimitType : {}", benefitLimitType);

        return benefitLimitTypeRepository
            .findById(benefitLimitType.getId())
            .map(existingBenefitLimitType -> {
                if (benefitLimitType.getName() != null) {
                    existingBenefitLimitType.setName(benefitLimitType.getName());
                }
                if (benefitLimitType.getActive() != null) {
                    existingBenefitLimitType.setActive(benefitLimitType.getActive());
                }

                return existingBenefitLimitType;
            })
            .map(benefitLimitTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BenefitLimitType> findAll(Pageable pageable) {
        log.debug("Request to get all BenefitLimitTypes");
        return benefitLimitTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BenefitLimitType> findOne(Long id) {
        log.debug("Request to get BenefitLimitType : {}", id);
        return benefitLimitTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BenefitLimitType : {}", id);
        benefitLimitTypeRepository.deleteById(id);
    }
}
