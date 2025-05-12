package org.example.appliancestore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.appliancestore.aspect.Loggable;
import org.example.appliancestore.dto.ManufacturerDto;
import org.example.appliancestore.exception.NullEntityReferenceException;
import org.example.appliancestore.exception.PageException;
import org.example.appliancestore.mapper.ManufacturerMapper;
import org.example.appliancestore.model.Manufacturer;
import org.example.appliancestore.repository.ManufacturerRepository;
import org.example.appliancestore.service.ManufacturerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.example.appliancestore.utils.PageUtils.getPageable;

@Service
@AllArgsConstructor
@Transactional
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;

    @Loggable
    @Override
    public ManufacturerDto add(Manufacturer manufacturer) {
        if (manufacturer != null) {
            return manufacturerMapper.mapToDto(manufacturerRepository.save(manufacturer));
        }
        throw new NullEntityReferenceException("Manufacturer cannot be 'null'");
    }

    @Loggable
    @Override
    @Transactional(readOnly = true)
    public ManufacturerDto readById(Long id) {
        return manufacturerMapper.mapToDto(manufacturerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Manufacturer with id " + id + " not found")));
    }

    @Loggable
    @Override
    @Transactional
    public ManufacturerDto update(Manufacturer manufacturer) {
        if (manufacturer != null) {
            readById(manufacturer.getId());
            return manufacturerMapper.mapToDto(manufacturerRepository.save(manufacturer));
        }
        throw new NullEntityReferenceException("Manufacturer cannot be 'null'");
    }

    @Loggable
    @Override
    @Transactional(readOnly = true)
    public List<ManufacturerDto> getAll(Integer page, Integer size, String sort) {
        if (page != null && page < 0) {
            throw new PageException("Page number must not be less than 0");
        } else if (page != null && size < 1) {
            throw new PageException("Page size must not be less than one");
        }
        Pageable pageable = getPageable(page, size, sort);
        Page<Manufacturer> manufacturersPage = manufacturerRepository.findAll(pageable);
        List<ManufacturerDto> manufacturers = (List<ManufacturerDto>) manufacturerMapper.mapToDto(manufacturersPage.getContent());
        return manufacturers.isEmpty() ? new ArrayList<>() : manufacturers;
    }

    @Loggable
    @Override
    public void delete(Long id) {
        manufacturerRepository.delete(manufacturerMapper.mapToEntity(readById(id)));
    }

    @Override
    public Integer getPagesCount(Integer page, Integer size) {
        return manufacturerRepository.findAll(getPageable(page, size, null)).getTotalPages();
    }
}
