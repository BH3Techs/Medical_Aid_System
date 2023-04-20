package com.xplug.medical_aid_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.xplug.medical_aid_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BenefitClaimTrackerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BenefitClaimTracker.class);
        BenefitClaimTracker benefitClaimTracker1 = new BenefitClaimTracker();
        benefitClaimTracker1.setId(1L);
        BenefitClaimTracker benefitClaimTracker2 = new BenefitClaimTracker();
        benefitClaimTracker2.setId(benefitClaimTracker1.getId());
        assertThat(benefitClaimTracker1).isEqualTo(benefitClaimTracker2);
        benefitClaimTracker2.setId(2L);
        assertThat(benefitClaimTracker1).isNotEqualTo(benefitClaimTracker2);
        benefitClaimTracker1.setId(null);
        assertThat(benefitClaimTracker1).isNotEqualTo(benefitClaimTracker2);
    }
}
