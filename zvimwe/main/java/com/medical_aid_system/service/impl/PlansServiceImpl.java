package com.medical_aid_system.service.impl;

import com.medical_aid_system.domain.Plans;
import com.medical_aid_system.repository.PlansRepository;
import com.medical_aid_system.service.PlansService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Plans}.
 */
@Service
@Transactional
public class PlansServiceImpl implements PlansService {

    private final Logger log = LoggerFactory.getLogger(PlansServiceImpl.class);

    private final PlansRepository plansRepository;

    public PlansServiceImpl(PlansRepository plansRepository) {
        this.plansRepository = plansRepository;
    }

    @Override
    public Plans save(Plans plans) {
        log.debug("Request to save Plans : {}", plans);
        return plansRepository.save(plans);
    }

    @Override
    public Plans update(Plans plans) {
        log.debug("Request to update Plans : {}", plans);
        return plansRepository.save(plans);
    }

    @Override
    public Optional<Plans> partialUpdate(Plans plans) {
        log.debug("Request to partially update Plans : {}", plans);

        return plansRepository
            .findById(plans.getId())
            .map(existingPlans -> {
                if (plans.getPlanCode() != null) {
                    existingPlans.setPlanCode(plans.getPlanCode());
                }
                if (plans.getName() != null) {
                    existingPlans.setName(plans.getName());
                }
                if (plans.getBasePremium() != null) {
                    existingPlans.setBasePremium(plans.getBasePremium());
                }
                if (plans.getCoverAmount() != null) {
                    existingPlans.setCoverAmount(plans.getCoverAmount());
                }
                if (plans.getCoverPeriodUnit() != null) {
                    existingPlans.setCoverPeriodUnit(plans.getCoverPeriodUnit());
                }
                if (plans.getCoverPeriodValue() != null) {
                    existingPlans.setCoverPeriodValue(plans.getCoverPeriodValue());
                }
                if (plans.getActive() != null) {
                    existingPlans.setActive(plans.getActive());
                }

                return existingPlans;
            })
            .map(plansRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Plans> findAll(Pageable pageable) {
        log.debug("Request to get all Plans");
        return plansRepository.findAll(pageable);
    }

    public Page<Plans> findAllWithEagerRelationships(Pageable pageable) {
        return plansRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Plans> findOne(Long id) {
        log.debug("Request to get Plans : {}", id);
        return plansRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plans : {}", id);
        plansRepository.deleteById(id);
    }
}
