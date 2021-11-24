package com.telvendas.discadora.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.telvendas.discadora.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LinhaMovelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinhaMovelDTO.class);
        LinhaMovelDTO linhaMovelDTO1 = new LinhaMovelDTO();
        linhaMovelDTO1.setId(1L);
        LinhaMovelDTO linhaMovelDTO2 = new LinhaMovelDTO();
        assertThat(linhaMovelDTO1).isNotEqualTo(linhaMovelDTO2);
        linhaMovelDTO2.setId(linhaMovelDTO1.getId());
        assertThat(linhaMovelDTO1).isEqualTo(linhaMovelDTO2);
        linhaMovelDTO2.setId(2L);
        assertThat(linhaMovelDTO1).isNotEqualTo(linhaMovelDTO2);
        linhaMovelDTO1.setId(null);
        assertThat(linhaMovelDTO1).isNotEqualTo(linhaMovelDTO2);
    }
}
