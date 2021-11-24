package com.telvendas.discadora.service.mapper;

import com.telvendas.discadora.domain.Fatura;
import com.telvendas.discadora.service.dto.FaturaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fatura} and its DTO {@link FaturaDTO}.
 */
@Mapper(componentModel = "spring", uses = { ClienteMapper.class })
public interface FaturaMapper extends EntityMapper<FaturaDTO, Fatura> {
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "cnpj")
    FaturaDTO toDto(Fatura s);
}
