package com.xplug.medical_aid_system.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.xplug.medical_aid_system.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankingDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankingDetails.class);
        BankingDetails bankingDetails1 = new BankingDetails();
        bankingDetails1.setId(1L);
        BankingDetails bankingDetails2 = new BankingDetails();
        bankingDetails2.setId(bankingDetails1.getId());
        assertThat(bankingDetails1).isEqualTo(bankingDetails2);
        bankingDetails2.setId(2L);
        assertThat(bankingDetails1).isNotEqualTo(bankingDetails2);
        bankingDetails1.setId(null);
        assertThat(bankingDetails1).isNotEqualTo(bankingDetails2);
    }
}
