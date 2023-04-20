package com.medical_aid_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.medical_aid_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BenefitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Benefit.class);
        Benefit benefit1 = new Benefit();
        benefit1.setId(1L);
        Benefit benefit2 = new Benefit();
        benefit2.setId(benefit1.getId());
        assertThat(benefit1).isEqualTo(benefit2);
        benefit2.setId(2L);
        assertThat(benefit1).isNotEqualTo(benefit2);
        benefit1.setId(null);
        assertThat(benefit1).isNotEqualTo(benefit2);
    }
}
