package com.xplug.medical_aid_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.xplug.medical_aid_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BenefitLimitTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BenefitLimitType.class);
        BenefitLimitType benefitLimitType1 = new BenefitLimitType();
        benefitLimitType1.setId(1L);
        BenefitLimitType benefitLimitType2 = new BenefitLimitType();
        benefitLimitType2.setId(benefitLimitType1.getId());
        assertThat(benefitLimitType1).isEqualTo(benefitLimitType2);
        benefitLimitType2.setId(2L);
        assertThat(benefitLimitType1).isNotEqualTo(benefitLimitType2);
        benefitLimitType1.setId(null);
        assertThat(benefitLimitType1).isNotEqualTo(benefitLimitType2);
    }
}
