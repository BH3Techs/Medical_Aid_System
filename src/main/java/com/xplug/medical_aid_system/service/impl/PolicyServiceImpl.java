package com.xplug.medical_aid_system.service.impl;

import com.xplug.medical_aid_system.domain.Policy;
import com.xplug.medical_aid_system.repository.PolicyRepository;
import com.xplug.medical_aid_system.service.PolicyService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Policy}.
 */
@Service
@Transactional
public class PolicyServiceImpl implements PolicyService {

    private final Logger log = LoggerFactory.getLogger(PolicyServiceImpl.class);

    private final PolicyRepository policyRepository;

    public PolicyServiceImpl(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    public Policy save(Policy policy) {
        log.debug("Request to save Policy : {}", policy);
        return policyRepository.save(policy);
    }

    @Override
    public Policy update(Policy policy) {
        log.debug("Request to update Policy : {}", policy);
        return policyRepository.save(policy);
    }

    @Override
    public Optional<Policy> partialUpdate(Policy policy) {
        log.debug("Request to partially update Policy : {}", policy);

        return policyRepository
            .findById(policy.getId())
            .map(existingPolicy -> {
                if (policy.getPolicyNumber() != null) {
                    existingPolicy.setPolicyNumber(policy.getPolicyNumber());
                }
                if (policy.getSuffix() != null) {
                    existingPolicy.setSuffix(policy.getSuffix());
                }
                if (policy.getPricingGroup() != null) {
                    existingPolicy.setPricingGroup(policy.getPricingGroup());
                }
                if (policy.getNextOfKin() != null) {
                    existingPolicy.setNextOfKin(policy.getNextOfKin());
                }
                if (policy.getMemberIdentifier() != null) {
                    existingPolicy.setMemberIdentifier(policy.getMemberIdentifier());
                }
                if (policy.getParentPolicy() != null) {
                    existingPolicy.setParentPolicy(policy.getParentPolicy());
                }
                if (policy.getSponsorIdentifier() != null) {
                    existingPolicy.setSponsorIdentifier(policy.getSponsorIdentifier());
                }
                if (policy.getSponsorType() != null) {
                    existingPolicy.setSponsorType(policy.getSponsorType());
                }
                if (policy.getStatus() != null) {
                    existingPolicy.setStatus(policy.getStatus());
                }
                if (policy.getBalance() != null) {
                    existingPolicy.setBalance(policy.getBalance());
                }

                return existingPolicy;
            })
            .map(policyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Policy> findAll(Pageable pageable) {
        log.debug("Request to get all Policies");
        return policyRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Policy> findOne(Long id) {
        log.debug("Request to get Policy : {}", id);
        return policyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Policy : {}", id);
        policyRepository.deleteById(id);
    }
}
