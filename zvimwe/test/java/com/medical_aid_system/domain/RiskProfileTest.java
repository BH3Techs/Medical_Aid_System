package com.medical_aid_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.medical_aid_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RiskProfileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskProfile.class);
        RiskProfile riskProfile1 = new RiskProfile();
        riskProfile1.setId(1L);
        RiskProfile riskProfile2 = new RiskProfile();
        riskProfile2.setId(riskProfile1.getId());
        assertThat(riskProfile1).isEqualTo(riskProfile2);
        riskProfile2.setId(2L);
        assertThat(riskProfile1).isNotEqualTo(riskProfile2);
        riskProfile1.setId(null);
        assertThat(riskProfile1).isNotEqualTo(riskProfile2);
    }
}
