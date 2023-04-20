package com.xplug.medical_aid_system.service.impl;

import com.xplug.medical_aid_system.domain.ContactDetails;
import com.xplug.medical_aid_system.repository.ContactDetailsRepository;
import com.xplug.medical_aid_system.service.ContactDetailsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContactDetails}.
 */
@Service
@Transactional
public class ContactDetailsServiceImpl implements ContactDetailsService {

    private final Logger log = LoggerFactory.getLogger(ContactDetailsServiceImpl.class);

    private final ContactDetailsRepository contactDetailsRepository;

    public ContactDetailsServiceImpl(ContactDetailsRepository contactDetailsRepository) {
        this.contactDetailsRepository = contactDetailsRepository;
    }

    @Override
    public ContactDetails save(ContactDetails contactDetails) {
        log.debug("Request to save ContactDetails : {}", contactDetails);
        return contactDetailsRepository.save(contactDetails);
    }

    @Override
    public ContactDetails update(ContactDetails contactDetails) {
        log.debug("Request to update ContactDetails : {}", contactDetails);
        return contactDetailsRepository.save(contactDetails);
    }

    @Override
    public Optional<ContactDetails> partialUpdate(ContactDetails contactDetails) {
        log.debug("Request to partially update ContactDetails : {}", contactDetails);

        return contactDetailsRepository
            .findById(contactDetails.getId())
            .map(existingContactDetails -> {
                if (contactDetails.getPrimaryPhoneNumber() != null) {
                    existingContactDetails.setPrimaryPhoneNumber(contactDetails.getPrimaryPhoneNumber());
                }
                if (contactDetails.getSecondaryPhoneNumber() != null) {
                    existingContactDetails.setSecondaryPhoneNumber(contactDetails.getSecondaryPhoneNumber());
                }
                if (contactDetails.getEmailAddress() != null) {
                    existingContactDetails.setEmailAddress(contactDetails.getEmailAddress());
                }
                if (contactDetails.getPhysicalAddress() != null) {
                    existingContactDetails.setPhysicalAddress(contactDetails.getPhysicalAddress());
                }
                if (contactDetails.getWhatsappNumber() != null) {
                    existingContactDetails.setWhatsappNumber(contactDetails.getWhatsappNumber());
                }

                return existingContactDetails;
            })
            .map(contactDetailsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactDetails> findAll(Pageable pageable) {
        log.debug("Request to get all ContactDetails");
        return contactDetailsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContactDetails> findOne(Long id) {
        log.debug("Request to get ContactDetails : {}", id);
        return contactDetailsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactDetails : {}", id);
        contactDetailsRepository.deleteById(id);
    }
}
