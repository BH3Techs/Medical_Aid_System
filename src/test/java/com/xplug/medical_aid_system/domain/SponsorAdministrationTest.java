package com.xplug.medical_aid_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.xplug.medical_aid_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SponsorAdministrationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SponsorAdministration.class);
        SponsorAdministration sponsorAdministration1 = new SponsorAdministration();
        sponsorAdministration1.setId(1L);
        SponsorAdministration sponsorAdministration2 = new SponsorAdministration();
        sponsorAdministration2.setId(sponsorAdministration1.getId());
        assertThat(sponsorAdministration1).isEqualTo(sponsorAdministration2);
        sponsorAdministration2.setId(2L);
        assertThat(sponsorAdministration1).isNotEqualTo(sponsorAdministration2);
        sponsorAdministration1.setId(null);
        assertThat(sponsorAdministration1).isNotEqualTo(sponsorAdministration2);
    }
}
