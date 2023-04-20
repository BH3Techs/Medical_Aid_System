package com.xplug.medical_aid_system.service.impl;

import com.xplug.medical_aid_system.domain.SponsorAdministration;
import com.xplug.medical_aid_system.repository.SponsorAdministrationRepository;
import com.xplug.medical_aid_system.service.SponsorAdministrationService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SponsorAdministration}.
 */
@Service
@Transactional
public class SponsorAdministrationServiceImpl implements SponsorAdministrationService {

    private final Logger log = LoggerFactory.getLogger(SponsorAdministrationServiceImpl.class);

    private final SponsorAdministrationRepository sponsorAdministrationRepository;

    public SponsorAdministrationServiceImpl(SponsorAdministrationRepository sponsorAdministrationRepository) {
        this.sponsorAdministrationRepository = sponsorAdministrationRepository;
    }

    @Override
    public SponsorAdministration save(SponsorAdministration sponsorAdministration) {
        log.debug("Request to save SponsorAdministration : {}", sponsorAdministration);
        return sponsorAdministrationRepository.save(sponsorAdministration);
    }

    @Override
    public SponsorAdministration update(SponsorAdministration sponsorAdministration) {
        log.debug("Request to update SponsorAdministration : {}", sponsorAdministration);
        return sponsorAdministrationRepository.save(sponsorAdministration);
    }

    @Override
    public Optional<SponsorAdministration> partialUpdate(SponsorAdministration sponsorAdministration) {
        log.debug("Request to partially update SponsorAdministration : {}", sponsorAdministration);

        return sponsorAdministrationRepository
            .findById(sponsorAdministration.getId())
            .map(existingSponsorAdministration -> {
                if (sponsorAdministration.getFirstName() != null) {
                    existingSponsorAdministration.setFirstName(sponsorAdministration.getFirstName());
                }
                if (sponsorAdministration.getLastName() != null) {
                    existingSponsorAdministration.setLastName(sponsorAdministration.getLastName());
                }
                if (sponsorAdministration.getInitial() != null) {
                    existingSponsorAdministration.setInitial(sponsorAdministration.getInitial());
                }
                if (sponsorAdministration.getDateOfBirth() != null) {
                    existingSponsorAdministration.setDateOfBirth(sponsorAdministration.getDateOfBirth());
                }
                if (sponsorAdministration.getSponsorId() != null) {
                    existingSponsorAdministration.setSponsorId(sponsorAdministration.getSponsorId());
                }
                if (sponsorAdministration.getSponsorType() != null) {
                    existingSponsorAdministration.setSponsorType(sponsorAdministration.getSponsorType());
                }

                return existingSponsorAdministration;
            })
            .map(sponsorAdministrationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SponsorAdministration> findAll(Pageable pageable) {
        log.debug("Request to get all SponsorAdministrations");
        return sponsorAdministrationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SponsorAdministration> findOne(Long id) {
        log.debug("Request to get SponsorAdministration : {}", id);
        return sponsorAdministrationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SponsorAdministration : {}", id);
        sponsorAdministrationRepository.deleteById(id);
    }
}
