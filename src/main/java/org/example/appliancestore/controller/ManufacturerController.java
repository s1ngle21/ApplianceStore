package org.example.appliancestore.controller;

import lombok.AllArgsConstructor;
import org.example.appliancestore.annotation.IsEmployee;
import org.example.appliancestore.aspect.Loggable;
import org.example.appliancestore.dto.ManufacturerDto;
import org.example.appliancestore.mapper.ManufacturerMapper;
import org.example.appliancestore.service.ManufacturerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manufacturers")
@AllArgsConstructor
public class ManufacturerController {

    private final ManufacturerService manufacturerService;
    private final ManufacturerMapper manufacturerMapper;

    @Loggable
    @GetMapping
    @IsEmployee
    public String getAll(Model model,
                         @RequestParam(name = "page", defaultValue = "0") Integer page,
                         @RequestParam(name = "size", defaultValue = "5") Integer size,
                         @RequestParam(name = "sort", required = false) String sort) {
        model.addAttribute("manufacturers", manufacturerService.getAll(page, size, sort));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", manufacturerService.getPagesCount(page, size));
        model.addAttribute("pageSize", size);
        return "manufacture/manufacturers";
    }

    @Loggable
    @GetMapping("/add")
    @IsEmployee
    public String add(Model model) {
        ManufacturerDto manufacturer = new ManufacturerDto();
        model.addAttribute("manufacturer", manufacturer);
        return "manufacture/newManufacturer";
    }

    @Loggable
    @PostMapping("/add")
    @IsEmployee
    public String add(@Validated @ModelAttribute("manufacturer") ManufacturerDto manufacturer, BindingResult result) {
        if (result.hasErrors()) {
            return "manufacture/newManufacturer";
        }
        manufacturerService.add(manufacturerMapper.mapToEntity(manufacturer));
        return "redirect:/manufacturers";
    }

    @Loggable
    @GetMapping("/{id}/update")
    @IsEmployee
    public String update(@PathVariable("id") Long id, Model model) {
        ManufacturerDto manufacturer = manufacturerService.readById(id);
        model.addAttribute("manufacturer", manufacturer);
        return "manufacture/updateManufacturer";
    }

    @Loggable
    @PostMapping("/{id}/update")
    @IsEmployee
    public String update(@PathVariable("id") Long id, @Validated @ModelAttribute("manufacturer") ManufacturerDto manufacturer, BindingResult result) {
        if (result.hasErrors()) {
            return "manufacture/updateManufacturer";
        }
        manufacturerService.update(manufacturerMapper.mapToEntity(manufacturer));
        return "redirect:/manufacturers";
    }

    @Loggable
    @GetMapping("/{id}/delete")
    @IsEmployee
    public String delete(@PathVariable("id") Long id) {
        manufacturerService.delete(id);
        return "redirect:/manufacturers";
    }
}
