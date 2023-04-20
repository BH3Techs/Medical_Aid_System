package com.xplug.medical_aid_system.service.impl;

import com.xplug.medical_aid_system.domain.BenefitType;
import com.xplug.medical_aid_system.repository.BenefitTypeRepository;
import com.xplug.medical_aid_system.service.BenefitTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BenefitType}.
 */
@Service
@Transactional
public class BenefitTypeServiceImpl implements BenefitTypeService {

    private final Logger log = LoggerFactory.getLogger(BenefitTypeServiceImpl.class);

    private final BenefitTypeRepository benefitTypeRepository;

    public BenefitTypeServiceImpl(BenefitTypeRepository benefitTypeRepository) {
        this.benefitTypeRepository = benefitTypeRepository;
    }

    @Override
    public BenefitType save(BenefitType benefitType) {
        log.debug("Request to save BenefitType : {}", benefitType);
        return benefitTypeRepository.save(benefitType);
    }

    @Override
    public BenefitType update(BenefitType benefitType) {
        log.debug("Request to update BenefitType : {}", benefitType);
        return benefitTypeRepository.save(benefitType);
    }

    @Override
    public Optional<BenefitType> partialUpdate(BenefitType benefitType) {
        log.debug("Request to partially update BenefitType : {}", benefitType);

        return benefitTypeRepository
            .findById(benefitType.getId())
            .map(existingBenefitType -> {
                if (benefitType.getName() != null) {
                    existingBenefitType.setName(benefitType.getName());
                }
                if (benefitType.getDescription() != null) {
                    existingBenefitType.setDescription(benefitType.getDescription());
                }
                if (benefitType.getActive() != null) {
                    existingBenefitType.setActive(benefitType.getActive());
                }

                return existingBenefitType;
            })
            .map(benefitTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BenefitType> findAll(Pageable pageable) {
        log.debug("Request to get all BenefitTypes");
        return benefitTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BenefitType> findOne(Long id) {
        log.debug("Request to get BenefitType : {}", id);
        return benefitTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BenefitType : {}", id);
        benefitTypeRepository.deleteById(id);
    }
}
