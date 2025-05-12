package org.example.appliancestore.controller;

import lombok.AllArgsConstructor;
import org.example.appliancestore.annotation.IsEmployee;
import org.example.appliancestore.aspect.Loggable;
import org.example.appliancestore.dto.ApplianceDto;
import org.example.appliancestore.dto.ManufacturerDto;
import org.example.appliancestore.mapper.ApplianceMapper;
import org.example.appliancestore.model.Appliance;
import org.example.appliancestore.model.Category;
import org.example.appliancestore.model.Order;
import org.example.appliancestore.model.PowerType;
import org.example.appliancestore.service.ApplianceService;
import org.example.appliancestore.service.ManufacturerService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/appliances")
@AllArgsConstructor
public class ApplianceController {

    private final ApplianceService applianceService;
    private final ManufacturerService manufacturerService;
    private final ApplianceMapper applianceMapper;

    @Loggable
    @GetMapping
    @IsEmployee
    public String getAll(Model model,
                         @RequestParam(name = "page", defaultValue = "0") Integer page,
                         @RequestParam(name = "size", defaultValue = "5") Integer size,
                         @RequestParam(name = "sort", required = false) String sort) {
        List<ApplianceDto> appliances = applianceService.getAll(page, size, sort);
        model.addAttribute("appliances", appliances);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", applianceService.getPagesCount(page, size));
        model.addAttribute("pageSize", size);
        return "appliance/appliances";
    }

    @Loggable
    @GetMapping("/add")
    @IsEmployee
    public String add(Model model,
                      @RequestParam(name = "page", required = false) Integer page,
                      @RequestParam(name = "size", required = false) Integer size,
                      @RequestParam(name = "sort", required = false) String sort) {

        ApplianceDto appliance = new ApplianceDto();
        List<ManufacturerDto> manufacturers = manufacturerService.getAll(page, size, sort);
        model.addAttribute("appliance", appliance);
        model.addAttribute("categories", Category.values());
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("powerTypes", PowerType.values());
        return "appliance/newAppliance";
    }

    @Loggable
    @PostMapping("/add")
    @IsEmployee
    public String add(@Validated @ModelAttribute("appliance") ApplianceDto appliance, BindingResult result) {
        if (result.hasErrors()) {
            return "appliance/newAppliance";
        }
        applianceService.add(applianceMapper.mapToEntity(appliance));
        return "redirect:/appliances";
    }

    @Loggable
    @GetMapping("/{id}/update")
    @IsEmployee
    public String update(@PathVariable("id") Long id, Model model) {
        ApplianceDto appliance = applianceService.readById(id);
        model.addAttribute("appliance", appliance);
        model.addAttribute("categories", Category.values());
        model.addAttribute("powerTypes", PowerType.values());
        return "appliance/updateAppliance";
    }

    @Loggable
    @PostMapping("/{id}/update")
    @IsEmployee
    public String update(@PathVariable("id") Long id, @Validated @ModelAttribute("appliance") ApplianceDto appliance, BindingResult result) {
        if (result.hasErrors()) {
            return "appliance/updateAppliance";
        }
        Appliance oldAppliance = applianceMapper.mapToEntity(applianceService.readById(id));
        appliance.setManufacturer(oldAppliance.getManufacturer());
        applianceService.update(applianceMapper.mapToEntity(appliance));
        return "redirect:/appliances";
    }


    @Loggable
    @GetMapping("/{id}/delete")
    @IsEmployee
    public String delete(@PathVariable("id") Long id) {
        applianceService.delete(id);
        return "redirect:/appliances";
    }

}

