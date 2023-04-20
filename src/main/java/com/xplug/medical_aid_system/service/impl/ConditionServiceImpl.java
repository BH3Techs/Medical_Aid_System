package com.xplug.medical_aid_system.service.impl;

import com.xplug.medical_aid_system.domain.Condition;
import com.xplug.medical_aid_system.repository.ConditionRepository;
import com.xplug.medical_aid_system.service.ConditionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Condition}.
 */
@Service
@Transactional
public class ConditionServiceImpl implements ConditionService {

    private final Logger log = LoggerFactory.getLogger(ConditionServiceImpl.class);

    private final ConditionRepository conditionRepository;

    public ConditionServiceImpl(ConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
    }

    @Override
    public Condition save(Condition condition) {
        log.debug("Request to save Condition : {}", condition);
        return conditionRepository.save(condition);
    }

    @Override
    public Condition update(Condition condition) {
        log.debug("Request to update Condition : {}", condition);
        return conditionRepository.save(condition);
    }

    @Override
    public Optional<Condition> partialUpdate(Condition condition) {
        log.debug("Request to partially update Condition : {}", condition);

        return conditionRepository
            .findById(condition.getId())
            .map(existingCondition -> {
                if (condition.getName() != null) {
                    existingCondition.setName(condition.getName());
                }
                if (condition.getDetails() != null) {
                    existingCondition.setDetails(condition.getDetails());
                }

                return existingCondition;
            })
            .map(conditionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Condition> findAll(Pageable pageable) {
        log.debug("Request to get all Conditions");
        return conditionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Condition> findOne(Long id) {
        log.debug("Request to get Condition : {}", id);
        return conditionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Condition : {}", id);
        conditionRepository.deleteById(id);
    }
}
