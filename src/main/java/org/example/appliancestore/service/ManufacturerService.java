package org.example.appliancestore.service;


import org.example.appliancestore.dto.ManufacturerDto;
import org.example.appliancestore.model.Manufacturer;

import java.util.List;


public interface ManufacturerService {

    ManufacturerDto add(Manufacturer manufacturer);

    ManufacturerDto readById(Long id);

    ManufacturerDto update(Manufacturer manufacturer);

    List<ManufacturerDto> getAll(Integer page, Integer size, String sort);

    void delete(Long id);

    Integer getPagesCount(Integer page, Integer size);
}
