package com.telvendas.discadora.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResponsavelClienteMapperTest {

    private ResponsavelClienteMapper responsavelClienteMapper;

    @BeforeEach
    public void setUp() {
        responsavelClienteMapper = new ResponsavelClienteMapperImpl();
    }
}
