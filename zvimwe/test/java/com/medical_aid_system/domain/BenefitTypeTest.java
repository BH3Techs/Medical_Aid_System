package com.medical_aid_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.medical_aid_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BenefitTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BenefitType.class);
        BenefitType benefitType1 = new BenefitType();
        benefitType1.setId(1L);
        BenefitType benefitType2 = new BenefitType();
        benefitType2.setId(benefitType1.getId());
        assertThat(benefitType1).isEqualTo(benefitType2);
        benefitType2.setId(2L);
        assertThat(benefitType1).isNotEqualTo(benefitType2);
        benefitType1.setId(null);
        assertThat(benefitType1).isNotEqualTo(benefitType2);
    }
}
