package com.xplug.medical_aid_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.xplug.medical_aid_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConditionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Condition.class);
        Condition condition1 = new Condition();
        condition1.setId(1L);
        Condition condition2 = new Condition();
        condition2.setId(condition1.getId());
        assertThat(condition1).isEqualTo(condition2);
        condition2.setId(2L);
        assertThat(condition1).isNotEqualTo(condition2);
        condition1.setId(null);
        assertThat(condition1).isNotEqualTo(condition2);
    }
}
