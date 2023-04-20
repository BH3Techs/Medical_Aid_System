package com.xplug.medical_aid_system.service.impl;

import com.xplug.medical_aid_system.domain.TarrifClaim;
import com.xplug.medical_aid_system.repository.TarrifClaimRepository;
import com.xplug.medical_aid_system.service.TarrifClaimService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TarrifClaim}.
 */
@Service
@Transactional
public class TarrifClaimServiceImpl implements TarrifClaimService {

    private final Logger log = LoggerFactory.getLogger(TarrifClaimServiceImpl.class);

    private final TarrifClaimRepository tarrifClaimRepository;

    public TarrifClaimServiceImpl(TarrifClaimRepository tarrifClaimRepository) {
        this.tarrifClaimRepository = tarrifClaimRepository;
    }

    @Override
    public TarrifClaim save(TarrifClaim tarrifClaim) {
        log.debug("Request to save TarrifClaim : {}", tarrifClaim);
        return tarrifClaimRepository.save(tarrifClaim);
    }

    @Override
    public TarrifClaim update(TarrifClaim tarrifClaim) {
        log.debug("Request to update TarrifClaim : {}", tarrifClaim);
        return tarrifClaimRepository.save(tarrifClaim);
    }

    @Override
    public Optional<TarrifClaim> partialUpdate(TarrifClaim tarrifClaim) {
        log.debug("Request to partially update TarrifClaim : {}", tarrifClaim);

        return tarrifClaimRepository
            .findById(tarrifClaim.getId())
            .map(existingTarrifClaim -> {
                if (tarrifClaim.getTarrifCode() != null) {
                    existingTarrifClaim.setTarrifCode(tarrifClaim.getTarrifCode());
                }
                if (tarrifClaim.getQuantity() != null) {
                    existingTarrifClaim.setQuantity(tarrifClaim.getQuantity());
                }
                if (tarrifClaim.getAmount() != null) {
                    existingTarrifClaim.setAmount(tarrifClaim.getAmount());
                }
                if (tarrifClaim.getDescription() != null) {
                    existingTarrifClaim.setDescription(tarrifClaim.getDescription());
                }

                return existingTarrifClaim;
            })
            .map(tarrifClaimRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TarrifClaim> findAll(Pageable pageable) {
        log.debug("Request to get all TarrifClaims");
        return tarrifClaimRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TarrifClaim> findOne(Long id) {
        log.debug("Request to get TarrifClaim : {}", id);
        return tarrifClaimRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TarrifClaim : {}", id);
        tarrifClaimRepository.deleteById(id);
    }
}
