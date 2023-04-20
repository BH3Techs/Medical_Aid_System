package com.xplug.medical_aid_system.service.impl;

import com.xplug.medical_aid_system.domain.RiskProfile;
import com.xplug.medical_aid_system.repository.RiskProfileRepository;
import com.xplug.medical_aid_system.service.RiskProfileService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RiskProfile}.
 */
@Service
@Transactional
public class RiskProfileServiceImpl implements RiskProfileService {

    private final Logger log = LoggerFactory.getLogger(RiskProfileServiceImpl.class);

    private final RiskProfileRepository riskProfileRepository;

    public RiskProfileServiceImpl(RiskProfileRepository riskProfileRepository) {
        this.riskProfileRepository = riskProfileRepository;
    }

    @Override
    public RiskProfile save(RiskProfile riskProfile) {
        log.debug("Request to save RiskProfile : {}", riskProfile);
        return riskProfileRepository.save(riskProfile);
    }

    @Override
    public RiskProfile update(RiskProfile riskProfile) {
        log.debug("Request to update RiskProfile : {}", riskProfile);
        return riskProfileRepository.save(riskProfile);
    }

    @Override
    public Optional<RiskProfile> partialUpdate(RiskProfile riskProfile) {
        log.debug("Request to partially update RiskProfile : {}", riskProfile);

        return riskProfileRepository
            .findById(riskProfile.getId())
            .map(existingRiskProfile -> {
                if (riskProfile.getTotalRiskScore() != null) {
                    existingRiskProfile.setTotalRiskScore(riskProfile.getTotalRiskScore());
                }
                if (riskProfile.getLifeStyle() != null) {
                    existingRiskProfile.setLifeStyle(riskProfile.getLifeStyle());
                }

                return existingRiskProfile;
            })
            .map(riskProfileRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RiskProfile> findAll(Pageable pageable) {
        log.debug("Request to get all RiskProfiles");
        return riskProfileRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RiskProfile> findOne(Long id) {
        log.debug("Request to get RiskProfile : {}", id);
        return riskProfileRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RiskProfile : {}", id);
        riskProfileRepository.deleteById(id);
    }
}
