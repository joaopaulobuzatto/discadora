package com.telvendas.discadora.service.mapper;

import com.telvendas.discadora.domain.Filial;
import com.telvendas.discadora.service.dto.FilialDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Filial} and its DTO {@link FilialDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FilialMapper extends EntityMapper<FilialDTO, Filial> {
    @Named("nomeFantasia")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomeFantasia", source = "nomeFantasia")
    FilialDTO toDtoNomeFantasia(Filial filial);
}
