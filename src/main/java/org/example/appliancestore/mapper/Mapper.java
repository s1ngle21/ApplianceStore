package org.example.appliancestore.mapper;

import java.util.Collection;


public interface Mapper<ENTITY, DTO> {

    DTO mapToDto(ENTITY entity);

    ENTITY mapToEntity(DTO dto);

    Collection<DTO> mapToDto(Collection<ENTITY> entities);

    Collection<ENTITY> mapToEntity(Collection<DTO> dtos);

    //<COLLECTION extends Collection<ENTITY>> Collection<DTO> mapToDto(COLLECTION entities);
}
