package com.telvendas.discadora.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.telvendas.discadora.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResponsavelClienteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsavelClienteDTO.class);
        ResponsavelClienteDTO responsavelClienteDTO1 = new ResponsavelClienteDTO();
        responsavelClienteDTO1.setId(1L);
        ResponsavelClienteDTO responsavelClienteDTO2 = new ResponsavelClienteDTO();
        assertThat(responsavelClienteDTO1).isNotEqualTo(responsavelClienteDTO2);
        responsavelClienteDTO2.setId(responsavelClienteDTO1.getId());
        assertThat(responsavelClienteDTO1).isEqualTo(responsavelClienteDTO2);
        responsavelClienteDTO2.setId(2L);
        assertThat(responsavelClienteDTO1).isNotEqualTo(responsavelClienteDTO2);
        responsavelClienteDTO1.setId(null);
        assertThat(responsavelClienteDTO1).isNotEqualTo(responsavelClienteDTO2);
    }
}
