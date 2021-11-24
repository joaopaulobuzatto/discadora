package com.telvendas.discadora.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.telvendas.discadora.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponsavelClienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelCliente.class);
        ResponsavelCliente responsavelCliente1 = new ResponsavelCliente();
        responsavelCliente1.setId(1L);
        ResponsavelCliente responsavelCliente2 = new ResponsavelCliente();
        responsavelCliente2.setId(responsavelCliente1.getId());
        assertThat(responsavelCliente1).isEqualTo(responsavelCliente2);
        responsavelCliente2.setId(2L);
        assertThat(responsavelCliente1).isNotEqualTo(responsavelCliente2);
        responsavelCliente1.setId(null);
        assertThat(responsavelCliente1).isNotEqualTo(responsavelCliente2);
    }
}
