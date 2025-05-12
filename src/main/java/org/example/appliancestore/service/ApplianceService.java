package org.example.appliancestore.service;

import org.example.appliancestore.dto.ApplianceDto;
import org.example.appliancestore.model.Appliance;

import java.util.List;

public interface ApplianceService {

    ApplianceDto add(Appliance appliance);

    ApplianceDto readById(Long id);

    ApplianceDto update(Appliance appliance);

    List<ApplianceDto> getAll(Integer page, Integer size, String sort);

    void delete(Long id);

    Integer getPagesCount(Integer page, Integer size);
}
