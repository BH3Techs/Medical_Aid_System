package com.xplug.medical_aid_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.xplug.medical_aid_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanBillingCycleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanBillingCycle.class);
        PlanBillingCycle planBillingCycle1 = new PlanBillingCycle();
        planBillingCycle1.setId(1L);
        PlanBillingCycle planBillingCycle2 = new PlanBillingCycle();
        planBillingCycle2.setId(planBillingCycle1.getId());
        assertThat(planBillingCycle1).isEqualTo(planBillingCycle2);
        planBillingCycle2.setId(2L);
        assertThat(planBillingCycle1).isNotEqualTo(planBillingCycle2);
        planBillingCycle1.setId(null);
        assertThat(planBillingCycle1).isNotEqualTo(planBillingCycle2);
    }
}
