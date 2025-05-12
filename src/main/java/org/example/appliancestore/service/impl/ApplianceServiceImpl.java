package org.example.appliancestore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.appliancestore.aspect.Loggable;
import org.example.appliancestore.dto.ApplianceDto;
import org.example.appliancestore.exception.NullEntityReferenceException;
import org.example.appliancestore.exception.PageException;
import org.example.appliancestore.mapper.ApplianceMapper;
import org.example.appliancestore.model.Appliance;
import org.example.appliancestore.repository.ApplianceRepository;
import org.example.appliancestore.service.ApplianceService;
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
public class ApplianceServiceImpl implements ApplianceService {

    private final ApplianceRepository applianceRepository;
    private final ApplianceMapper applianceMapper;

    @Loggable
    @Override
    public ApplianceDto add(Appliance appliance) {
        if (appliance != null) {
            return applianceMapper.mapToDto(applianceRepository.save(appliance));
        }
        throw new NullEntityReferenceException("Appliance cannot be 'null'");
    }

    @Loggable
    @Override
    @Transactional(readOnly = true)
    public ApplianceDto readById(Long id) {
        return applianceMapper.mapToDto(applianceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Appliance with id " + id + " not found")));
    }

    @Loggable
    @Override
    public ApplianceDto update(Appliance appliance) {
        if (appliance != null) {
            readById(appliance.getId());
            return applianceMapper.mapToDto(applianceRepository.save(appliance));
        }
        throw new NullEntityReferenceException("Appliance cannot be 'null'");
    }

    @Loggable
    @Override
    @Transactional(readOnly = true)
    public List<ApplianceDto> getAll(Integer page, Integer size, String sort) {
        if (page != null && page < 0) {
            throw new PageException("Page number must not be less than 0");
        } else if (page != null && size < 1) {
            throw new PageException("Page size must not be less than one");
        }
        Pageable pageable = getPageable(page, size, sort);
        Page<Appliance> appliancePage = applianceRepository.findAll(pageable);
        List<ApplianceDto> appliances = (List<ApplianceDto>) applianceMapper.mapToDto(appliancePage.getContent());
        return appliances.isEmpty() ? new ArrayList<>() : appliances;
    }

    @Loggable
    @Override
    public void delete(Long id) {
        applianceRepository.delete(applianceMapper.mapToEntity(readById(id)));
    }

    @Override
    public Integer getPagesCount(Integer page, Integer size) {
        return applianceRepository.findAll(getPageable(page, size, null))
                .getTotalPages();
    }
}

