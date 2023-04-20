package com.medical_aid_system.service.impl;

import com.medical_aid_system.domain.PlanBillingCycle;
import com.medical_aid_system.repository.PlanBillingCycleRepository;
import com.medical_aid_system.service.PlanBillingCycleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlanBillingCycle}.
 */
@Service
@Transactional
public class PlanBillingCycleServiceImpl implements PlanBillingCycleService {

    private final Logger log = LoggerFactory.getLogger(PlanBillingCycleServiceImpl.class);

    private final PlanBillingCycleRepository planBillingCycleRepository;

    public PlanBillingCycleServiceImpl(PlanBillingCycleRepository planBillingCycleRepository) {
        this.planBillingCycleRepository = planBillingCycleRepository;
    }

    @Override
    public PlanBillingCycle save(PlanBillingCycle planBillingCycle) {
        log.debug("Request to save PlanBillingCycle : {}", planBillingCycle);
        return planBillingCycleRepository.save(planBillingCycle);
    }

    @Override
    public PlanBillingCycle update(PlanBillingCycle planBillingCycle) {
        log.debug("Request to update PlanBillingCycle : {}", planBillingCycle);
        return planBillingCycleRepository.save(planBillingCycle);
    }

    @Override
    public Optional<PlanBillingCycle> partialUpdate(PlanBillingCycle planBillingCycle) {
        log.debug("Request to partially update PlanBillingCycle : {}", planBillingCycle);

        return planBillingCycleRepository
            .findById(planBillingCycle.getId())
            .map(existingPlanBillingCycle -> {
                if (planBillingCycle.getPeriodUnit() != null) {
                    existingPlanBillingCycle.setPeriodUnit(planBillingCycle.getPeriodUnit());
                }
                if (planBillingCycle.getPeriodValue() != null) {
                    existingPlanBillingCycle.setPeriodValue(planBillingCycle.getPeriodValue());
                }
                if (planBillingCycle.getDateConfiguration() != null) {
                    existingPlanBillingCycle.setDateConfiguration(planBillingCycle.getDateConfiguration());
                }
                if (planBillingCycle.getBillingDate() != null) {
                    existingPlanBillingCycle.setBillingDate(planBillingCycle.getBillingDate());
                }

                return existingPlanBillingCycle;
            })
            .map(planBillingCycleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanBillingCycle> findAll(Pageable pageable) {
        log.debug("Request to get all PlanBillingCycles");
        return planBillingCycleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanBillingCycle> findOne(Long id) {
        log.debug("Request to get PlanBillingCycle : {}", id);
        return planBillingCycleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanBillingCycle : {}", id);
        planBillingCycleRepository.deleteById(id);
    }
}
