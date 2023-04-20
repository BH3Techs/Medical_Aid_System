package com.xplug.medical_aid_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.xplug.medical_aid_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlansTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plans.class);
        Plans plans1 = new Plans();
        plans1.setId(1L);
        Plans plans2 = new Plans();
        plans2.setId(plans1.getId());
        assertThat(plans1).isEqualTo(plans2);
        plans2.setId(2L);
        assertThat(plans1).isNotEqualTo(plans2);
        plans1.setId(null);
        assertThat(plans1).isNotEqualTo(plans2);
    }
}
