package com.xplug.medical_aid_system.repository;

import com.xplug.medical_aid_system.domain.Plans;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PlansRepositoryWithBagRelationships {
    Optional<Plans> fetchBagRelationships(Optional<Plans> plans);

    List<Plans> fetchBagRelationships(List<Plans> plans);

    Page<Plans> fetchBagRelationships(Page<Plans> plans);
}
