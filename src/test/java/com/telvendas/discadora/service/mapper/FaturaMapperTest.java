package com.telvendas.discadora.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FaturaMapperTest {

    private FaturaMapper faturaMapper;

    @BeforeEach
    public void setUp() {
        faturaMapper = new FaturaMapperImpl();
    }
}
