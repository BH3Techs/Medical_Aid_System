package com.medical_aid_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.medical_aid_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanCategory.class);
        PlanCategory planCategory1 = new PlanCategory();
        planCategory1.setId(1L);
        PlanCategory planCategory2 = new PlanCategory();
        planCategory2.setId(planCategory1.getId());
        assertThat(planCategory1).isEqualTo(planCategory2);
        planCategory2.setId(2L);
        assertThat(planCategory1).isNotEqualTo(planCategory2);
        planCategory1.setId(null);
        assertThat(planCategory1).isNotEqualTo(planCategory2);
    }
}
