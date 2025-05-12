package org.example.appliancestore.mapper;

import org.example.appliancestore.dto.ManufacturerDto;
import org.example.appliancestore.model.Manufacturer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class ManufacturerMapper implements Mapper<Manufacturer, ManufacturerDto> {
    @Override
    public ManufacturerDto mapToDto(Manufacturer manufacturer) {
        return new ManufacturerDto(
                manufacturer.getId(),
                manufacturer.getName()
        );
    }

    @Override
    public Manufacturer mapToEntity(ManufacturerDto manufacturerDto) {
        return new Manufacturer(
                manufacturerDto.getId(),
                manufacturerDto.getName()
        );
    }

    @Override
    public Collection<ManufacturerDto> mapToDto(Collection<Manufacturer> manufacturers) {
        if (manufacturers == null) {
            return new ArrayList<>();
        }
        return manufacturers.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public Collection<Manufacturer> mapToEntity(Collection<ManufacturerDto> manufacturerDtos) {
        if (manufacturerDtos == null) {
            return new ArrayList<>();
        }
        return manufacturerDtos.stream()
                .map(this::mapToEntity)
                .toList();
    }
}
