package com.telvendas.discadora.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinhaMovelMapperTest {

    private LinhaMovelMapper linhaMovelMapper;

    @BeforeEach
    public void setUp() {
        linhaMovelMapper = new LinhaMovelMapperImpl();
    }
}
