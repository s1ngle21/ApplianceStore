package org.example.appliancestore.mapper;

import lombok.AllArgsConstructor;
import org.example.appliancestore.dto.EmployeeDto;
import org.example.appliancestore.model.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@AllArgsConstructor
public class EmployeeMapper implements Mapper<Employee, EmployeeDto> {
    @Override
    public EmployeeDto mapToDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPassword(),
                employee.getDepartment()
        );
    }

    @Override
    public Employee mapToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPassword(employeeDto.getPassword());
        employee.setDepartment(employeeDto.getDepartment());
        return employee;
    }

    @Override
    public Collection<EmployeeDto> mapToDto(Collection<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            return new ArrayList<>();
        }
        return employees.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public Collection<Employee> mapToEntity(Collection<EmployeeDto> employeeDtos) {
        return null;
    }

}
