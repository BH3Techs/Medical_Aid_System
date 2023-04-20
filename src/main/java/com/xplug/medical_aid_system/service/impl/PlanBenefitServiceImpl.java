package com.xplug.medical_aid_system.service.impl;

import com.xplug.medical_aid_system.domain.PlanBenefit;
import com.xplug.medical_aid_system.repository.PlanBenefitRepository;
import com.xplug.medical_aid_system.service.PlanBenefitService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlanBenefit}.
 */
@Service
@Transactional
public class PlanBenefitServiceImpl implements PlanBenefitService {

    private final Logger log = LoggerFactory.getLogger(PlanBenefitServiceImpl.class);

    private final PlanBenefitRepository planBenefitRepository;

    public PlanBenefitServiceImpl(PlanBenefitRepository planBenefitRepository) {
        this.planBenefitRepository = planBenefitRepository;
    }

    @Override
    public PlanBenefit save(PlanBenefit planBenefit) {
        log.debug("Request to save PlanBenefit : {}", planBenefit);
        return planBenefitRepository.save(planBenefit);
    }

    @Override
    public PlanBenefit update(PlanBenefit planBenefit) {
        log.debug("Request to update PlanBenefit : {}", planBenefit);
        return planBenefitRepository.save(planBenefit);
    }

    @Override
    public Optional<PlanBenefit> partialUpdate(PlanBenefit planBenefit) {
        log.debug("Request to partially update PlanBenefit : {}", planBenefit);

        return planBenefitRepository
            .findById(planBenefit.getId())
            .map(existingPlanBenefit -> {
                if (planBenefit.getName() != null) {
                    existingPlanBenefit.setName(planBenefit.getName());
                }
                if (planBenefit.getWaitingPeriodUnit() != null) {
                    existingPlanBenefit.setWaitingPeriodUnit(planBenefit.getWaitingPeriodUnit());
                }
                if (planBenefit.getWaitingPeriodValue() != null) {
                    existingPlanBenefit.setWaitingPeriodValue(planBenefit.getWaitingPeriodValue());
                }
                if (planBenefit.getDescription() != null) {
                    existingPlanBenefit.setDescription(planBenefit.getDescription());
                }
                if (planBenefit.getActive() != null) {
                    existingPlanBenefit.setActive(planBenefit.getActive());
                }

                return existingPlanBenefit;
            })
            .map(planBenefitRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanBenefit> findAll(Pageable pageable) {
        log.debug("Request to get all PlanBenefits");
        return planBenefitRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanBenefit> findOne(Long id) {
        log.debug("Request to get PlanBenefit : {}", id);
        return planBenefitRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanBenefit : {}", id);
        planBenefitRepository.deleteById(id);
    }
}
