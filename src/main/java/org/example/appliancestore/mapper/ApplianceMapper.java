package org.example.appliancestore.mapper;

import org.example.appliancestore.dto.ApplianceDto;
import org.example.appliancestore.model.Appliance;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class ApplianceMapper implements Mapper<Appliance, ApplianceDto> {
    @Override
    public ApplianceDto mapToDto(Appliance appliance) {
        ApplianceDto applianceDto =  new ApplianceDto();
        applianceDto.setId(appliance.getId());
        applianceDto.setName(appliance.getName());
        applianceDto.setCategory(appliance.getCategory());
        applianceDto.setModel(appliance.getModel());
        applianceDto.setManufacturer(appliance.getManufacturer());
        applianceDto.setCharacteristic(appliance.getCharacteristic());
        applianceDto.setDescription(appliance.getDescription());
        applianceDto.setPower(appliance.getPower());
        applianceDto.setPrice(appliance.getPrice());
        return applianceDto;
    }

    @Override
    public Appliance mapToEntity(ApplianceDto applianceDto) {
        return new Appliance(
                applianceDto.getId(),
                applianceDto.getName(),
                applianceDto.getCategory(),
                applianceDto.getModel(),
                applianceDto.getManufacturer(),
                applianceDto.getPowerType(),
                applianceDto.getCharacteristic(),
                applianceDto.getDescription(),
                applianceDto.getPower(),
                applianceDto.getPrice()
        );
    }

    @Override
    public Collection<ApplianceDto> mapToDto(Collection<Appliance> appliances) {
        if (appliances == null) {
            return new ArrayList<>();
        }
        return appliances.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public Collection<Appliance> mapToEntity(Collection<ApplianceDto> applianceDtos) {
        return null;
    }


}
