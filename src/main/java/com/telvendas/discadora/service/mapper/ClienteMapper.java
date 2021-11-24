package com.telvendas.discadora.service.mapper;

import com.telvendas.discadora.domain.Cliente;
import com.telvendas.discadora.service.dto.ClienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, FilialMapper.class })
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {
    @Mapping(target = "consultor", source = "consultor", qualifiedByName = "login")
    @Mapping(target = "supervisor", source = "supervisor", qualifiedByName = "login")
    @Mapping(target = "filial", source = "filial", qualifiedByName = "nomeFantasia")
    ClienteDTO toDto(Cliente s);

    @Named("cnpj")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "cnpj", source = "cnpj")
    ClienteDTO toDtoCnpj(Cliente cliente);
}
