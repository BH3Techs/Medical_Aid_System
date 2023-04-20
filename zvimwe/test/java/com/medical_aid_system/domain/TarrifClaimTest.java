package com.medical_aid_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.medical_aid_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TarrifClaimTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarrifClaim.class);
        TarrifClaim tarrifClaim1 = new TarrifClaim();
        tarrifClaim1.setId(1L);
        TarrifClaim tarrifClaim2 = new TarrifClaim();
        tarrifClaim2.setId(tarrifClaim1.getId());
        assertThat(tarrifClaim1).isEqualTo(tarrifClaim2);
        tarrifClaim2.setId(2L);
        assertThat(tarrifClaim1).isNotEqualTo(tarrifClaim2);
        tarrifClaim1.setId(null);
        assertThat(tarrifClaim1).isNotEqualTo(tarrifClaim2);
    }
}
