package com.telvendas.discadora.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.telvendas.discadora.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FaturaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fatura.class);
        Fatura fatura1 = new Fatura();
        fatura1.setId(1L);
        Fatura fatura2 = new Fatura();
        fatura2.setId(fatura1.getId());
        assertThat(fatura1).isEqualTo(fatura2);
        fatura2.setId(2L);
        assertThat(fatura1).isNotEqualTo(fatura2);
        fatura1.setId(null);
        assertThat(fatura1).isNotEqualTo(fatura2);
    }
}
