package org.example.appliancestore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.appliancestore.aspect.Loggable;
import org.example.appliancestore.dto.EmployeeDto;
import org.example.appliancestore.exception.NullEntityReferenceException;
import org.example.appliancestore.exception.PageException;
import org.example.appliancestore.mapper.EmployeeMapper;
import org.example.appliancestore.model.Employee;
import org.example.appliancestore.repository.EmployeeRepository;
import org.example.appliancestore.service.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    
    @Loggable
    @Override
    public EmployeeDto add(Employee employee) {
        if (employee != null) {
            return employeeMapper.mapToDto(employeeRepository.save(employee));
        }
        throw new NullEntityReferenceException("Employee cannot be 'null'");
    }

    @Loggable
    @Override
    @Transactional(readOnly = true)
    public EmployeeDto readById(Long id) {
        return employeeMapper.mapToDto(employeeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Employee with id " + id + " not found")));
    }

    @Loggable
    @Override
    public EmployeeDto update(Employee employee) {
        if (employee != null) {
            readById(employee.getId());
            return employeeMapper.mapToDto(employeeRepository.save(employee));
        }
        throw new NullEntityReferenceException("Employee cannot be 'null'");
    }

    @Loggable
    @Override
    public List<EmployeeDto> getAll(Integer page, Integer size, String sort) {
        if (page != null && page < 0) {
            throw new PageException("Page number must not be less than 0");
        } else if (page != null && size < 1) {
            throw new PageException("Page size must not be less than one");
        }
        Pageable pageable = getPageable(page, size, sort);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        List<EmployeeDto> employees = (List<EmployeeDto>) employeeMapper.mapToDto(employeePage.getContent());
        return employees.isEmpty() ? new ArrayList<>() : employees;
    }

    @Loggable
    @Override
    public void delete(Long id) {
        employeeRepository.delete(employeeMapper.mapToEntity(readById(id)));
    }

    @Override
    public Integer getPagesCount(Integer page, Integer size) {
        return employeeRepository.findAll(getPageable(page, size, null))
                .getTotalPages();
    }
}
