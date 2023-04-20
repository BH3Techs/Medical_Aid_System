package com.medical_aid_system.service.impl;

import com.medical_aid_system.domain.Individual;
import com.medical_aid_system.repository.IndividualRepository;
import com.medical_aid_system.service.IndividualService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Individual}.
 */
@Service
@Transactional
public class IndividualServiceImpl implements IndividualService {

    private final Logger log = LoggerFactory.getLogger(IndividualServiceImpl.class);

    private final IndividualRepository individualRepository;

    public IndividualServiceImpl(IndividualRepository individualRepository) {
        this.individualRepository = individualRepository;
    }

    @Override
    public Individual save(Individual individual) {
        log.debug("Request to save Individual : {}", individual);
        return individualRepository.save(individual);
    }

    @Override
    public Individual update(Individual individual) {
        log.debug("Request to update Individual : {}", individual);
        return individualRepository.save(individual);
    }

    @Override
    public Optional<Individual> partialUpdate(Individual individual) {
        log.debug("Request to partially update Individual : {}", individual);

        return individualRepository
            .findById(individual.getId())
            .map(existingIndividual -> {
                if (individual.getFirstName() != null) {
                    existingIndividual.setFirstName(individual.getFirstName());
                }
                if (individual.getLastName() != null) {
                    existingIndividual.setLastName(individual.getLastName());
                }
                if (individual.getInitial() != null) {
                    existingIndividual.setInitial(individual.getInitial());
                }
                if (individual.getDateOfBirth() != null) {
                    existingIndividual.setDateOfBirth(individual.getDateOfBirth());
                }
                if (individual.getGender() != null) {
                    existingIndividual.setGender(individual.getGender());
                }
                if (individual.getNationalId() != null) {
                    existingIndividual.setNationalId(individual.getNationalId());
                }

                return existingIndividual;
            })
            .map(individualRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Individual> findAll(Pageable pageable) {
        log.debug("Request to get all Individuals");
        return individualRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Individual> findOne(Long id) {
        log.debug("Request to get Individual : {}", id);
        return individualRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Individual : {}", id);
        individualRepository.deleteById(id);
    }
}
