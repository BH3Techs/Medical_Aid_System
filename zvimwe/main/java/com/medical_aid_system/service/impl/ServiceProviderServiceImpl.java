package com.medical_aid_system.service.impl;

import com.medical_aid_system.domain.ServiceProvider;
import com.medical_aid_system.repository.ServiceProviderRepository;
import com.medical_aid_system.service.ServiceProviderService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ServiceProvider}.
 */
@Service
@Transactional
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private final Logger log = LoggerFactory.getLogger(ServiceProviderServiceImpl.class);

    private final ServiceProviderRepository serviceProviderRepository;

    public ServiceProviderServiceImpl(ServiceProviderRepository serviceProviderRepository) {
        this.serviceProviderRepository = serviceProviderRepository;
    }

    @Override
    public ServiceProvider save(ServiceProvider serviceProvider) {
        log.debug("Request to save ServiceProvider : {}", serviceProvider);
        return serviceProviderRepository.save(serviceProvider);
    }

    @Override
    public ServiceProvider update(ServiceProvider serviceProvider) {
        log.debug("Request to update ServiceProvider : {}", serviceProvider);
        return serviceProviderRepository.save(serviceProvider);
    }

    @Override
    public Optional<ServiceProvider> partialUpdate(ServiceProvider serviceProvider) {
        log.debug("Request to partially update ServiceProvider : {}", serviceProvider);

        return serviceProviderRepository
            .findById(serviceProvider.getId())
            .map(existingServiceProvider -> {
                if (serviceProvider.getName() != null) {
                    existingServiceProvider.setName(serviceProvider.getName());
                }
                if (serviceProvider.getaHFOZNumber() != null) {
                    existingServiceProvider.setaHFOZNumber(serviceProvider.getaHFOZNumber());
                }
                if (serviceProvider.getDescription() != null) {
                    existingServiceProvider.setDescription(serviceProvider.getDescription());
                }

                return existingServiceProvider;
            })
            .map(serviceProviderRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceProvider> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceProviders");
        return serviceProviderRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceProvider> findOne(Long id) {
        log.debug("Request to get ServiceProvider : {}", id);
        return serviceProviderRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceProvider : {}", id);
        serviceProviderRepository.deleteById(id);
    }
}
