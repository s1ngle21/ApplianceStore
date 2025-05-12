package org.example.appliancestore.config;

import lombok.AllArgsConstructor;
import org.example.appliancestore.dto.ClientDto;
import org.example.appliancestore.dto.EmployeeDto;
import org.example.appliancestore.service.ClientService;
import org.example.appliancestore.service.EmployeeService;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;

@ControllerAdvice
@AllArgsConstructor
public class GlobalBinderConfig {

    private final ClientService clientService;
    private final EmployeeService employeeService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(ClientDto.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(clientService.readById(Long.valueOf(text)));
            }
        });

        binder.registerCustomEditor(EmployeeDto.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(employeeService.readById(Long.valueOf(text)));
            }
        });
    }
}
