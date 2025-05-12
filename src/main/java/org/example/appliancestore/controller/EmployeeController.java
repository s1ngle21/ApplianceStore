package org.example.appliancestore.controller;


import lombok.AllArgsConstructor;
import org.example.appliancestore.annotation.IsEmployee;
import org.example.appliancestore.aspect.Loggable;
import org.example.appliancestore.dto.EmployeeDto;
import org.example.appliancestore.mapper.EmployeeMapper;
import org.example.appliancestore.service.EmployeeService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @Loggable
    @GetMapping
    @IsEmployee
    public String getAll(Model model,
                         @RequestParam(name = "page", defaultValue = "0") Integer page,
                         @RequestParam(name = "size", defaultValue = "5") Integer size,
                         @RequestParam(name = "sort", required = false) String sort) {
        List<EmployeeDto> employees = employeeService.getAll(page, size, sort);
        model.addAttribute("employees", employees);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeeService.getPagesCount(page, size));
        model.addAttribute("pageSize", size);
        return "employee/employees";
    }

    @Loggable
    @GetMapping("/add")
    @IsEmployee
    @PostAuthorize("returnObject.owner == authentication.name")
    public String add(Model model) {
        EmployeeDto employee = new EmployeeDto();
        model.addAttribute("employee", employee);
        return "employee/newEmployee";
    }

    @Loggable
    @PostMapping("/add")
    @IsEmployee
    public String add(@Validated @ModelAttribute("employee") EmployeeDto employee, BindingResult result) {
        if (result.hasErrors()) {
            return "employee/newEmployee";
        }
        employeeService.add(employeeMapper.mapToEntity(employee));
        return "redirect:/employees";
    }

    @GetMapping("/{id}/update")
    @IsEmployee
    public String update(@PathVariable("id") Long id, Model model) {
        EmployeeDto employee = employeeService.readById(id);
        model.addAttribute("employee", employee);
        return "employee/updateEmployee";
    }

    @Loggable
    @PostMapping("/{id}/update")
    @IsEmployee
    public String update(@PathVariable("id") Long id, @Validated @ModelAttribute("employee") EmployeeDto employee, BindingResult result) {
        if (result.hasErrors()) {
            return "employee/updateEmployee";
        }
        EmployeeDto oldEmpl = employeeService.readById(id);
        employee.setPassword(oldEmpl.getPassword());
        employeeService.update(employeeMapper.mapToEntity(employee));
        return "redirect:/employees";
    }


    @Loggable
    @GetMapping("/{id}/delete")
    @IsEmployee
    public String delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
        return "redirect:/employees";
    }


}
