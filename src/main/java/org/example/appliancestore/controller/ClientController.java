package org.example.appliancestore.controller;

import lombok.AllArgsConstructor;
import org.example.appliancestore.annotation.IsEmployee;
import org.example.appliancestore.aspect.Loggable;
import org.example.appliancestore.dto.ClientDto;
import org.example.appliancestore.dto.ClientUpdate;
import org.example.appliancestore.mapper.ClientMapper;
import org.example.appliancestore.mapper.ClientUpdateMapper;
import org.example.appliancestore.model.Client;
import org.example.appliancestore.model.User;
import org.example.appliancestore.service.ClientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;
    private final ClientUpdateMapper clientUpdateMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;


    @Loggable
    @GetMapping
    @IsEmployee
    public String getAll(Model model,
                         @RequestParam(name = "page", defaultValue = "0") Integer page,
                         @RequestParam(name = "size", defaultValue = "5") Integer size,
                         @RequestParam(name = "sort", required = false) String sort) {
        List<ClientDto> clients = clientService.getAll(page, size, sort);
        model.addAttribute("clients", clients);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", clientService.getPagesCount(page, size));
        model.addAttribute("pageSize", size);
        return "client/clients";
    }

    @Loggable
    @GetMapping("/add")
    @IsEmployee
    public String add(Model model) {
        ClientDto client = new ClientDto();
        model.addAttribute("client", client);
        return "client/newClient";
    }

    @Loggable
    @PostMapping("/add")
    @IsEmployee
    public String add(@Validated @ModelAttribute("client") ClientDto client, BindingResult result) {
        if (result.hasErrors()) {
            return "client/newClient";
        }
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientService.add(clientMapper.mapToEntity(client));
        return "redirect:/clients";
    }


    @Loggable
    @GetMapping("/{id}/update")
    @PreAuthorize("hasRole('EMPLOYEE') or #id == authentication.principal.id")
    public String update(@PathVariable("id") Long id, Model model) {
        Client client = clientMapper.mapToEntity(clientService.readById(id));
        ClientUpdate clientUpdate = clientUpdateMapper.mapToDto(client);
        model.addAttribute("clientUpdate", clientUpdate);
        return "client/updateClient";
    }

    @Loggable
    @PostMapping("/{id}/update")
    @PreAuthorize("hasRole('EMPLOYEE') or #id == authentication.principal.id")
    public String update(@PathVariable("id") Long id, @Validated @ModelAttribute("clientUpdate") ClientUpdate clientUpdate,
                         BindingResult result, Principal principal, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "client/updateClient";
        }
        ClientDto oldClientDto = clientService.readById(id);
        if (!passwordEncoder.matches(clientUpdate.getOldPassword(), oldClientDto.getPassword())) {
            result.rejectValue("oldPassword", "error.oldPassword", "Old password is incorrect.");
        }
        if (passwordEncoder.matches(clientUpdate.getNewPassword(), oldClientDto.getPassword())) {
            result.rejectValue("newPassword", "error.newPassword", "New password must difference from old password.");
        }
        if (result.hasErrors()) {
            return "client/updateClient";
        }
        Client clientToSave = clientUpdateMapper.mapToEntity(clientUpdate);
        clientToSave.setPassword(passwordEncoder.encode(clientToSave.getPassword()));
        clientService.update(clientToSave);
        String userName = principal.getName();
        User user = (User) userDetailsService.loadUserByUsername(userName);
        if (user instanceof Client) {
            redirectAttributes.addFlashAttribute("message",
                    "Your information has been updated!");
            return "redirect:/home";
        }
        return "redirect:/clients";
    }


    @Loggable
    @GetMapping("/{id}/delete")
    @IsEmployee
    public String delete(@PathVariable("id") Long id) {
        clientService.delete(id);
        return "redirect:/clients";
    }
}
