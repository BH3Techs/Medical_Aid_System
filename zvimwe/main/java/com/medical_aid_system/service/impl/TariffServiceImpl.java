package com.medical_aid_system.service.impl;

import com.medical_aid_system.domain.Tariff;
import com.medical_aid_system.repository.TariffRepository;
import com.medical_aid_system.service.TariffService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tariff}.
 */
@Service
@Transactional
public class TariffServiceImpl implements TariffService {

    private final Logger log = LoggerFactory.getLogger(TariffServiceImpl.class);

    private final TariffRepository tariffRepository;

    public TariffServiceImpl(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    @Override
    public Tariff save(Tariff tariff) {
        log.debug("Request to save Tariff : {}", tariff);
        return tariffRepository.save(tariff);
    }

    @Override
    public Tariff update(Tariff tariff) {
        log.debug("Request to update Tariff : {}", tariff);
        return tariffRepository.save(tariff);
    }

    @Override
    public Optional<Tariff> partialUpdate(Tariff tariff) {
        log.debug("Request to partially update Tariff : {}", tariff);

        return tariffRepository
            .findById(tariff.getId())
            .map(existingTariff -> {
                if (tariff.getName() != null) {
                    existingTariff.setName(tariff.getName());
                }
                if (tariff.getPrice() != null) {
                    existingTariff.setPrice(tariff.getPrice());
                }
                if (tariff.getActive() != null) {
                    existingTariff.setActive(tariff.getActive());
                }

                return existingTariff;
            })
            .map(tariffRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Tariff> findAll(Pageable pageable) {
        log.debug("Request to get all Tariffs");
        return tariffRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tariff> findOne(Long id) {
        log.debug("Request to get Tariff : {}", id);
        return tariffRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tariff : {}", id);
        tariffRepository.deleteById(id);
    }
}
