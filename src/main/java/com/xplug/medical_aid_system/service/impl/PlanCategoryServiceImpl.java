package com.xplug.medical_aid_system.service.impl;

import com.xplug.medical_aid_system.domain.PlanCategory;
import com.xplug.medical_aid_system.repository.PlanCategoryRepository;
import com.xplug.medical_aid_system.service.PlanCategoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlanCategory}.
 */
@Service
@Transactional
public class PlanCategoryServiceImpl implements PlanCategoryService {

    private final Logger log = LoggerFactory.getLogger(PlanCategoryServiceImpl.class);

    private final PlanCategoryRepository planCategoryRepository;

    public PlanCategoryServiceImpl(PlanCategoryRepository planCategoryRepository) {
        this.planCategoryRepository = planCategoryRepository;
    }

    @Override
    public PlanCategory save(PlanCategory planCategory) {
        log.debug("Request to save PlanCategory : {}", planCategory);
        return planCategoryRepository.save(planCategory);
    }

    @Override
    public PlanCategory update(PlanCategory planCategory) {
        log.debug("Request to update PlanCategory : {}", planCategory);
        return planCategoryRepository.save(planCategory);
    }

    @Override
    public Optional<PlanCategory> partialUpdate(PlanCategory planCategory) {
        log.debug("Request to partially update PlanCategory : {}", planCategory);

        return planCategoryRepository
            .findById(planCategory.getId())
            .map(existingPlanCategory -> {
                if (planCategory.getName() != null) {
                    existingPlanCategory.setName(planCategory.getName());
                }
                if (planCategory.getDescription() != null) {
                    existingPlanCategory.setDescription(planCategory.getDescription());
                }
                if (planCategory.getDateCreated() != null) {
                    existingPlanCategory.setDateCreated(planCategory.getDateCreated());
                }
                if (planCategory.getActive() != null) {
                    existingPlanCategory.setActive(planCategory.getActive());
                }

                return existingPlanCategory;
            })
            .map(planCategoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanCategory> findAll(Pageable pageable) {
        log.debug("Request to get all PlanCategories");
        return planCategoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanCategory> findOne(Long id) {
        log.debug("Request to get PlanCategory : {}", id);
        return planCategoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanCategory : {}", id);
        planCategoryRepository.deleteById(id);
    }
}
