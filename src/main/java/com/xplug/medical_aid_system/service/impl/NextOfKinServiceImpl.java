package com.xplug.medical_aid_system.service.impl;

import com.xplug.medical_aid_system.domain.NextOfKin;
import com.xplug.medical_aid_system.repository.NextOfKinRepository;
import com.xplug.medical_aid_system.service.NextOfKinService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NextOfKin}.
 */
@Service
@Transactional
public class NextOfKinServiceImpl implements NextOfKinService {

    private final Logger log = LoggerFactory.getLogger(NextOfKinServiceImpl.class);

    private final NextOfKinRepository nextOfKinRepository;

    public NextOfKinServiceImpl(NextOfKinRepository nextOfKinRepository) {
        this.nextOfKinRepository = nextOfKinRepository;
    }

    @Override
    public NextOfKin save(NextOfKin nextOfKin) {
        log.debug("Request to save NextOfKin : {}", nextOfKin);
        return nextOfKinRepository.save(nextOfKin);
    }

    @Override
    public NextOfKin update(NextOfKin nextOfKin) {
        log.debug("Request to update NextOfKin : {}", nextOfKin);
        return nextOfKinRepository.save(nextOfKin);
    }

    @Override
    public Optional<NextOfKin> partialUpdate(NextOfKin nextOfKin) {
        log.debug("Request to partially update NextOfKin : {}", nextOfKin);

        return nextOfKinRepository
            .findById(nextOfKin.getId())
            .map(existingNextOfKin -> {
                if (nextOfKin.getName() != null) {
                    existingNextOfKin.setName(nextOfKin.getName());
                }
                if (nextOfKin.getIdentifier() != null) {
                    existingNextOfKin.setIdentifier(nextOfKin.getIdentifier());
                }

                return existingNextOfKin;
            })
            .map(nextOfKinRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NextOfKin> findAll(Pageable pageable) {
        log.debug("Request to get all NextOfKins");
        return nextOfKinRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NextOfKin> findOne(Long id) {
        log.debug("Request to get NextOfKin : {}", id);
        return nextOfKinRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NextOfKin : {}", id);
        nextOfKinRepository.deleteById(id);
    }
}
