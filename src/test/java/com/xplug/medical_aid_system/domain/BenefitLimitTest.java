package com.xplug.medical_aid_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.xplug.medical_aid_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BenefitLimitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BenefitLimit.class);
        BenefitLimit benefitLimit1 = new BenefitLimit();
        benefitLimit1.setId(1L);
        BenefitLimit benefitLimit2 = new BenefitLimit();
        benefitLimit2.setId(benefitLimit1.getId());
        assertThat(benefitLimit1).isEqualTo(benefitLimit2);
        benefitLimit2.setId(2L);
        assertThat(benefitLimit1).isNotEqualTo(benefitLimit2);
        benefitLimit1.setId(null);
        assertThat(benefitLimit1).isNotEqualTo(benefitLimit2);
    }
}
