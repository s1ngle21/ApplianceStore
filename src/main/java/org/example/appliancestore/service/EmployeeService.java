package org.example.appliancestore.service;

import org.example.appliancestore.dto.EmployeeDto;
import org.example.appliancestore.model.Employee;

import java.util.List;


public interface EmployeeService {

    EmployeeDto add(Employee employee);

    EmployeeDto readById(Long id);

    EmployeeDto update(Employee employee);

    List<EmployeeDto> getAll(Integer page, Integer size, String sort);

    void delete(Long id);

    Integer getPagesCount(Integer page, Integer size);
}
