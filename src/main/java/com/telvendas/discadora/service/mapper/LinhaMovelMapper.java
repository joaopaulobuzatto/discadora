package com.telvendas.discadora.service.mapper;

import com.telvendas.discadora.domain.LinhaMovel;
import com.telvendas.discadora.service.dto.LinhaMovelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LinhaMovel} and its DTO {@link LinhaMovelDTO}.
 */
@Mapper(componentModel = "spring", uses = { ClienteMapper.class })
public interface LinhaMovelMapper extends EntityMapper<LinhaMovelDTO, LinhaMovel> {
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "cnpj")
    LinhaMovelDTO toDto(LinhaMovel s);
}
