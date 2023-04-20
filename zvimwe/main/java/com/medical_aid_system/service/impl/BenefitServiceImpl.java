package com.medical_aid_system.service.impl;

import com.medical_aid_system.domain.Benefit;
import com.medical_aid_system.repository.BenefitRepository;
import com.medical_aid_system.service.BenefitService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Benefit}.
 */
@Service
@Transactional
public class BenefitServiceImpl implements BenefitService {

    private final Logger log = LoggerFactory.getLogger(BenefitServiceImpl.class);

    private final BenefitRepository benefitRepository;

    public BenefitServiceImpl(BenefitRepository benefitRepository) {
        this.benefitRepository = benefitRepository;
    }

    @Override
    public Benefit save(Benefit benefit) {
        log.debug("Request to save Benefit : {}", benefit);
        return benefitRepository.save(benefit);
    }

    @Override
    public Benefit update(Benefit benefit) {
        log.debug("Request to update Benefit : {}", benefit);
        return benefitRepository.save(benefit);
    }

    @Override
    public Optional<Benefit> partialUpdate(Benefit benefit) {
        log.debug("Request to partially update Benefit : {}", benefit);

        return benefitRepository
            .findById(benefit.getId())
            .map(existingBenefit -> {
                if (benefit.getName() != null) {
                    existingBenefit.setName(benefit.getName());
                }
                if (benefit.getDescription() != null) {
                    existingBenefit.setDescription(benefit.getDescription());
                }
                if (benefit.getBenefitCode() != null) {
                    existingBenefit.setBenefitCode(benefit.getBenefitCode());
                }
                if (benefit.getActive() != null) {
                    existingBenefit.setActive(benefit.getActive());
                }

                return existingBenefit;
            })
            .map(benefitRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Benefit> findAll(Pageable pageable) {
        log.debug("Request to get all Benefits");
        return benefitRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Benefit> findOne(Long id) {
        log.debug("Request to get Benefit : {}", id);
        return benefitRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Benefit : {}", id);
        benefitRepository.deleteById(id);
    }
}
