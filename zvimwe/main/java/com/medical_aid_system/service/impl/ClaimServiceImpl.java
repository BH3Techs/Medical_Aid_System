package com.medical_aid_system.service.impl;

import com.medical_aid_system.domain.Claim;
import com.medical_aid_system.repository.ClaimRepository;
import com.medical_aid_system.service.ClaimService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Claim}.
 */
@Service
@Transactional
public class ClaimServiceImpl implements ClaimService {

    private final Logger log = LoggerFactory.getLogger(ClaimServiceImpl.class);

    private final ClaimRepository claimRepository;

    public ClaimServiceImpl(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    @Override
    public Claim save(Claim claim) {
        log.debug("Request to save Claim : {}", claim);
        return claimRepository.save(claim);
    }

    @Override
    public Claim update(Claim claim) {
        log.debug("Request to update Claim : {}", claim);
        return claimRepository.save(claim);
    }

    @Override
    public Optional<Claim> partialUpdate(Claim claim) {
        log.debug("Request to partially update Claim : {}", claim);

        return claimRepository
            .findById(claim.getId())
            .map(existingClaim -> {
                if (claim.getSubmissionDate() != null) {
                    existingClaim.setSubmissionDate(claim.getSubmissionDate());
                }
                if (claim.getApprovalDate() != null) {
                    existingClaim.setApprovalDate(claim.getApprovalDate());
                }
                if (claim.getProcessingDate() != null) {
                    existingClaim.setProcessingDate(claim.getProcessingDate());
                }
                if (claim.getClaimStatus() != null) {
                    existingClaim.setClaimStatus(claim.getClaimStatus());
                }
                if (claim.getDiagnosis() != null) {
                    existingClaim.setDiagnosis(claim.getDiagnosis());
                }
                if (claim.getClaimant() != null) {
                    existingClaim.setClaimant(claim.getClaimant());
                }
                if (claim.getRelationshipToMember() != null) {
                    existingClaim.setRelationshipToMember(claim.getRelationshipToMember());
                }

                return existingClaim;
            })
            .map(claimRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Claim> findAll(Pageable pageable) {
        log.debug("Request to get all Claims");
        return claimRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Claim> findOne(Long id) {
        log.debug("Request to get Claim : {}", id);
        return claimRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Claim : {}", id);
        claimRepository.deleteById(id);
    }
}
