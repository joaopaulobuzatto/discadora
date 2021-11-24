package com.telvendas.discadora.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.telvendas.discadora.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LinhaMovelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinhaMovel.class);
        LinhaMovel linhaMovel1 = new LinhaMovel();
        linhaMovel1.setId(1L);
        LinhaMovel linhaMovel2 = new LinhaMovel();
        linhaMovel2.setId(linhaMovel1.getId());
        assertThat(linhaMovel1).isEqualTo(linhaMovel2);
        linhaMovel2.setId(2L);
        assertThat(linhaMovel1).isNotEqualTo(linhaMovel2);
        linhaMovel1.setId(null);
        assertThat(linhaMovel1).isNotEqualTo(linhaMovel2);
    }
}
