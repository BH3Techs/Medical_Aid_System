package com.xplug.medical_aid_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.xplug.medical_aid_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanBenefitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanBenefit.class);
        PlanBenefit planBenefit1 = new PlanBenefit();
        planBenefit1.setId(1L);
        PlanBenefit planBenefit2 = new PlanBenefit();
        planBenefit2.setId(planBenefit1.getId());
        assertThat(planBenefit1).isEqualTo(planBenefit2);
        planBenefit2.setId(2L);
        assertThat(planBenefit1).isNotEqualTo(planBenefit2);
        planBenefit1.setId(null);
        assertThat(planBenefit1).isNotEqualTo(planBenefit2);
    }
}
