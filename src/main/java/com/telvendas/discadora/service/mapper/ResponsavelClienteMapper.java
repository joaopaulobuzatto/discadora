package com.telvendas.discadora.service.mapper;

import com.telvendas.discadora.domain.ResponsavelCliente;
import com.telvendas.discadora.service.dto.ResponsavelClienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResponsavelCliente} and its DTO {@link ResponsavelClienteDTO}.
 */
@Mapper(componentModel = "spring", uses = { ClienteMapper.class })
public interface ResponsavelClienteMapper extends EntityMapper<ResponsavelClienteDTO, ResponsavelCliente> {
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "cnpj")
    ResponsavelClienteDTO toDto(ResponsavelCliente s);
}
